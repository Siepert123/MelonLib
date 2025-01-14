package com.melonstudios.melonlib.predicates;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class StackPredicateDamage extends StackPredicate {
    private final int damage;

    public StackPredicateDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public boolean test(@Nonnull ItemStack stack) {
        return stack.getItemDamage() == damage;
    }
}
