package com.melonstudios.melonlib.tileentity;

public abstract class TileEntityOptimizedBase extends TileEntityCachedRenderBB implements ISyncedTE {
    @Override
    public void onLoad() {
        if (this.world.isRemote) this.requestSync();
    }

    @Override
    public boolean compressPacketNBT() {
        return false;
    }
}
