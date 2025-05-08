package com.melonstudios.melonlib.predicates;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

//Filters based on whether the itemstack is unstackable
public class StackPredicateUnstackable extends StackPredicate {
    public static final StackPredicate instance = new StackPredicateUnstackable(); //only allow one instance to save memory

    private StackPredicateUnstackable() {}

    @Override
    public boolean test(@Nonnull ItemStack stack) {
        return !stack.isStackable();
    }
}
