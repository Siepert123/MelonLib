package com.melonstudios.melonlib.predicates;

import com.melonstudios.melonlib.misc.MetaBlock;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

public class StatePredicateMetaBlock extends StatePredicate {
    private final MetaBlock metaBlock;

    public StatePredicateMetaBlock(MetaBlock metaBlock) {
        this.metaBlock = metaBlock;
    }

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return MetaBlock.of(state).equals(this.metaBlock);
    }
}
