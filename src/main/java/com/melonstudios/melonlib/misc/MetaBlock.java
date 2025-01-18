package com.melonstudios.melonlib.misc;

import com.melonstudios.melonlib.blockdict.BlockDictionary;
import com.melonstudios.melonlib.predicates.StatePredicate;
import com.melonstudios.melonlib.predicates.StatePredicateMetaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * A Block that has Metadata packaged with it.
 * Used for predicates and the BlockDictionary.
 * @since 1.0
 * @see MetaItem
 * @see StatePredicateMetaBlock
 * @see BlockDictionary
 */
public class MetaBlock {
    public static final MetaBlock AIR = new MetaBlock(Blocks.AIR, 0);

    //It may occur that many equal MetaBlocks will be created. This cache will save a little bit of memory
    private static final Map<Block, MetaBlock[]> cache = new HashMap<>();

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
     * @since 1.0
     */
    public StatePredicate getPredicate() {
        return new StatePredicateMetaBlock(this);
    }

    /**
     * Checks if this MetaBlock is air.
     * @return Whether this MetaBlock is air.
     * @since 1.0
     */
    public boolean isAir() {
        return this == AIR || this.getBlock() == null || this.getBlock() == Blocks.AIR;
    }

    /**
     * Creates a new MetaBlock, or AIR if the block is air.
     *
     * @param block The block to represent
     * @param meta The packaged metadata
     * @return The new instance of the MetaBlock
     * @since 1.0
     */
    public static MetaBlock of(Block block, int meta) {
        if (block == Blocks.AIR) return AIR;
        if (cache.get(block) == null) {
            MetaBlock[] cacheEntries = new MetaBlock[16];
            for (int i = 0; i < 16; i++) {
                cacheEntries[i] = new MetaBlock(block, i);
            }
            cache.put(block, cacheEntries);
        }
        return cache.get(block)[meta];
    }

    /**
     * Creates a new MetaBlock, or AIR if the state is based on air.
     *
     * @param state The blockstate to package into the MetaBlock
     * @return The new instance of the MetaBlock
     * @since 1.0
     */
    public static MetaBlock of(IBlockState state) {
        if (state.getBlock() == Blocks.AIR) return AIR;
        if (cache.get(state.getBlock()) == null) {
            MetaBlock[] cacheEntries = new MetaBlock[16];
            for (int i = 0; i < 16; i++) {
                cacheEntries[i] = new MetaBlock(state.getBlock(), i);
            }
            cache.put(state.getBlock(), cacheEntries);
        }
        return cache.get(state.getBlock())[state.getBlock().getMetaFromState(state)];
    }

    /**
     * Creates a new MetaBlock, or AIR if the state id is 0.
     *
     * @param stateId The state ID of the packaged blockstate
     * @return The new instance of the MetaBlock
     * @since 1.0
     */
    public static MetaBlock of(int stateId) {
        if (stateId == 0) return AIR;
        return of(Block.getStateById(stateId));
    }
}
