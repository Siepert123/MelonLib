package nl.melonstudios.melonlib.api.blockdisplay;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

/**
 * A functional interface for if you wish to provide additional information to a block.
 * @since 1.2
 */
@SuppressWarnings("unused")
@FunctionalInterface
public interface IAdditionalBlockInfo {
    IAdditionalBlockInfo EMPTY = (world, pos, state) -> Collections.emptyList();
    List<String> getAdditionalBlockInfo(World world, BlockPos pos, IBlockState state);
}
