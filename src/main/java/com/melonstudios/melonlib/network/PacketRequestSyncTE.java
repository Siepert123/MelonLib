package com.melonstudios.melonlib.network;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.sided.ServerPacketStaller;
import com.melonstudios.melonlib.tileentity.ISyncedTE;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
    private int dimension;
    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.dimension = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
        buf.writeInt(this.dimension);
    }

    public PacketRequestSyncTE() {

    }
    public PacketRequestSyncTE(ISyncedTE te) {
        this.pos = te.self_ISyncedTE().getPos();
        this.dimension = te.self_ISyncedTE().getWorld().provider.getDimension();
    }

    public static class Handler implements IMessageHandler<PacketRequestSyncTE, PacketSyncTE> {
        @Override
        public PacketSyncTE onMessage(PacketRequestSyncTE message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntity te = world.getTileEntity(message.pos);
            if (te instanceof ISyncedTE) {
                return new PacketSyncTE((ISyncedTE) te, message.pos);
            } else if (te == null) {
                //ServerPacketStaller.add(new ServerPacketStaller.Stall(message, this, ctx));
                MelonLib.logger.warn("No tile entity at {}, skipping", message.pos);
            }
            return null;
        }
    }
}
