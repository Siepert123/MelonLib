package com.melonstudios.melonlib.misc;

import com.melonstudios.melonlib.predicates.StatePredicate;
import com.melonstudios.melonlib.predicates.StatePredicateMetaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import javax.annotation.Nonnull;

public class MetaBlock {
    public static final MetaBlock AIR = new MetaBlock(Blocks.AIR, 0);

    private final Block block;
    private final int metadata;

    private MetaBlock(@Nonnull Block block, int meta) {
        this.block = block;
        this.metadata = meta;
        if (block.getRegistryName() == null) throw new NullPointerException("Block registry name is null!");
    }

    public Block getBlock() {
        return block;
    }
    public int getMetadata() {
        return metadata;
    }

    /**
     * @return The state ID of the represented blockstate.
     */
    public int getStateId() {
        return hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s/%s", block.getRegistryName(), metadata);
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
    @SuppressWarnings("deprecation")
    public int hashCode() {
        return Block.getStateId(block.getStateFromMeta(metadata));
    }

    /**
     * Creates a new StatePredicate based on this MetaBlock.
     * @return A new StatePredicateMetaBlock with this as filter
     */
    public StatePredicate getPredicate() {
        return new StatePredicateMetaBlock(this);
    }

    /**
     * Checks if this MetaBlock is air.
     * @return Whether this MetaBlock is air.
     */
    public boolean isAir() {
        return this == AIR || this.getBlock() == null || this.getBlock() == Blocks.AIR;
    }

    public static MetaBlock of(Block block, int meta) {
        if (block == Blocks.AIR) return AIR;
        return new MetaBlock(block, meta);
    }
    public static MetaBlock of(IBlockState state) {
        if (state.getBlock() == Blocks.AIR) return AIR;
        return new MetaBlock(state.getBlock(), state.getBlock().getMetaFromState(state));
    }
    public static MetaBlock of(int stateId) {
        if (stateId == 0) return AIR;
        return of(Block.getStateById(stateId));
    }
}
