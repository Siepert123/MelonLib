package com.melonstudios.melonlib.predicates;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

//Filters based on if the itemstack is EXACTLY the same as the filter
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
