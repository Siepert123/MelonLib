package com.melonstudios.melonlib.predicates;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import javax.annotation.Nonnull;

public class StatePredicateLava extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateLava();

    private StatePredicateLava() {}

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() == Blocks.LAVA || state.getBlock() == Blocks.FLOWING_LAVA;
    }
}
