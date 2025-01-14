package com.melonstudios.melonlib.predicates;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class StackPredicateStackable extends StackPredicate {
    public static final StackPredicate instance = new StackPredicateStackable();
    @Override
    public boolean test(@Nonnull ItemStack stack) {
        return stack.isStackable();
    }
}
