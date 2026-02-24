package com.melonstudios.melonlib.misc;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    public static boolean itemMatches(ItemStack example, ItemStack input) {
        if (example.isEmpty() != input.isEmpty()) return false;
        if (example.getItem() == input.getItem()) {
            if (example.getItem().getHasSubtypes()) {
                return (example.getMetadata() == input.getMetadata()) || (example.getMetadata() == OreDictionary.WILDCARD_VALUE);
            }
            return true;
        }
        return false;
    }

    public static void writeItemStack(ItemStack stack, ByteBuf buf, boolean withSize, boolean withNBT) {
        Item item = stack.getItem();
        int count = stack.getCount();
        int damage = stack.getItemDamage();
        boolean useID = item.getRegistryName().getResourceDomain().equals("minecraft");
        buf.writeBoolean(useID);
        if (useID) buf.writeInt(Item.getIdFromItem(item));
        else {
            String itemID = String.valueOf(item.getRegistryName());
            buf.writeInt(itemID.length());
            buf.writeCharSequence(itemID, StandardCharsets.UTF_8);
        }
        if (withSize) buf.writeByte(count);;
        buf.writeShort(damage);
        if (withNBT) {
            NBTTagCompound itemNBT = stack.getTagCompound();
            if (itemNBT != null) {
                buf.writeBoolean(true);
                new PacketBuffer(buf).writeCompoundTag(itemNBT);
            } else {
                buf.writeBoolean(false);
            }
        }
    }

    public static ItemStack readItemStack(ByteBuf buf, boolean withSize, boolean withNBT) throws IOException {
        boolean useID = buf.readBoolean();
        Item item = useID ? Item.getItemById(buf.readInt()) : ForgeRegistries.ITEMS.getValue(readRL(buf));
        int count = withSize ? buf.readUnsignedByte() : 1;
        int damage = buf.readUnsignedShort();
        assert item != null;
        if (withNBT) {
            boolean hasNBT = buf.readBoolean();
            if (hasNBT) return new ItemStack(item, count, damage, new PacketBuffer(buf).readCompoundTag());
        }
        return new ItemStack(item, count, damage);
    }

    private static ResourceLocation readRL(ByteBuf buf) {
        int len = buf.readInt();
        String unparsed = buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
        return new ResourceLocation(unparsed);
    }
}
