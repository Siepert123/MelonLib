package com.melonstudios.melonlib.predicates;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

//Filters based on the material of the block
public class StatePredicateMaterial extends StatePredicate {
    private final Material material;

    public StatePredicateMaterial(Material material) {
        this.material = material;
    }

    @Override
    public boolean test(@Nonnull IBlockState state) {
        return state.getMaterial() == this.material;
    }
}
