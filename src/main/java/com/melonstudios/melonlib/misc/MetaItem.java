package com.melonstudios.melonlib.misc;

import com.melonstudios.melonlib.predicates.StackPredicate;
import com.melonstudios.melonlib.predicates.StackPredicateMetaItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MetaItem {
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

    public StackPredicate getPredicate() {
        return new StackPredicateMetaItem(this);
    }

    public static MetaItem of(Item item, int meta) {
        return new MetaItem(item, meta);
    }
    public static MetaItem of(ItemStack stack) {
        return new MetaItem(stack.getItem(), stack.getMetadata());
    }
    public static MetaItem of(Block block, int meta) {
        return of(Item.getItemFromBlock(block), meta);
    }
}
