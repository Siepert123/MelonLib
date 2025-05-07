package nl.melonstudios.melonlib.network;

import nl.melonstudios.melonlib.tileentity.ISyncedTE;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * A packet requesting a synchronization of any {@link ISyncedTE}.
 * @since 1.4
 * @see ISyncedTE
 * @see PacketSyncTE
 */
public class PacketRequestSyncTE implements IMessage {
    private BlockPos pos;
    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
    }

    public PacketRequestSyncTE() {

    }
    public PacketRequestSyncTE(ISyncedTE te) {
        this.pos = te.self_ISyncedTE().getPos();
    }

    public static class Handler implements IMessageHandler<PacketRequestSyncTE, PacketSyncTE> {
        @Override
        public PacketSyncTE onMessage(PacketRequestSyncTE message, MessageContext ctx) {
            TileEntity te = ctx.getServerHandler().player.world.getTileEntity(message.pos);
            if (te instanceof ISyncedTE) {
                return new PacketSyncTE((ISyncedTE) te);
            }
            return null;
        }
    }
}
