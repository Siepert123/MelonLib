package com.melonstudios.melonlib.sided;

import com.melonstudios.melonlib.network.PacketSyncTE;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractProxy {
    public abstract Side getSide();
    public abstract void registerClientTESync(SimpleNetworkWrapper net);
    public void packetSyncTE(PacketSyncTE packet, IMessageHandler<PacketSyncTE, IMessage> handler, MessageContext ctx) {

    }
}
