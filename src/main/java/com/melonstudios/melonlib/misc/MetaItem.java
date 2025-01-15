package com.melonstudios.melonlib.misc;

import com.melonstudios.melonlib.predicates.StackPredicate;
import com.melonstudios.melonlib.predicates.StackPredicateMetaItem;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * An Item that has some Metadata packaged with it. Used for predicates.
 * @since 1.0
 * @see MetaBlock
 * @see StackPredicateMetaItem
 */
public class MetaItem {
    public static final MetaItem AIR = new MetaItem(Items.AIR, 0);

    private final Item item;
    private final int metadata;

    private MetaItem(@Nonnull Item item, int metadata) {
        this.item = item;
        this.metadata = metadata;
        if (item.getRegistryName() == null) throw new NullPointerException("Item registry name is null!");
    }

    public Item getItem() {
        return item;
    }
    public int getMetadata() {
        return metadata;
    }

    /**
     * Creates an ItemStack with size 1 with the item and metadata info of this MetaItem.
     * @return The newly created ItemStack.
     */
    public ItemStack asItemStack() {
        return new ItemStack(item, 1, metadata);
    }

    @Override
    public String toString() {
        return String.format("%s/%s", item.getRegistryName(), metadata);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MetaItem) {
            MetaItem metaItem = (MetaItem) obj;
            return metaItem.item == this.item && metaItem.metadata == this.metadata;
        }
        if (obj instanceof ItemStack) {
            MetaItem metaItem = MetaItem.of((ItemStack) obj);
            return metaItem.equals(this);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Item.getIdFromItem(item) + (metadata << 16);
    }

    /**
     * Creates a new StackPredicate based on this MetaItem.
     * @return A new StackPredicateMetaItem with this as filter
     * @since 1.0
     */
    public StackPredicate getPredicate() {
        return new StackPredicateMetaItem(this);
    }

    /**
     * Checks if this MetaItem is air.
     * @return Whether this MetaItem is air.
     * @since 1.0
     */
    public boolean isAir() {
        return this == AIR || this.getItem() == null || this.getItem() == Items.AIR;
    }

    /**
     * Creates a new MetaItem, or AIR if the item is air.
     * @param item The item to represent
     * @param meta The packaged metadata
     * @return The new instance of the MetaItem
     * @since 1.0
     */
    public static MetaItem of(Item item, int meta) {
        if (item == Items.AIR) return AIR;
        return new MetaItem(item, meta);
    }

    /**
     * Creates a new MetaItem, or AIR if the stack is empty.
     *
     * @param stack The itemstack to package into the MetaItem
     * @return The new instance of the MetaItem
     * @since 1.0
     */
    public static MetaItem of(ItemStack stack) {
        if (stack.isEmpty()) return AIR;
        return new MetaItem(stack.getItem(), stack.getMetadata());
    }

    /**
     * Creates a new MetaItem, or AIR if the block has no represented item.
     *
     * @param block The block form of the item to represent
     * @param meta The packaged metadata
     * @return The new instance of the MetaItem
     * @since 1.0
     */
    public static MetaItem of(Block block, int meta) {
        if (block == Blocks.AIR) return AIR;
        return of(Item.getItemFromBlock(block), meta);
    }
}
