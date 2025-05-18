package com.melonstudios.melonlib.sided;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashSet;

@SuppressWarnings("all")
public class ClientPacketStaller {
    private static boolean isDelegation = false;
    public static boolean delegation() {
        return isDelegation;
    }
    public static HashSet<Stall> STALLS = new HashSet<>();

    public static void add(Stall stall) {
        SidedExecution.onlyIn(Side.CLIENT, () -> () -> STALLS.add(stall));
    }

    public static void tick() {
        isDelegation = true;
        HashSet<Stall> copy = new HashSet<>(STALLS);
        STALLS.clear();
        for (Stall stall : copy) {
            try {
                stall.handler.onMessage(stall.packet, stall.ctx);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        copy.clear();
        isDelegation = false;
    }

    public static class Stall {
        public final IMessage packet;
        public final IMessageHandler handler;
        public final MessageContext ctx;

        public Stall(IMessage packet, IMessageHandler handler, MessageContext ctx) {
            this.packet = packet;
            this.handler = handler;
            this.ctx = ctx;
        }
    }
}
