package com.melonstudios.melonlib.predicates;

import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

public class StatePredicateMeta extends StatePredicate {
    private static final StatePredicateMeta[] predicates = new StatePredicateMeta[16];

    static {
        for (int i = 0; i < 16; i++) {
            predicates[i] = new StatePredicateMeta(i);
        }
    }

    public static StatePredicateMeta get(int meta) {
        return predicates[meta];
    }

    private final int meta;

    private StatePredicateMeta(int meta) {
        this.meta = meta;
    }

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock().getMetaFromState(state) == meta;
    }
}
