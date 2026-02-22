package com.melonstudios.melonlib.sided;

import com.melonstudios.melonlib.network.PacketSyncTE;
import com.melonstudios.melonlib.recipe.ISyncedRecipeType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public abstract class AbstractProxy {
    public abstract Side getSide();
    public abstract void registerClientTESync(SimpleNetworkWrapper net);
    public void packetSyncTE(PacketSyncTE packet, IMessageHandler<PacketSyncTE, IMessage> handler, MessageContext ctx) {

    }

    public abstract <T, R extends ISyncedRecipeType<T>> void sendRecipes(EntityPlayerMP player, String typeID, R type) throws IOException;

    /**
     * Checks if the passed in player is the same player as the current local client player
     * (UUID-based, not object based; a serverside player with the same UUID as the local player returns true)
     * @since 1.10.0
     */
    public boolean isPlayerLocal(EntityPlayer player) {
        return false;
    }

    public <T, R extends ISyncedRecipeType<T>> void receiveRecipes(String typeID, int amount, ByteBuf buf) {}
}
