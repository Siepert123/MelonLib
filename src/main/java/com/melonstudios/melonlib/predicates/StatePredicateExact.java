package com.melonstudios.melonlib.predicates;

import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

public class StatePredicateExact extends StatePredicate {
    private final IBlockState state;

    public StatePredicateExact(IBlockState state) {
        this.state = state;
    }

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.equals(this.state);
    }
}
