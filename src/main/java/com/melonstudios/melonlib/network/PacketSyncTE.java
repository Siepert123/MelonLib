package com.melonstudios.melonlib.network;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.tileentity.ISyncedTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;

/**
 * A packet for synchronizing some {@link ISyncedTE} data. Revised in 1.7 and 1.11
 * @since 1.4
 * @see ISyncedTE
 * @see PacketRequestSyncTE
 */
@Deprecated
public class PacketSyncTE implements IMessage {
    public BlockPos pos;
    public int size;
    public ByteBuf data;
    public boolean readable = false;

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.size = buf.readInt();
        MelonLib.proxy.packetSyncTE(this.pos, buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
        buf.writeInt(this.size);
        buf.writeBytes(this.data, 0, this.size);
    }

    public PacketSyncTE(ISyncedTE te, BlockPos pos) throws IOException {
        this();
        this.pos = pos;
        TrackedByteBuf tracked = new TrackedByteBuf(this.data);
        te.writePacket(tracked);
        this.data = tracked.internal();
        this.size = tracked.written();
    }
    public PacketSyncTE(ISyncedTE te) throws IOException {
        this(te, te.self_ISyncedTE().getPos());
    }
    public PacketSyncTE() {
        this.data = Unpooled.buffer();
    }

    public static class Handler implements IMessageHandler<PacketSyncTE, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncTE message, MessageContext ctx) {
            //MelonLib.proxy.packetSyncTE(message, this, ctx);
            return null;
        }
    }
}
