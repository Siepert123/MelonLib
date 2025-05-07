package nl.melonstudios.melonlib.predicates;

import nl.melonstudios.melonlib.misc.MetaItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

//Filters based on whether the passed blockstate is tagged with a certain oredict entry
public class StackPredicateOreDict extends StackPredicate {
    private final String oreDict;

    public StackPredicateOreDict(String ore) {
        this.oreDict = ore;
    }

    @Override
    public boolean test(@Nonnull ItemStack stack) {
        return OreDictionary.getOres(this.oreDict).stream().anyMatch(new StackPredicateMetaItem(MetaItem.of(stack)));
    }
}
