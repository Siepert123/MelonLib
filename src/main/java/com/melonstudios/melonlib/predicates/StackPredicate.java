package com.melonstudios.melonlib.predicates;

import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public abstract class StackPredicate implements Predicate<ItemStack> {
    public static boolean anyMatch(Predicate<ItemStack> predicate, Iterable<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (predicate.test(stack)) return true;
        }
        return false;
    }

    @Override
    public abstract boolean test(ItemStack stack);
}
