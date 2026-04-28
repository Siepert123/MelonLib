package com.melonstudios.melonlib.network.nt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class CPacketBase {
    @SideOnly(Side.CLIENT)
    @Nullable
    public abstract FMLProxyPacket handle(PacketBuffer data) throws Throwable;

    protected final PacketBuffer buf() {
        ByteBuf data = Unpooled.buffer();
        data.writeByte(MelonLibPacketManager.idMapCPacket.get(this));
        return new PacketBuffer(data);
    }
}
