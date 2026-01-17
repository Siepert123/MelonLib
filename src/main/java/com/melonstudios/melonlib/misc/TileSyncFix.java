package com.melonstudios.melonlib.misc;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.network.PacketSyncTE;
import com.melonstudios.melonlib.tileentity.ISyncedTE;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    public void flush() {
        synchronized (this.tileEntitiesQueue) {
            for (Set<ISyncedTE> syncedTEs : this.tileEntitiesQueue.values()) {
                for (ISyncedTE te : syncedTEs) {
                    if (te.self_ISyncedTE().isInvalid()) continue;
                    MelonLib.net.sendToAllTracking(new PacketSyncTE(te), te.getTargetPoint());
                }
                syncedTEs.clear();
            }
        }
    }
}
