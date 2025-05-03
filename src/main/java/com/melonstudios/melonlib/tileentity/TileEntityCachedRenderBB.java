package com.melonstudios.melonlib.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public abstract class TileEntityCachedRenderBB extends TileEntity {
    private AxisAlignedBB cachedRenderAABB = null;

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.cachedRenderAABB == null) {
            this.cachedRenderAABB = this.createRenderBoundingBox();
        }
        return this.cachedRenderAABB;
    }

    protected AxisAlignedBB createRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }
    public void invalidateRenderBoundingBox() {
        this.cachedRenderAABB = null;
    }
}
