package com.melonstudios.melonlib.misc;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.MelonLibConfig;
import com.melonstudios.melonlib.network.PacketBulkSyncTE;
import com.melonstudios.melonlib.network.PacketSyncTE;
import com.melonstudios.melonlib.tileentity.ISyncedTE;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.*;

public class TileSyncFix {
    private static final TileSyncFix INSTANCE = new TileSyncFix();
    public static TileSyncFix getInstance() {
        return INSTANCE;
    }

    private final Map<World, Set<ISyncedTE>> tileEntitiesQueue = new HashMap<>();

    public void clear() {
        this.tileEntitiesQueue.clear();
    }
    public void sync(ISyncedTE te) {
        TileEntity tile = te.self_ISyncedTE();
        Set<ISyncedTE> set = this.tileEntitiesQueue.computeIfAbsent(tile.getWorld(), k -> new HashSet<>());
        set.add(te);
    }

    /**
     * Flushes all tile entities to be synced to all tracking players.
     */
    public void flush() {
        synchronized (this.tileEntitiesQueue) {
            if (MelonLibConfig.bulkSendTileEntities) {
                for (Map.Entry<World, Set<ISyncedTE>> entry : this.tileEntitiesQueue.entrySet()) {
                    int dimension = entry.getKey().provider.getDimension();
                    Set<ISyncedTE> syncedTEs = entry.getValue();
                    Long2ObjectMap<List<ISyncedTE>> tiles = new Long2ObjectArrayMap<>();
                    for (ISyncedTE te : syncedTEs) {
                        TileEntity self = te.self_ISyncedTE();
                        if (self.isInvalid()) continue;
                        long pos = ChunkPos.asLong(self.getPos().getX() >> 4, self.getPos().getZ() >> 4);
                        tiles.computeIfAbsent(pos, k -> new ArrayList<>()).add(te);
                    }
                    for (Long2ObjectMap.Entry<List<ISyncedTE>> synchronizable : tiles.long2ObjectEntrySet()) {
                        try {
                            List<ISyncedTE> list = synchronizable.getValue();
                            if (list.isEmpty()) {
                                // ???
                            } else if (list.size() == 1) {
                                MelonLib.net.sendToAllTracking(new PacketSyncTE(list.get(0)), list.get(0).getTargetPoint());
                            } else {
                                long chunk = synchronizable.getLongKey();
                                double x = ((int) (chunk) << 4) + 8.0;
                                double z = ((int) (chunk >> 32) << 4) + 8.0;
                                double y = 64.0;
                                MelonLib.net.sendToAllTracking(
                                        new PacketBulkSyncTE(list),
                                        new NetworkRegistry.TargetPoint(dimension, x, y, z, 64.0)
                                );
                            }
                        } catch (Throwable e) {
                            long chunk = synchronizable.getLongKey();
                            int x = (int) chunk;
                            int z = (int) (chunk >> 32);
                            throw new RuntimeException("Exception bulk synchronizing TEs in chunk " + x + "," + z, e);
                        }
                    }
                }
            } else {
                for (Set<ISyncedTE> syncedTEs : this.tileEntitiesQueue.values()) {
                    for (ISyncedTE te : syncedTEs) {
                        if (te.self_ISyncedTE().isInvalid()) continue;
                        try {
                            MelonLib.net.sendToAllTracking(new PacketSyncTE(te), te.getTargetPoint());
                        } catch (Throwable e) {
                            throw new RuntimeException("Exception synchronizing TE", e);
                        }
                    }
                    syncedTEs.clear();
                }
            }
        }
    }
}
