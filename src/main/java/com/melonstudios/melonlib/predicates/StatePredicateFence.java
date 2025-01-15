package com.melonstudios.melonlib.predicates;

import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

public class StatePredicateFence extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateFence();

    private StatePredicateFence() {}

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() instanceof BlockFence;
    }
}
