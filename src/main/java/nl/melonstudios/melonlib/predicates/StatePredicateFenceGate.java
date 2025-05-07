package nl.melonstudios.melonlib.predicates;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

//Filters based on if the block is an instance of BlockFenceGate
public class StatePredicateFenceGate extends StatePredicate {
    public static final StatePredicate instance = new StatePredicateFenceGate(); //only allow one instance to save memory

    private StatePredicateFenceGate() {}

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getBlock() instanceof BlockFenceGate;
    }
}
