package com.melonstudios.melonlib.network.nt.cpacket;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.network.nt.CPacketBase;
import com.melonstudios.melonlib.network.nt.MelonLibPacketManager;
import com.melonstudios.melonlib.recipe.IRecipeTypeClient;
import com.melonstudios.melonlib.recipe.ISyncedRecipeType;
import com.melonstudios.melonlib.recipe.RecipeRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;

public final class CPacketSendRecipes extends CPacketBase {
    @SideOnly(Side.CLIENT)
    @Nullable
    @Override
    public FMLProxyPacket handle(PacketBuffer data) throws Throwable {
        int len = data.readInt();
        String typeID = data.readCharSequence(len, StandardCharsets.UTF_8).toString();
        int amount = data.readInt();
        this.receiveRecipes(typeID, amount, data);
        return null;
    }

    public FMLProxyPacket create(ByteBuf buf) {
        PacketBuffer data = this.buf();
        data.writeBytes(buf);
        return new FMLProxyPacket(data, MelonLibPacketManager.CHANNEL);
    }

    @SuppressWarnings("unchecked")
    private <T, R extends ISyncedRecipeType<T>> void receiveRecipes(String typeID, int amount, ByteBuf buf) {
        MelonLib.logger.info("Receiving {} recipes of type {}", amount, typeID);
        ISyncedRecipeType<T> recipeType = (ISyncedRecipeType<T>) RecipeRegistry.getRecipeType(typeID);
        IRecipeTypeClient<T, R> recipeTypeClient = RecipeRegistry.getRecipeTypeClient(typeID);
        if (recipeType == null) throw new RuntimeException("Null recipe type " + typeID);
        if (recipeTypeClient == null) throw new RuntimeException("Null client recipe type " + typeID);
        try {
            recipeTypeClient.prepareForData();
            for (int i = 0; i < amount; i++) {
                int len = buf.readInt();
                String recipeID = buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
                T recipe = recipeType.read(recipeID, buf);
                recipeTypeClient.addFromRemote(recipeID, recipe);
            }
        } catch (Throwable e) {
            throw new RuntimeException("Exception loading in recipes of type " + typeID, e);
        }
    }
}
