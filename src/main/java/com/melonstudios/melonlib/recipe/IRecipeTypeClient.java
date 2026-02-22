package com.melonstudios.melonlib.recipe;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

@SideOnly(Side.CLIENT)
@SuppressWarnings("unused")
public interface IRecipeTypeClient<T, R extends ISyncedRecipeType<T>> {
    /**
     * Retrieves a recipe from the recipe type with the associated recipe ID.
     * @param recipeID The ID of the recipe. If no recipe with the same ID exists, return null.
     * @return The recipe associated with the recipe ID, or null if no recipe with that ID exists.
     */
    @Nullable
    T getRecipe(@Nonnull String recipeID);
    /** Checks if a recipe with a certain recipe ID exists.
     * @param recipeID The ID of the recipe to check for.
     * @return True if the recipe is in this recipe type, false otherwise.
     */
    boolean hasRecipe(@Nonnull String recipeID);

    /**
     * Retrieves all recipe IDs currently in the recipe type.
     * The returned collection should not be modified; it may break things, or it could be immutable and throw an exception.
     * @return All currently registered recipe IDs.
     */
    @Nonnull
    Collection<String> getAllRecipeIDs();
    /**
     * Retrieves all recipes currently in the recipe type.
     * The returned collection should not be modified; it may break things, or it could be immutable and throw an exception.
     * @return All currently registered recipes.
     */
    @Nonnull
    Collection<T> getAllRecipes();

    @Nonnull
    Map<String, T> getRecipeMap();

    //FOR SYNCHRONIZATION PURPOSES - DO NOT CALL THIS!!!
    /**
     * Prepares for incoming recipe data; should clear all data.
     * Basically resets the client recipe type to be completely empty.
     */
    void prepareForData();
    /**
     * Adds all recipes from the local recipe type.
     * Should make the client recipe type a near 1:1 copy of the server recipe type.
     */
    void addFromLocal(@Nonnull R local);
    /**
     * Adds a recipe from the remote server recipe type.
     * Used when recipes are sent over the network.
     */
    void addFromRemote(@Nonnull String recipeID, @Nonnull T recipe);
}
