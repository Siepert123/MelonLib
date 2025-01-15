package com.melonstudios.melonlib.misc;

import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Helper class to aid with creating Bounding Boxes.
 * @since 1.0
 */
public class AABB {
    private AABB() {}
    //Some default bounding boxes
    public static final AxisAlignedBB FULL_BLOCK_AABB = Block.FULL_BLOCK_AABB;
    public static final AxisAlignedBB NULL_AABB = Block.NULL_AABB;
    public static final AxisAlignedBB SLAB_BOTTOM_AABB = create(0, 0, 0, 16, 8, 16);
    public static final AxisAlignedBB SLAB_TOP_AABB = create(0, 8, 0, 16, 16, 16);

    /**
     * Creates a new Bounding Box, but uses pixels instead of blocks.
     * @return The new AxisAlignedBB
     */
    public static AxisAlignedBB create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new AxisAlignedBB(minX / 16, minY / 16, minZ / 16, maxX / 16, maxY / 16, maxZ / 16);
    }

    /**
     * Creates a new Bounding Box of a certain size wrapping a block pos.
     * @return The new AxisAlignedBB
     */
    public static AxisAlignedBB wrap(BlockPos pos, int range) {
        return new AxisAlignedBB(
                pos.add(-range, -range, -range),
                pos.add(range+1, range+1, range+1)
        );
    }
}
