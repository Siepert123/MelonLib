package nl.melonstudios.melonlib;

import nl.melonstudios.melonlib.blockdict.BlockDictionary;
import nl.melonstudios.melonlib.command.CommandBlockDict;
import nl.melonstudios.melonlib.command.CommandOreDict;
import nl.melonstudios.melonlib.misc.AdvancementUtil;
import nl.melonstudios.melonlib.misc.ServerHack;
import nl.melonstudios.melonlib.network.PacketRequestSyncTE;
import nl.melonstudios.melonlib.network.PacketSyncTE;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
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
    public static final String VERSION = "1.6";

    public static Logger logger;
    public static SimpleNetworkWrapper net;

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
        net.registerMessage(
                new PacketSyncTE.Handler(),
                PacketSyncTE.class,
                0, Side.CLIENT
        );
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
