package com.melonstudios.melonlib.recipe;

/**
 * Thrown when something fails during recipe loading.
 * @since 1.10.0
 * @see UniversalRecipe
 */
public class RecipeException extends Exception {
    public RecipeException() {
        super();
    }

    public RecipeException(String message) {
        super(message);
    }

    public RecipeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecipeException(Throwable cause) {
        super(cause);
    }

    protected RecipeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
