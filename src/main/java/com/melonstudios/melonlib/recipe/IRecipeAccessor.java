package com.melonstudios.melonlib.recipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public interface IRecipeAccessor<T> {
    @Nullable
    T getRecipe(@Nonnull String recipeID);

    boolean hasRecipe(@Nonnull String recipeID);

    @Nonnull
    Collection<String> getAllRecipeIDs();
}
