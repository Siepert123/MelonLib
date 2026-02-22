package com.melonstudios.melonlib.event;

import com.melonstudios.melonlib.recipe.RecipeException;
import com.melonstudios.melonlib.recipe.RecipeRegistry;
import com.melonstudios.melonlib.recipe.UniversalRecipe;
import net.minecraftforge.fml.common.eventhandler.Event;

@SuppressWarnings("unused")
public class RegisterRecipesEvent extends Event {
    public RegisterRecipesEvent() {

    }

    public void registerRecipe(UniversalRecipe recipe) throws RecipeException {
        RecipeRegistry.processUniversalRecipe("RegisterRecipesEvent", recipe);
    }
}
