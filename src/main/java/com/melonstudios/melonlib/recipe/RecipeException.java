package com.melonstudios.melonlib.recipe;

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
