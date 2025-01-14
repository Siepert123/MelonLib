package com.melonstudios.melonlib.predicates;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class StackPredicateItem extends StackPredicate {
    private final Item item;

    public StackPredicateItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean test(@Nonnull ItemStack stack) {
        return stack.getItem() == this.item;
    }
}
