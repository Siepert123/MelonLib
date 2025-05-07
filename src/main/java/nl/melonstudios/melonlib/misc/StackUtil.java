package nl.melonstudios.melonlib.misc;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Utilities for Items.
 * @since 1.0
 */
public class StackUtil {
    /**
     * Drops items on a set location, as if they were dropped from a broken block.
     * @param world World
     * @param pos The position to summon the items at
     * @param stacks The items to spawn
     * @since 1.0
     */
    public static void dropItemsAt(World world, BlockPos pos, ItemStack... stacks) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;

        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                world.spawnEntity(new EntityItem(world, x, y, z, stack));
            }
        }
    }

    /**
     * Spawns item at a certain location with a set velocity.
     * @param world World
     * @param x X-position of the item
     * @param y Y-position of the item
     * @param z Z-position of the item
     * @param stack The itemstack of the item
     * @param vx X-velocity of the item
     * @param vy Y-velocity of the item
     * @param vz Z-velocity of the item
     * @since 1.0
     */
    public static void spawnItemWithVelocity(World world, double x, double y, double z, ItemStack stack, double vx, double vy, double vz) {
        if (!stack.isEmpty()) {
            EntityItem entity = new EntityItem(world, x, y, z, stack);
            entity.motionX = vx;
            entity.motionY = vy;
            entity.motionZ = vz;
            world.spawnEntity(entity);
        }
    }
    public static void spawnItemNoVelocity(World world, double x, double y, double z, ItemStack stack) {
        spawnItemWithVelocity(world, x, y, z, stack, 0, 0, 0);
    }
    public static void spawnItemDefaultVelocity(World world, double x, double y, double z, ItemStack stack) {
        if (!stack.isEmpty()) world.spawnEntity(new EntityItem(world, x, y, z, stack));
    }
}
