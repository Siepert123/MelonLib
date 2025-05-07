package nl.melonstudios.melonlib.predicates;

import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

//Filters based on if the block is an instance of BlockFire
public class StatePredicateFire extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateFire(); //only allow one instance to save memory

    private StatePredicateFire() {}

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() instanceof BlockFire;
    }
}
