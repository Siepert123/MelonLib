package com.melonstudios.melonlib.predicates;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

//Filters based on whether the itemstack is stackable
public class StackPredicateStackable extends StackPredicate {
    public static final StackPredicate instance = new StackPredicateStackable(); //only allow one instance to save memory

    private StackPredicateStackable() {}

    @Override
    public boolean test(@Nonnull ItemStack stack) {
        return stack.isStackable();
    }
}
