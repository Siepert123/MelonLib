package com.melonstudios.melonlib.predicates;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A predicate for ItemStacks.
 * Can be passed into a function requiring a {@code Predicate<ItemStack>}.
 *
 * @since 1.0
 * @see StatePredicate
 * @see StackPredicateMetaItem
 * @see StackPredicateExact
 */
@SuppressWarnings("all")
public abstract class StackPredicate implements Predicate<ItemStack> {
    public static boolean anyMatch(Predicate<ItemStack> predicate, Iterable<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (predicate.test(stack)) return true;
        }
        return false;
    }

    public StackPredicate() {}

    @Override
    public abstract boolean test(@Nonnull ItemStack stack);

    @Override
    public final boolean apply(@Nullable ItemStack input) {
        return input != null && test(input);
    }
}
