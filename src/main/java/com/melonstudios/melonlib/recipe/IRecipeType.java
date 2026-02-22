package com.melonstudios.melonlib.recipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

/**
 * The IRecipeType is used to register any recipe type that can be accessed via MelonLoader's IMC.
 * However, this recipe type class is only for the server-side.
 * In the case that {@link #shouldSynchronize()} returns {@code true},
 * MelonLib will send packets to the client to update the respective
 * {@link IRecipeTypeClient} with the contents of this recipe type.
 * Do note that for recipe synchronization to work, the type must be an instance of {@link ISyncedRecipeType},
 * and both the {@link IRecipeType} and {@link IRecipeTypeClient} must be registered in the {@link RecipeRegistry}!
 * @since 1.10.0
 * @see RecipeRegistry
 * @see ISyncedRecipeType
 * @see IRecipeTypeClient
 * @param <T> The recipe object type parameter
 */
@SuppressWarnings("unused")
public interface IRecipeType<T> {
    /**
     * Determines whether MelonLib should synchronize this recipe type to the client.
     * When it returns true, the class will be cast to {@link ISyncedRecipeType}!!!
     * As such it is VITAL that this method returns true if and ONLY IF the class is an instance of
     * {@link ISyncedRecipeType}!!!
     * Apart from that, the method is allowed to return false even if the class
     * is an instance of {@link ISyncedRecipeType}. If, for example, the sync can be configured,
     * the class will always implement {@link ISyncedRecipeType} but may not always want to be sent to the client.
     */
    default boolean shouldSynchronize() {
        return false;
    }

    /**
     * Adds a new recipe to this recipe type.
     * @param recipeID The ID of the recipe. If another recipe with the same ID already exists, it should be replaced.
     * @param recipe The recipe to add.
     */
    void addRecipe(@Nonnull String recipeID, @Nonnull T recipe);

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
     * Removed a recipe with a certain recipe ID. If the recipe didn't exist in the first place, nothing happens.
     * @param recipeID The ID of the recipe to remove.
     */
    void removeRecipe(@Nonnull String recipeID);

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

    /**
     * Converts a {@link UniversalRecipe} into a local recipe.
     * The Universal Recipe may be interpreted in any way, so long it is consistent for this recipe type.
     * @param universal The Universal Recipe to convert.
     * @return The local recipe for this type.
     * @throws RecipeException When the Universal Recipe is invalid for this recipe type
     */
    @Nonnull
    T convert(@Nonnull UniversalRecipe universal) throws RecipeException;
}
