package com.melonstudios.melonlib.misc;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for advancements
 * @since 1.2
 */
public class AdvancementUtil {
    /**
     * A cache of achievements so that the server does not have to search for the advancement every time
     */
    private static final Map<ResourceLocation, Advancement> cache = new HashMap<>();
    public static void clearCache() {
        cache.clear();
    }

    /**
     * Finds the Advancement with the passed ID.
     * @param id The resource location that the advancement is located at.
     * @return The advancement, or null if it is invalid.
     */
    @Nullable
    public static Advancement getAdvancement(@Nonnull ResourceLocation id) {
        if (cache.containsKey(id)) return cache.get(id);
        MinecraftServer server = ServerHack.exists() ? ServerHack.getServer() : FMLCommonHandler.instance().getMinecraftServerInstance();
        Advancement advancement = server.getAdvancementManager().getAdvancement(id);
        if (advancement != null) cache.put(id, advancement);
        return advancement;
    }

    /**
     * Grants an advancement to a player.
     * @param player The player to grant the advancement to
     * @param advancement The advancement to grant
     */
    public static void grantAdvancement(@Nonnull EntityPlayerMP player, @Nullable Advancement advancement) {
        if (advancement == null) return;
        PlayerAdvancements advancements = player.getAdvancements();
        AdvancementProgress progress = advancements.getProgress(advancement);

        for (String criterion : progress.getRemaningCriteria()) {
            advancements.grantCriterion(advancement, criterion);
        }
    }

    public static void grantAdvancement(@Nonnull EntityPlayer player, @Nullable Advancement advancement) {
        if (player instanceof EntityPlayerMP) grantAdvancement((EntityPlayerMP) player, advancement);
    }

    public static void grantAdvancement(@Nonnull EntityPlayerMP player, @Nonnull ResourceLocation advancement) {
        grantAdvancement(player, getAdvancement(advancement));
    }

    public static void grantAdvancement(@Nonnull EntityPlayer player, @Nonnull ResourceLocation advancement) {
        if (player instanceof EntityPlayerMP)  grantAdvancement((EntityPlayerMP) player, advancement);
    }
}
