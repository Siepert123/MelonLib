package com.melonstudios.melonlib.sided;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.network.PacketSendRecipes;
import com.melonstudios.melonlib.network.PacketSyncTE;
import com.melonstudios.melonlib.recipe.IRecipeType;
import com.melonstudios.melonlib.recipe.ISyncedRecipeType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@SideOnly(Side.SERVER)
public class ServerProxy extends AbstractProxy {
    @Override
    public Side getSide() {
        return Side.SERVER;
    }

    @Override
    public void registerClientTESync(SimpleNetworkWrapper net) {
        net.registerMessage(
                new PacketSyncTE.Handler(),
                PacketSyncTE.class,
                1, Side.CLIENT
        );
    }

    @Override
    public <T, R extends ISyncedRecipeType<T>> void sendRecipes(EntityPlayerMP player, String typeID, R type) throws IOException {
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
}
