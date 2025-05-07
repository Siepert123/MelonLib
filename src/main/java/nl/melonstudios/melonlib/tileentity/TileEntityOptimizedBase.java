package nl.melonstudios.melonlib.tileentity;

import net.minecraft.util.ITickable;

/**
 * A base optimized TileEntity for various purposes.
 * @since 1.4
 * @see ISyncedTE
 */
public abstract class TileEntityOptimizedBase extends TileEntityCachedRenderBB implements ISyncedTE, ITickable {
    private int tickRateLazy, tickCounterLazy;
    private boolean syncNextTick = false;

    /**
     * Changes the lazy tick rate for this tile entity.
     * @param rate The new lazy tick rate
     */
    protected void setTickRateLazy(int rate) {
        this.tickRateLazy = this.tickCounterLazy = rate;
    }

    @Override
    public void sync() {
        if (this.pos != null)
            ISyncedTE.super.sync();
        else this.syncNextTick = true;
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
    public final void update() {
        if (this.syncNextTick) {
            this.syncNextTick = false;
            this.sync();
        }
        this.tick();
        if (this.tickCounterLazy-- <= this.tickRateLazy) {
            this.tickCounterLazy = this.tickRateLazy;
            this.tickLazy();
        }
    }

    /**
     * Called every tick
     * @since 1.6
     */
    public abstract void tick();

    /**
     * Called every lazy tick
     * @since 1.6
     * @see #setTickRateLazy(int)
     */
    public abstract void tickLazy();
}
