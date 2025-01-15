package com.melonstudios.melonlib.predicates;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("all")
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

    @Override
    public final boolean apply(@Nullable ItemStack input) {
        return input != null && test(input);
    }
}
