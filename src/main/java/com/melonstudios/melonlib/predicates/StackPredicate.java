package com.melonstudios.melonlib.predicates;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public abstract class StackPredicate implements Predicate<ItemStack> {
    public static boolean anyMatch(Predicate<ItemStack> predicate, Iterable<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (predicate.test(stack)) return true;
        }
        return false;
    }

    protected StackPredicate() {

    }

    @Override
    public abstract boolean test(@Nonnull ItemStack stack);
}
