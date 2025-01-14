package com.melonstudios.melonlib.predicates;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

public class StatePredicateFenceGate extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateFenceGate();

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() instanceof BlockFenceGate;
    }
}
