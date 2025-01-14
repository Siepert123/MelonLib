package com.melonstudios.melonlib.predicates;

import com.melonstudios.melonlib.misc.MetaItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

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
