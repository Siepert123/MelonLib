package com.melonstudios.melonlib.imc;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.api.DimensionTeleporterSoundFix;
import com.melonstudios.melonlib.recipe.RecipeException;
import com.melonstudios.melonlib.recipe.RecipeRegistry;
import com.melonstudios.melonlib.recipe.UniversalRecipe;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class IMCHandler {
    public static void process(FMLInterModComms.IMCMessage message) {
        String sender = message.getSender();
        String key = message.key;
        if ("DimensionTeleporterSoundFix".equalsIgnoreCase(key)) {
            if (!message.isStringMessage()) {
                MelonLib.logger.warn("Received malformed DimensionTeleporterSoundFix IMC message from {}", sender);
                return;
            }
            try {
                Class<?> clazz = Class.forName(message.getStringValue());
                // noinspection unchecked
                DimensionTeleporterSoundFix.add((Class<? extends ITeleporter>) clazz);
                MelonLib.logger.debug("Received DimensionTeleporterSoundFix IMC message for {}", clazz.toString());
            } catch (Exception e) {
                MelonLib.logger.warn("Received malformed DimensionTeleporterSoundFix IMC message: {}", e.toString());
            }
            return;
        }
        if ("AddRecipe".equalsIgnoreCase(key)) {
            if (!message.isNBTMessage()) {
                MelonLib.logger.warn("Received malformed AddRecipe IMC message from {}", sender);
            }

            try {
                UniversalRecipe recipe = UniversalRecipe.read(message.getNBTValue());

                RecipeRegistry.processUniversalRecipe(sender, recipe);
            } catch (RecipeException e) {
                MelonLib.logger.warn("Exception reading recipe: {} (IMC received from: {})", e.toString(), sender);
            }
        }
    }
}
