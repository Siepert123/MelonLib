package com.melonstudios.melonlib.api.blockdisplay;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

@FunctionalInterface
public interface IAdditionalBlockInfo {
    List<ITextComponent> getAdditionalBlockInfo(World world, BlockPos pos, IBlockState state);
}
