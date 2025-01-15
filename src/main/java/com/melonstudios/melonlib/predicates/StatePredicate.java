package com.melonstudios.melonlib.predicates;

import com.google.common.base.Predicate;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A predicate for IBlockStates.
 * Can be passed into a function requiring a {@code Predicate<IBlockState>}.
 *
 * @since 1.0
 * @see StackPredicate
 * @see StatePredicateMetaBlock
 * @see StatePredicateExact
 */
@SuppressWarnings("all")
public abstract class StatePredicate implements Predicate<IBlockState> {
    public static boolean anyMatch(Predicate<IBlockState> predicate, Iterable<IBlockState> states) {
        for (IBlockState state : states) {
            if (predicate.test(state)) return true;
        }
        return false;
    }

    public StatePredicate() {}

    @Override
    public abstract boolean test(@Nonnull IBlockState state);

    @Override
    public final boolean apply(@Nullable IBlockState input) {
        return input != null && test(input);
    }
}
