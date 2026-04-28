package com.melonstudios.melonlib.network.nt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import javax.annotation.Nullable;

public abstract class SPacketBase {
    @Nullable
    public abstract FMLProxyPacket handle(PacketBuffer data, EntityPlayer player) throws Throwable;

    protected final PacketBuffer buf() {
        ByteBuf data = Unpooled.buffer();
        data.writeByte(MelonLibPacketManager.idMapSPacket.get(this));
        return new PacketBuffer(data);
    }
}
