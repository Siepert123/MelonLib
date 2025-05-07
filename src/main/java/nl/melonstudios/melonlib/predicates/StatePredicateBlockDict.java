package nl.melonstudios.melonlib.predicates;

import nl.melonstudios.melonlib.blockdict.BlockDictionary;
import nl.melonstudios.melonlib.misc.MetaBlock;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

//Filters based on whether the passed blockstate is tagged with a certain blockdict entry
public class StatePredicateBlockDict extends StatePredicate {
    private final String ore;

    public StatePredicateBlockDict(String ore) {
        this.ore = ore;
    }

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return BlockDictionary.getOres(ore, false).contains(MetaBlock.of(state));
    }
}
