package com.melonstudios.melonlib.network;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.tileentity.ISyncedTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;

/**
 * A packet for synchronizing bundled {@link ISyncedTE} data.
 * @since 1.11
 * @see ISyncedTE
 * @see PacketSyncTE
 * @see PacketRequestSyncTE
 */
public class PacketBulkSyncTE implements IMessage {
    public List<BlockPos> positions;
    public int size;
    public ByteBuf data;
    public boolean readable = false;

    @Override
    public void fromBytes(ByteBuf buf) {
        int len = buf.readUnsignedShort();
        this.positions = new ArrayList<>(len);
        for (int i = 0; i < len; i++) this.positions.add(BlockPos.fromLong(buf.readLong()));
        this.size = buf.readInt();
        this.data.readBytes(buf, 0, this.size);
        this.readable = true;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeShort(this.positions.size());
        for (BlockPos pos : this.positions) buf.writeLong(pos.toLong());
        buf.writeInt(this.size);
        buf.writeBytes(this.data, 0, this.size);
    }

    public PacketBulkSyncTE(List<ISyncedTE> tiles) {
        this();
        this.positions = new ArrayList<>(tiles.size());
        TrackedByteBuf tracked = new TrackedByteBuf(this.data);
        for (ISyncedTE syncedTE : tiles) {
            TileEntity te = syncedTE.self_ISyncedTE();
            this.positions.add(te.getPos().toImmutable());
            syncedTE.writePacket(tracked);
        }
        this.data = tracked.internal();
        this.size = tracked.written();
    }
    public PacketBulkSyncTE() {
        this.data = Unpooled.buffer();
    }

    public static class Handler implements IMessageHandler<PacketBulkSyncTE, IMessage> {
        @Override
        public IMessage onMessage(PacketBulkSyncTE message, MessageContext ctx) {
            MelonLib.proxy.packetBulkSyncTE(message, this, ctx);
            return null;
        }
    }
}
