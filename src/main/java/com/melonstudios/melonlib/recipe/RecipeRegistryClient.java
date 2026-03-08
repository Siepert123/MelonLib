package com.melonstudios.melonlib.recipe;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class RecipeRegistryClient {
    static final Map<String, IRecipeTypeClient<?, ?>> CLIENT_RECIPE_TYPES = new HashMap<>();
}
