package nl.melonstudios.melonlib.predicates;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

//Filters based on whether the block extends a certain class
public class StatePredicateClass extends StatePredicate {
    private final Class<? extends Block> clazz;
    private final boolean exact;

    public StatePredicateClass(Class<? extends Block> clazz, boolean allowInheritance) {
        this.clazz = clazz;
        this.exact = allowInheritance;
    }

    @Override
    public boolean test(@Nonnull IBlockState state) {
        if (exact) {
            return state.getBlock().getClass() == this.clazz;
        }
        return state.getBlock().getClass().isAssignableFrom(this.clazz);
    }
}
