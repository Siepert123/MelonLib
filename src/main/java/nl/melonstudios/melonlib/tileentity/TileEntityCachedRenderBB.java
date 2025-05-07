package nl.melonstudios.melonlib.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A TileEntity with a cached render bounding box.
 * Good for performance, as it does not need to create a new {@link AxisAlignedBB} object each frame for each TE.
 * (For reference, creating a new Object is not very good for performance if done in bulk)
 * @since 1.4
 */
public abstract class TileEntityCachedRenderBB extends TileEntity {
    private AxisAlignedBB cachedRenderAABB = null;

    @Override
    @SideOnly(Side.CLIENT)
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
