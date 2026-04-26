package com.melonstudios.melonlib.network;

import com.melonstudios.melonlib.MelonLib;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBulkSyncTE implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<PacketBulkSyncTE, IMessage> {
        @Override
        public IMessage onMessage(PacketBulkSyncTE message, MessageContext ctx) {
            MelonLib.proxy.packetBulkSyncTE(message, this, ctx);
            return null;
        }
    }
}
