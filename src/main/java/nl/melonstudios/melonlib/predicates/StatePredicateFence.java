package nl.melonstudios.melonlib.predicates;

import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

//Filters based on if the block is an instance of BlockFence
public class StatePredicateFence extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateFence(); //only allow one instance to save memory

    private StatePredicateFence() {}

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() instanceof BlockFence;
    }
}
