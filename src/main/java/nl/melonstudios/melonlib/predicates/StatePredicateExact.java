package nl.melonstudios.melonlib.predicates;

import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

//Filters based on whether the blockstate matches exactly
public class StatePredicateExact extends StatePredicate {
    private final IBlockState state;

    public StatePredicateExact(IBlockState state) {
        this.state = state;
    }

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.equals(this.state);
    }
}
