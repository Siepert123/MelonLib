package com.melonstudios.melonlib.recipe;

import com.melonstudios.melonlib.MelonLib;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class RecipeRegistry {
    static final Map<String, IRecipeType<?>> SERVER_RECIPE_TYPES = new HashMap<>();
    @SideOnly(Side.CLIENT)
    static final Map<String, IRecipeTypeClient<?, ?>> CLIENT_RECIPE_TYPES = new HashMap<>();

    public static void registerServer(String id, IRecipeType<?> type) {
        SERVER_RECIPE_TYPES.put(id, type);
    }
    @SideOnly(Side.CLIENT)
    public static void registerClient(String id, IRecipeTypeClient<?, ?> type) {
        CLIENT_RECIPE_TYPES.put(id, type);
    }

    public static <T> IRecipeType<T> getRecipeType(String id) {
        return (IRecipeType<T>) SERVER_RECIPE_TYPES.get(id);
    }
    @SideOnly(Side.CLIENT)
    public static <T, R extends ISyncedRecipeType<T>> IRecipeTypeClient<T, R> getRecipeTypeClient(String id) {
        return (IRecipeTypeClient<T, R>) CLIENT_RECIPE_TYPES.get(id);
    }

    public static <T> void processUniversalRecipe(String sender, UniversalRecipe recipe) throws RecipeException {
        String typeID = recipe.recipeType;
        IRecipeType<T> type = getRecipeType(typeID);
        if (type == null) return;
        String recipeID = recipe.recipeID;
        T converted = type.convert(recipe);
        type.addRecipe(recipeID, converted);
        MelonLib.logger.debug("{} added recipe {} to recipe type {}", sender, recipeID, typeID);
    }
}
