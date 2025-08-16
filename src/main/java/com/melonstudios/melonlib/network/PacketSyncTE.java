package com.melonstudios.melonlib.network;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.sided.ClientPacketStaller;
import com.melonstudios.melonlib.sided.SidedExecution;
import com.melonstudios.melonlib.tileentity.ISyncedTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * A packet for synchronizing some {@link ISyncedTE} data. Revised in 1.7
 * @since 1.4
 * @see ISyncedTE
 * @see PacketRequestSyncTE
 */
public class PacketSyncTE implements IMessage {

    public boolean compressedNBT;
    public BlockPos pos;
    public NBTTagCompound nbt;
    @Override
    public void fromBytes(ByteBuf buf) {
        this.compressedNBT = buf.readBoolean();
        this.pos = BlockPos.fromLong(buf.readLong());
        try {
            this.nbt = new PacketBuffer(buf).readCompoundTag();
        } catch (IOException e) {
            if (this.nbt == null) this.nbt = new NBTTagCompound();
            MelonLib.logger.error("help!!", e);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.compressedNBT);
        buf.writeLong(this.pos.toLong());
        new PacketBuffer(buf).writeCompoundTag(this.nbt);
    }

    public PacketSyncTE(ISyncedTE te, BlockPos pos) {
        this.compressedNBT = te.compressPacketNBT();
        this.pos = pos;
        this.nbt = te.writePacket();
    }
    public PacketSyncTE(ISyncedTE te) {
        this.compressedNBT = te.compressPacketNBT();
        this.pos = te.self_ISyncedTE().getPos();
        this.nbt = te.writePacket();
    }
    public PacketSyncTE() {

    }

    public static class Handler implements IMessageHandler<PacketSyncTE, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncTE message, MessageContext ctx) {
            MelonLib.proxy.packetSyncTE(message, this, ctx);
            return null;
        }
    }
}
