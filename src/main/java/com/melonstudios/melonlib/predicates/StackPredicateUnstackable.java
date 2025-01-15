package com.melonstudios.melonlib.predicates;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class StackPredicateUnstackable extends StackPredicate {
    public static final StackPredicate instance = new StackPredicateUnstackable();

    private StackPredicateUnstackable() {}

    @Override
    public boolean test(@Nonnull ItemStack stack) {
        return !stack.isStackable();
    }
}
