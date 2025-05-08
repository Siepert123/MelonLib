package com.melonstudios.melonlib.misc;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;

/**
 * A secondary way to access the {@link MinecraftServer} instance.
 * @since 1.2
 * @see FMLCommonHandler#getMinecraftServerInstance()
 */
public class ServerHack {
    private static MinecraftServer server = null;
    public static boolean exists() {
        return server != null;
    }

    public static void setServer(@Nonnull MinecraftServer mcServer) {
        server = mcServer;
    }
    public static void removeServer() {
        server = null;
    }
    @Nonnull
    public static MinecraftServer getServer() {
        if (!exists()) throw new NullPointerException("Server does not exist!");
        return server;
    }
}
