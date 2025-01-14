package com.melonstudios.melonlib.predicates;

import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

public class StatePredicateFire extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateFire();

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() instanceof BlockFire;
    }
}
