package nl.melonstudios.melonlib.tileentity;

import net.minecraft.util.ITickable;

public abstract class TileEntityOptimizedBase extends TileEntityCachedRenderBB implements ISyncedTE, ITickable {
    private int tickRateLazy, tickCounterLazy;

    protected void setTickRateLazy(int rate) {
        this.tickRateLazy = this.tickCounterLazy = rate;
    }

    @Override
    public void onLoad() {
        if (this.world.isRemote) this.requestSync();
    }

    @Override
    public boolean compressPacketNBT() {
        return false;
    }

    @Override
    public void update() {
        this.tick();
        if (this.tickCounterLazy-- <= this.tickRateLazy) {
            this.tickCounterLazy = this.tickRateLazy;
            this.tickLazy();
        }
    }

    public abstract void tick();
    public abstract void tickLazy();
}
