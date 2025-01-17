package com.melonstudios.melonlib;

import com.melonstudios.melonlib.blockdict.BlockDictionary;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;

//Main mod class. Not much to see here
@Mod(modid = MelonLib.MODID, name = MelonLib.NAME, version = MelonLib.VERSION)
public class MelonLib {
    public static final String MODID = "melonlib";
    public static final String NAME = "MelonLib";
    public static final String VERSION = "1.1";

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
}
