package com.melonstudios.melonlib.recipe;

import com.melonstudios.melonlib.MelonLib;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Map;

/**
 * Handles the synchronization of recipe types on a separate thread for performance.
 */
public class ThreadSynchronizeRecipes extends Thread {
    private final EntityPlayerMP player;
    public ThreadSynchronizeRecipes(EntityPlayerMP player) {
        this.player = player;
    }

    @Override
    public void run() {
        MelonLib.logger.info("Player {} connected, sending recipes", this.player.getName());

        try {
            for (Map.Entry<String, IRecipeType<?>> entry : RecipeRegistry.SERVER_RECIPE_TYPES.entrySet()) {
                if (this.checkConnection()) break;
                String typeID = entry.getKey();
                IRecipeType<?> type = entry.getValue();
                if (type.shouldSynchronize()) {
                    ISyncedRecipeType<?> synced = (ISyncedRecipeType<?>) type;
                    MelonLib.proxy.sendRecipes(this.player, typeID, synced);
                }
            }
        } catch (Throwable e) {
            MelonLib.logger.error("Exception in sending recipes to {}: {}", this.player.getName(), e.toString());
        }
    }

    private boolean checkConnection() {
        if (this.player.hasDisconnected()) {
            MelonLib.logger.info("Player {} disconnected, aborting recipe sending", this.player.getName());
            return true;
        } else return false;
    }
}
