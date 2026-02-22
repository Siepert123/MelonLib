package com.melonstudios.melonlib.sided;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.network.PacketSendRecipes;
import com.melonstudios.melonlib.network.PacketSyncTE;
import com.melonstudios.melonlib.recipe.IRecipeType;
import com.melonstudios.melonlib.recipe.IRecipeTypeClient;
import com.melonstudios.melonlib.recipe.ISyncedRecipeType;
import com.melonstudios.melonlib.recipe.RecipeRegistry;
import com.melonstudios.melonlib.tileentity.ISyncedTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class ClientProxy extends AbstractProxy {
    @Override
    public Side getSide() {
        return Side.CLIENT;
    }

    @Override
    public void registerClientTESync(SimpleNetworkWrapper net) {
        net.registerMessage(
                SidedExecution.supplyIf(Side.CLIENT, PacketSyncTE.Handler::new).get(),
                PacketSyncTE.class,
                1, Side.CLIENT
        );
    }

    @Override
    public void packetSyncTE(PacketSyncTE packet, IMessageHandler<PacketSyncTE, IMessage> handler, MessageContext ctx) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.addScheduledTask(() -> {
            World world = mc.world;
            boolean retry = false;
            if (world.isBlockLoaded(packet.pos)) {
                TileEntity te = world.getTileEntity(packet.pos);
                if (te instanceof ISyncedTE) {
                    ((ISyncedTE)te).readPacket(packet.nbt);
                } else if (te == null) {
                    retry = true;
                }
            } else retry = true;
            if (retry) {
                //MelonLib.logger.warn("No tile entity at {}, retrying", packet.pos);
                //ClientPacketStaller.add(new ClientPacketStaller.Stall(packet, handler, ctx));
                MelonLib.logger.warn("No tile entity at {}, skipping", packet.pos);
            }
        });
    }

    @Override
    public <T, R extends ISyncedRecipeType<T>> void sendRecipes(EntityPlayerMP player, String typeID, R type) throws IOException {
        if (this.isPlayerLocal(player)) {
            MelonLib.logger.info("Locally receiving recipes of type {}", typeID);
            IRecipeTypeClient<T, R> recipeTypeClient = RecipeRegistry.getRecipeTypeClient(typeID);
            recipeTypeClient.prepareForData();
            recipeTypeClient.addFromLocal(type);
            return;
        }
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(typeID.length());
        buf.writeCharSequence(typeID, StandardCharsets.UTF_8);
        Map<String, T> map = type.getRecipeMap();
        buf.writeInt(map.size());
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String recipeID = entry.getKey();
            T recipe = entry.getValue();
            buf.writeInt(recipeID.length());
            buf.writeCharSequence(recipeID, StandardCharsets.UTF_8);
            type.write(recipe, buf);
        }
        MelonLib.net.sendTo(new PacketSendRecipes(), player);
    }

    @Override
    public boolean isPlayerLocal(EntityPlayer player) {
        EntityPlayer local = Minecraft.getMinecraft().player;
        if (player == null || local == null) return false;
        if (player == local) return true;
        UUID playerUUID = player.getPersistentID();
        UUID localUUID = local.getPersistentID();
        return Objects.equals(playerUUID, localUUID);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R extends ISyncedRecipeType<T>> void receiveRecipes(String typeID, int amount, ByteBuf buf) {
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
