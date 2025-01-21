package com.melonstudios.melonlib.math;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class ItemStackSorter implements Comparator<ItemStack> {
    public static final ItemStackSorter instance = new ItemStackSorter();

    private ItemStackSorter() {}

    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        int id1 = Item.getIdFromItem(o1.getItem());
        int id2 = Item.getIdFromItem(o2.getItem());
        int meta1 = o1.getMetadata();
        int meta2 = o2.getMetadata();

        if (id1 == id2) {
            if (meta1 == meta2) return 0;
            return meta1 > meta2 ? 1 : -1;
        }
        return id1 > id2 ? 1 : -1;
    }
}
