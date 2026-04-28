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
import java.util.ArrayList;
import java.util.List;

public final class CPacketBulkSyncTE extends CPacketBase {
    @SideOnly(Side.CLIENT)
    @Nullable
    @Override
    public FMLProxyPacket handle(PacketBuffer data) throws IOException {
        int len = data.readUnsignedShort();
        List<BlockPos> positions = new ArrayList<>(len);
        for (int i = 0; i < len; i++) positions.add(data.readBlockPos());
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.world;
        for (BlockPos pos : positions) {
            if (world.isBlockLoaded(pos)) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof ISyncedTE) {
                    ((ISyncedTE) te).readPacket(data);
                }
            }
        }
        return null;
    }

    @Nullable
    public FMLProxyPacket create(List<ISyncedTE> tiles) throws IOException {
        tiles.removeIf(tile -> tile.self_ISyncedTE().isInvalid());
        if (tiles.isEmpty()) return null;
        PacketBuffer data = this.buf();
        List<BlockPos> positions = new ArrayList<>(tiles.size());
        for (ISyncedTE syncedTE : tiles) {
            TileEntity te = syncedTE.self_ISyncedTE();
            positions.add(te.getPos().toImmutable());
        }
        data.writeShort(positions.size());
        for (BlockPos pos : positions) data.writeBlockPos(pos);
        TrackedByteBuf tracked = new TrackedByteBuf(data);
        for (ISyncedTE syncedTE : tiles) {
            syncedTE.writePacket(tracked);
        }
        return new FMLProxyPacket(data, MelonLibPacketManager.CHANNEL);
    }
}
