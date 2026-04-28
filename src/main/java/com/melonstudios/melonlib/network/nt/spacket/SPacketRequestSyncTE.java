package com.melonstudios.melonlib.network.nt.spacket;

import com.melonstudios.melonlib.network.nt.MelonLibCPackets;
import com.melonstudios.melonlib.network.nt.MelonLibPacketManager;
import com.melonstudios.melonlib.network.nt.SPacketBase;
import com.melonstudios.melonlib.tileentity.ISyncedTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import javax.annotation.Nullable;
import java.io.IOException;

public final class SPacketRequestSyncTE extends SPacketBase {
    @Nullable
    @Override
    public FMLProxyPacket handle(PacketBuffer data, EntityPlayer player) throws IOException {
        BlockPos pos = data.readBlockPos();
        World world = player.world;

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ISyncedTE) {
            return MelonLibCPackets.SYNC_TE.create((ISyncedTE) te);
        } else return null;
    }

    public FMLProxyPacket create(ISyncedTE te) {
        return this.create(te.self_ISyncedTE().getPos());
    }
    public FMLProxyPacket create(BlockPos pos) {
        PacketBuffer data = this.buf();
        data.writeBlockPos(pos);
        return new FMLProxyPacket(data, MelonLibPacketManager.CHANNEL);
    }
}
