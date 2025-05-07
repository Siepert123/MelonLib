package nl.melonstudios.melonlib.misc;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Optimizes a list of bounding boxes.
     * @param boxes The unoptimized bounding box list
     * @param priorityAxis The axis to optimize along
     * @param recursionsLeft The depth of optimizations allowed
     * @return The optimized list of bounding boxes
     */
    public static List<AxisAlignedBB> optimize(List<AxisAlignedBB> boxes, EnumFacing.Axis priorityAxis, int recursionsLeft) {
        List<AxisAlignedBB> optimized = new ArrayList<>(boxes.size() / 2);
        while (!boxes.isEmpty()) {
            AxisAlignedBB bb = boxes.remove(0);
            boolean merged = false;
            for (int i = 0; i < boxes.size(); i++) {
                AxisAlignedBB aabb = boxes.get(i);
                if (mergeable(bb, aabb, priorityAxis)) {
                    boxes.remove(i);
                    optimized.add(merge(bb, aabb));
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                optimized.add(bb);
            }
        }
        return recursionsLeft > 0 ? optimize(optimized, priorityAxis, recursionsLeft-1) : optimized;
    }

    /**
     * Calculates the best optimization axis.
     * WARNING: May take some time to load as it optimizes three times! Only use in rare occasions.
     * @param boxes The unoptimized bounding box list
     * @param recursions The depth of optimizations allowed
     * @return The best axis to optimize along
     */
    public static EnumFacing.Axis getBestOptimizationAxis(List<AxisAlignedBB> boxes, int recursions) {
        int sizeX = optimize(new ArrayList<>(boxes), EnumFacing.Axis.X, recursions).size();
        int sizeY = optimize(new ArrayList<>(boxes), EnumFacing.Axis.Y, recursions).size();
        int sizeZ = optimize(new ArrayList<>(boxes), EnumFacing.Axis.Z, recursions).size();
        int largest = Math.max(sizeY, Math.max(sizeX, sizeZ));
        if (sizeX == largest) return EnumFacing.Axis.X;
        if (sizeZ == largest) return EnumFacing.Axis.Z;
        return EnumFacing.Axis.Y;
    }

    /**
     * Checks if two {@link AxisAlignedBB}s are mergeable along a certain axis.
     * @param a One AxisAlignedBB
     * @param b Another AxisAlignedBB
     * @param along The axis to check the merge along
     * @return True if the boxes can be merged without issue
     */
    public static boolean mergeable(AxisAlignedBB a, AxisAlignedBB b, EnumFacing.Axis along) {
        switch (along) {
            case X: return a.minY == b.minY && a.maxY == b.maxY && a.minZ == b.minZ && a.maxZ == b.maxZ && intersectsX(a, b);
            case Y: return a.minX == b.minX && a.maxX == b.maxX && a.minZ == b.minZ && a.maxZ == b.maxZ && intersectsY(a, b);
            case Z: return a.minX == b.minX && a.maxX == b.maxX && a.minY == b.maxY && a.maxY == b.maxY && intersectsZ(a, b);
        }
        return false;
    }

    /**
     * Merges two {@link AxisAlignedBB}s. May result something weird if not checked first.
     * @param a One box
     * @param b Another box
     * @return The merged {@link AxisAlignedBB}
     */
    public static AxisAlignedBB merge(AxisAlignedBB a, AxisAlignedBB b) {
        double minX = Math.min(a.minX, b.minX);
        double minY = Math.min(a.minY, b.minY);
        double minZ = Math.min(a.minZ, b.minZ);
        double maxX = Math.max(a.maxX, b.maxX);
        double maxY = Math.max(a.maxY, b.maxY);
        double maxZ = Math.max(a.maxZ, b.maxZ);
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static boolean intersectsX(AxisAlignedBB a, AxisAlignedBB b) {
        return a.minX < b.maxX && a.maxX > b.minX;
    }
    public static boolean intersectsY(AxisAlignedBB a, AxisAlignedBB b) {
        return a.minY < b.maxY && a.maxY > b.minY;
    }
    public static boolean intersectsZ(AxisAlignedBB a, AxisAlignedBB b) {
        return a.minZ < b.maxZ && a.maxZ > b.minZ;
    }
}
