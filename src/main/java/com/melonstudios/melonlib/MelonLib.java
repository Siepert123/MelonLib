package com.melonstudios.melonlib;

import com.melonstudios.melonlib.blockdict.BlockDictionary;
import com.melonstudios.melonlib.command.CommandBlockDict;
import com.melonstudios.melonlib.command.CommandOreDict;
import com.melonstudios.melonlib.command.CommandRecipeTypes;
import com.melonstudios.melonlib.event.RegisterRecipesEvent;
import com.melonstudios.melonlib.imc.IMCHandler;
import com.melonstudios.melonlib.misc.AdvancementUtil;
import com.melonstudios.melonlib.misc.ServerHack;
import com.melonstudios.melonlib.misc.TileSyncFix;
import com.melonstudios.melonlib.network.PacketRequestSyncTE;
import com.melonstudios.melonlib.network.PacketSendRecipes;
import com.melonstudios.melonlib.sided.AbstractProxy;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;

//Main mod class. Not much to see here
@Mod(modid = MelonLib.MODID, name = MelonLib.NAME, version = MelonLib.VERSION)
public class MelonLib {
    public static final String MODID = "melonlib";
    public static final String NAME = "MelonLib";
    public static final String VERSION = "1.10.0";

    public static Logger logger;
    public static SimpleNetworkWrapper net;

    @SidedProxy(
            serverSide = "com.melonstudios.melonlib.sided.ServerProxy",
            clientSide = "com.melonstudios.melonlib.sided.ClientProxy"
    )
    public static AbstractProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        OreDictionary.registerOre("doorIron", new ItemStack(Items.IRON_DOOR, 1, 0));
        net = NetworkRegistry.INSTANCE.newSimpleChannel("melonlib");

        net.registerMessage(
                new PacketRequestSyncTE.Handler(),
                PacketRequestSyncTE.class,
                0, Side.SERVER
        );
        proxy.registerClientTESync(net);
        net.registerMessage(
                new PacketSendRecipes.Handler(),
                PacketSendRecipes.class,
                2, Side.CLIENT
        );
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.post(new RegisterRecipesEvent());
    }

    @EventHandler
    public void youGotMail(FMLInterModComms.IMCEvent event) {
        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
            IMCHandler.process(message);
        }
    }

    @EventHandler
    public void mappingChanged(FMLModIdMappingEvent event) {
        BlockDictionary.rebakeMap();
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        if (event.getServer() != null) ServerHack.setServer(event.getServer());
        event.registerServerCommand(new CommandBlockDict());
        event.registerServerCommand(new CommandOreDict());
        event.registerServerCommand(new CommandRecipeTypes());
    }

    @EventHandler
    public void serverStop(FMLServerStoppingEvent event) {
        ServerHack.removeServer();
        AdvancementUtil.clearCache();
    }

    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        TileSyncFix.getInstance().clear();
    }
}
