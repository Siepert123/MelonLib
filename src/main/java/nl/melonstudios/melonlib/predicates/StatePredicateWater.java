package nl.melonstudios.melonlib.predicates;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import javax.annotation.Nonnull;

//Filters based on if the block is either water or flowing water
public class StatePredicateWater extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateWater(); //only allow one instance to save memory

    private StatePredicateWater() {}

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER;
    }
}
