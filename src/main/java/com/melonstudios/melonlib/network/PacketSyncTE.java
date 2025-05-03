package com.melonstudios.melonlib.network;

import com.melonstudios.melonlib.tileentity.ISyncedTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PacketSyncTE implements IMessage {

    private boolean compressedNBT;
    private BlockPos pos;
    private NBTTagCompound nbt;
    @Override
    public void fromBytes(ByteBuf buf) {
        this.compressedNBT = buf.readBoolean();
        this.pos = BlockPos.fromLong(buf.readLong());
        if (this.compressedNBT) {
            try (DataInputStream in = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteBufInputStream(buf))))) {
                this.nbt = CompressedStreamTools.read(in);
            } catch (IOException e) {
                e.printStackTrace();
                if (this.nbt == null) this.nbt = new NBTTagCompound();
            }
        } else {
            try (DataInputStream in = new DataInputStream(new BufferedInputStream(new ByteBufInputStream(buf)))) {
                this.nbt = CompressedStreamTools.read(in);
            } catch (IOException e) {
                e.printStackTrace();
                if (this.nbt == null) this.nbt = new NBTTagCompound();
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.compressedNBT);
        buf.writeLong(this.pos.toLong());
        if (this.compressedNBT) {
            try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(new ByteBufOutputStream(buf))))) {
                CompressedStreamTools.write(this.nbt, out);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write compressed TE sync packet", e);
            }
        } else {
            try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new ByteBufOutputStream(buf)))) {
                CompressedStreamTools.write(this.nbt, out);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write uncompressed TE sync packet", e);
            }
        }
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
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                World world = mc.world;
                if (world.isBlockLoaded(message.pos)) {
                    TileEntity te = world.getTileEntity(message.pos);
                    if (te instanceof ISyncedTE) {
                        ((ISyncedTE)te).readPacket(message.nbt);
                    }
                }
            });
            return null;
        }
    }
}
