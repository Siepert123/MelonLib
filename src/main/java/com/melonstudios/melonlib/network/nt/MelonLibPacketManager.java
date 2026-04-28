package com.melonstudios.melonlib.network.nt;

import com.melonstudios.melonlib.MelonLib;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectArrayMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ByteArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class MelonLibPacketManager {
    public static final String CHANNEL = "melonlib";
    public static FMLEventChannel bus;

    public static final Byte2ObjectMap<SPacketBase> handlersSPacket = new Byte2ObjectArrayMap<>();
    public static final Object2ByteMap<SPacketBase> idMapSPacket = new Object2ByteArrayMap<>();

    public static final Byte2ObjectMap<CPacketBase> handlersCPacket = new Byte2ObjectArrayMap<>();
    public static final Object2ByteMap<CPacketBase> idMapCPacket = new Object2ByteArrayMap<>();

    private static void register(SPacketBase handler, int id) {
        if (id < 0 || id > 255) throw new IllegalArgumentException("Invalid packet ID: " + id);
        handlersSPacket.put((byte) id, handler);
        idMapSPacket.put(handler, (byte) id);
    }
    private static void register(CPacketBase handler, int id) {
        if (id < 0 || id > 255) throw new IllegalArgumentException("Invalid packet ID: " + id);
        handlersCPacket.put((byte) id, handler);
        idMapCPacket.put(handler, (byte) id);
    }

    public static PacketBuffer pb(ByteBuf buf) {
        return buf instanceof PacketBuffer ? (PacketBuffer) buf : new PacketBuffer(buf);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPacketData(FMLNetworkEvent.ClientCustomPacketEvent event) {
        FMLProxyPacket packet = event.getPacket();
        PacketBuffer data = pb(packet.payload());
        byte type = data.readByte();
        CPacketBase handler = handlersCPacket.get(type);
        if (handler == null) {
            MelonLib.logger.error("Received unknown clientbound packet of ID #{}", Byte.toUnsignedInt(type));
        } else {
            try {
                FMLProxyPacket reply = handler.handle(data);
                if (reply != null) {
                    sendToServer(reply);
                    //event.setReply(reply);
                }
            } catch (Throwable e) {
                throw new RuntimeException("Exception on packet of ID #" + Byte.toUnsignedInt(type), e);
            }
        }
    }

    @SubscribeEvent
    public void onPacketData(FMLNetworkEvent.ServerCustomPacketEvent event) {
        FMLProxyPacket packet = event.getPacket();
        PacketBuffer data = pb(packet.payload());
        byte type = data.readByte();
        SPacketBase handler = handlersSPacket.get(type);
        if (handler == null) {
            MelonLib.logger.error("Received unknown serverbound packet of ID #{}");
        } else {
            try {
                EntityPlayerMP player = ((NetHandlerPlayServer) event.getHandler()).player;
                FMLProxyPacket reply = handler.handle(data, player);
                if (reply != null) {
                    sendTo(packet, player);
                    //event.setReply(reply);
                }
            } catch (Throwable e) {
                throw new RuntimeException("Exception on serverbound packet of ID #" + Byte.toUnsignedInt(type), e);
            }
        }
    }

    public MelonLibPacketManager() {
        register(MelonLibSPackets.REQUEST_SYNC_TE, 1);

        register(MelonLibCPackets.SYNC_TE, 1);
        register(MelonLibCPackets.BULK_SYNC_TE, 2);
        register(MelonLibCPackets.SEND_RECIPES, 10);
    }

    public static void sendToAll(FMLProxyPacket pkt) {
        if (pkt != null) bus.sendToAll(pkt);
    }
    public static void sendTo(FMLProxyPacket pkt, EntityPlayerMP player) {
        if (pkt != null) bus.sendTo(pkt, player);
    }
    public static void sendToAllAround(FMLProxyPacket pkt, NetworkRegistry.TargetPoint point) {
        if (pkt != null) bus.sendToAllAround(pkt, point);
    }
    public static void sendToAllTracking(FMLProxyPacket pkt, NetworkRegistry.TargetPoint point) {
        if (pkt != null) bus.sendToAllTracking(pkt, point);
    }
    public static void sendToAllTracking(FMLProxyPacket pkt, Entity entity) {
        if (pkt != null) bus.sendToAllTracking(pkt, entity);
    }
    public static void sendToDimension(FMLProxyPacket pkt, int dimensionId) {
        if (pkt != null) bus.sendToDimension(pkt, dimensionId);
    }
    public static void sendToServer(FMLProxyPacket pkt) {
        if (pkt != null) bus.sendToServer(pkt);
    }
}
