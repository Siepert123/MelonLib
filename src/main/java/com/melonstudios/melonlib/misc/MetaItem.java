package com.melonstudios.melonlib.misc;

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
}
