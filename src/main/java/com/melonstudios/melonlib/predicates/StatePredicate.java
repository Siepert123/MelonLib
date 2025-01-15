package com.melonstudios.melonlib.predicates;

import com.google.common.base.Predicate;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("all")
public abstract class StatePredicate implements Predicate<IBlockState> {
    public static boolean anyMatch(Predicate<IBlockState> predicate, Iterable<IBlockState> states) {
        for (IBlockState state : states) {
            if (predicate.test(state)) return true;
        }
        return false;
    }

    protected StatePredicate() {}

    @Override
    public abstract boolean test(@Nonnull IBlockState state);

    @Override
    public final boolean apply(@Nullable IBlockState input) {
        return input != null && test(input);
    }
}
