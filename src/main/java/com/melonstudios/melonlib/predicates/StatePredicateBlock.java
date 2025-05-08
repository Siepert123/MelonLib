package com.melonstudios.melonlib.predicates;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

//Filters based on block
public class StatePredicateBlock extends StatePredicate {
    private final Block block;

    public StatePredicateBlock(Block block) {
        this.block = block;
    }

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() == this.block;
    }
}
