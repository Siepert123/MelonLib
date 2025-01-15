package com.melonstudios.melonlib.predicates;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import javax.annotation.Nonnull;

public class StatePredicateWater extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateWater();

    private StatePredicateWater() {}

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER;
    }
}
