package com.melonstudios.melonlib.sided;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.network.PacketSyncTE;
import com.melonstudios.melonlib.tileentity.ISyncedTE;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends AbstractProxy {
    @Override
    public Side getSide() {
        return Side.CLIENT;
    }

    @Override
    public void registerClientTESync(SimpleNetworkWrapper net) {
        net.registerMessage(
                SidedExecution.supplyIf(Side.CLIENT, PacketSyncTE.Handler::new).get(),
                PacketSyncTE.class,
                1, Side.CLIENT
        );
    }

    @Override
    public void packetSyncTE(PacketSyncTE packet, IMessageHandler<PacketSyncTE, IMessage> handler, MessageContext ctx) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.addScheduledTask(() -> {
            World world = mc.world;
            boolean retry = false;
            if (world.isBlockLoaded(packet.pos)) {
                TileEntity te = world.getTileEntity(packet.pos);
                if (te instanceof ISyncedTE) {
                    ((ISyncedTE)te).readPacket(packet.nbt);
                } else if (te == null) {
                    retry = true;
                }
            } else retry = true;
            if (retry) {
                //MelonLib.logger.warn("No tile entity at {}, retrying", packet.pos);
                //ClientPacketStaller.add(new ClientPacketStaller.Stall(packet, handler, ctx));
                MelonLib.logger.warn("No tile entity at {}, skipping", packet.pos);
            }
        });
    }
}
