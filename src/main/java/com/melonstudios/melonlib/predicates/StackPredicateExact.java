package com.melonstudios.melonlib.predicates;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class StackPredicateExact extends StackPredicate {
    private final ItemStack stack;

    public StackPredicateExact(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public boolean test(@Nonnull ItemStack stack) {
        return ItemStack.areItemStackTagsEqual(stack, this.stack) && ItemStack.areItemStacksEqual(stack, this.stack);
    }
}
