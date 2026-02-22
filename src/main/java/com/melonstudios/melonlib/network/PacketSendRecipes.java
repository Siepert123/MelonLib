package com.melonstudios.melonlib.network;

import com.melonstudios.melonlib.MelonLib;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;

public class PacketSendRecipes implements IMessage {
    private ByteBuf buf;
    @Override
    public void fromBytes(ByteBuf buf) {
        int strLen = buf.readInt();
        String typeID = buf.readCharSequence(strLen, StandardCharsets.UTF_8).toString();
        int amount = buf.readInt();
        //Client performance is less relevant as it only needs to happen once
        MelonLib.proxy.receiveRecipes(typeID, amount, buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(this.buf);
    }

    public PacketSendRecipes() {

    }

    //Pre-code the buffer to increase server performance
    public PacketSendRecipes(ByteBuf buf) {
        this.buf = buf;
    }

    public static class Handler implements IMessageHandler<PacketSendRecipes, IMessage> {
        @Override
        public IMessage onMessage(PacketSendRecipes message, MessageContext ctx) {
            return null;
        }
    }
}
