package com.melonstudios.melonlib.network.nt.cpacket;

import com.melonstudios.melonlib.network.TrackedByteBuf;
import com.melonstudios.melonlib.network.nt.CPacketBase;
import com.melonstudios.melonlib.network.nt.MelonLibPacketManager;
import com.melonstudios.melonlib.tileentity.ISyncedTE;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.io.IOException;

public final class CPacketSyncTE extends CPacketBase {
    @SideOnly(Side.CLIENT)
    @Nullable
    @Override
    public FMLProxyPacket handle(PacketBuffer data) throws IOException {
        BlockPos pos = data.readBlockPos();
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.world;
        if (world.isBlockLoaded(pos)) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof ISyncedTE) {
                ((ISyncedTE) te).readPacket(data);
            }
        }
        return null;
    }

    @Nullable
    public FMLProxyPacket create(ISyncedTE tile) throws IOException {
        TileEntity te = tile.self_ISyncedTE();
        if (te.isInvalid()) return null;
        PacketBuffer data = this.buf();
        data.writeBlockPos(te.getPos());
        tile.writePacket(new TrackedByteBuf(data));
        return new FMLProxyPacket(data, MelonLibPacketManager.CHANNEL);
    }
}
