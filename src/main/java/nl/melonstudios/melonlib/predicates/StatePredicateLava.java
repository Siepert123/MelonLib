package nl.melonstudios.melonlib.predicates;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import javax.annotation.Nonnull;

//Filters based on if the block is either lava or flowing lava
public class StatePredicateLava extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateLava(); //only allow one instance to save memory

    private StatePredicateLava() {}

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() == Blocks.LAVA || state.getBlock() == Blocks.FLOWING_LAVA;
    }
}
