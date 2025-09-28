package com.melonstudios.melonlib.sided;

import com.melonstudios.melonlib.network.PacketSyncTE;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerProxy extends AbstractProxy {
    @Override
    public Side getSide() {
        return Side.SERVER;
    }

    @Override
    public void registerClientTESync(SimpleNetworkWrapper net) {
        net.registerMessage(
                new PacketSyncTE.Handler(),
                PacketSyncTE.class,
                0, Side.CLIENT
        );
    }
}
