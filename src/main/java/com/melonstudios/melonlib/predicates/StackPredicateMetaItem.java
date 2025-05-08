package com.melonstudios.melonlib.predicates;

import com.melonstudios.melonlib.misc.MetaItem;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

//Filters based on the MetaItem representing the stack
public class StackPredicateMetaItem extends StackPredicate {
    private final MetaItem metaItem;

    public StackPredicateMetaItem(MetaItem metaItem) {
        this.metaItem = metaItem;
    }

    @Override
    public boolean test(@Nonnull ItemStack stack) {
        return MetaItem.of(stack).equals(metaItem);
    }
}
