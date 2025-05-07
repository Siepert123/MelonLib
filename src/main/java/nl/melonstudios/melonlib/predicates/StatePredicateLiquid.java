package nl.melonstudios.melonlib.predicates;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

//Filters based on if the block is liquid
public class StatePredicateLiquid extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateLiquid(); //only allow one instance to save memory

    private StatePredicateLiquid() {}

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() instanceof BlockLiquid;
    }
}
