package com.melonstudios.melonlib.api.blockdisplay;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface IAdditionalBlockInfo {
    IAdditionalBlockInfo EMPTY = (world, pos, state) -> Collections.emptyList();
    List<String> getAdditionalBlockInfo(World world, BlockPos pos, IBlockState state);
}
