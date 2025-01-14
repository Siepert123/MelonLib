package com.melonstudios.melonlib.misc;

import com.melonstudios.melonlib.predicates.StatePredicate;
import com.melonstudios.melonlib.predicates.StatePredicateMetaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class MetaBlock {
    private final Block block;
    private final int metadata;

    private MetaBlock(Block block, int meta) {
        this.block = block;
        this.metadata = meta;
    }

    public Block getBlock() {
        return block;
    }
    public int getMetadata() {
        return metadata;
    }

    public int getStateId() {
        return hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s/%s", block.getLocalizedName(), metadata);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MetaBlock) {
            MetaBlock metaBlock = (MetaBlock) obj;
            return metaBlock.block.equals(block) && metaBlock.metadata == metadata;
        }
        if (obj instanceof IBlockState) {
            MetaBlock metaBlock = MetaBlock.of((IBlockState) obj);
            return metaBlock.equals(this);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Block.getStateId(block.getStateFromMeta(metadata));
    }

    public StatePredicate getPredicate() {
        return new StatePredicateMetaBlock(this);
    }

    public static MetaBlock of(Block block, int meta) {
        return new MetaBlock(block, meta);
    }
    public static MetaBlock of(IBlockState state) {
        return new MetaBlock(state.getBlock(), state.getBlock().getMetaFromState(state));
    }
    public static MetaBlock of(int stateId) {
        return of(Block.getStateById(stateId));
    }
}
