package com.melonstudios.melonlib;

import com.melonstudios.melonlib.blockdict.BlockDictionary;
import com.melonstudios.melonlib.command.CommandBlockDict;
import com.melonstudios.melonlib.command.CommandOreDict;
import com.melonstudios.melonlib.misc.AdvancementUtil;
import com.melonstudios.melonlib.misc.ServerHack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;

//Main mod class. Not much to see here
@Mod(modid = MelonLib.MODID, name = MelonLib.NAME, version = MelonLib.VERSION)
public class MelonLib {
    public static final String MODID = "melonlib";
    public static final String NAME = "MelonLib";
    public static final String VERSION = "1.3";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        OreDictionary.registerOre("doorIron", new ItemStack(Items.IRON_DOOR, 1, 0));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

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
    }

    @EventHandler
    public void serverStop(FMLServerStoppingEvent event) {
        ServerHack.removeServer();
        AdvancementUtil.clearCache();
    }
}
