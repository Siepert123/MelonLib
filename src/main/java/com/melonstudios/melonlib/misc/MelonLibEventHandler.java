package com.melonstudios.melonlib.misc;

import com.melonstudios.melonlib.MelonLibConfig;
import com.melonstudios.melonlib.blockdict.BlockDictionary;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.input.Keyboard;

/**
 * Event bus subscriber of MelonLib.
 * There isn't really much to see here tbh
 */
@Mod.EventBusSubscriber(modid = "melonlib")
public class MelonLibEventHandler {
    private static int getKeyForDictEntries() {
        return MelonLibConfig.keyForDictEntriesTooltip;
    }

    /**
     * Adds BlockDict and OreDict info to the item tooltip if advanced item info is enabled (F3 + H)
     * @param event the event
     */
    @SubscribeEvent
    public static void expandItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        boolean hasEntries = false;
        boolean display = Keyboard.isKeyDown(getKeyForDictEntries());

        if (item instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock) item;
            Block block = itemBlock.getBlock();
            try {
                MetaBlock metaBlock = MetaBlock.of(block.getStateForPlacement(
                        event.getEntityPlayer() != null ? event.getEntityPlayer().world : null,
                        event.getEntityPlayer().getPosition(),
                        EnumFacing.DOWN, 0, 0, 0, stack.getMetadata(),
                        event.getEntityLiving(), EnumHand.MAIN_HAND
                        )
                );
                int[] ids = BlockDictionary.getOreIDs(metaBlock);
                if (ids.length > 0) {
                    hasEntries = true;
                    if (display) {
                        event.getToolTip().add("BlockDict entries:");
                        for (int id : ids) {
                            String name = BlockDictionary.getOreName(id);
                            event.getToolTip().add(" *" + name);
                        }
                    }
                }
            } catch (NullPointerException e) {
                if (display) event.getToolTip().add(Localizer.translate("tooltip.melonlib.blockdict_data_fail"));
            }
        } else if (item instanceof ItemBlockSpecial) {
            ItemBlockSpecial special = (ItemBlockSpecial) item;
            Block block = special.getBlock();
            try {
                MetaBlock metaBlock = MetaBlock.of(block.getStateForPlacement(
                                event.getEntityPlayer() != null ? event.getEntityPlayer().world : null,
                                event.getEntityPlayer().getPosition(),
                                EnumFacing.DOWN, 0, 0, 0, stack.getMetadata(),
                                event.getEntityLiving(), EnumHand.MAIN_HAND
                        )
                );
                int[] ids = BlockDictionary.getOreIDs(metaBlock);
                if (ids.length > 0) {
                    hasEntries = true;
                    if (display) {
                        event.getToolTip().add(Localizer.translate("tooltip.melonlib.blockdict_entries") + ":");
                        for (int id : ids) {
                            String name = BlockDictionary.getOreName(id);
                            event.getToolTip().add(" *" + name);
                        }
                    }
                }
            } catch (NullPointerException e) {
                if (display) event.getToolTip().add(Localizer.translate("tooltip.melonlib.blockdict_data_fail"));
            }
        }
        int[] ids = OreDictionary.getOreIDs(stack.copy());
        if (ids.length > 0) {
            hasEntries = true;
            if (display) {
                event.getToolTip().add(Localizer.translate("tooltip.melonlib.oredict_entries") + ":");
                for (int id : ids) {
                    String name = OreDictionary.getOreName(id);
                    event.getToolTip().add(" *" + name);
                }
            }
        }

        if (hasEntries && !display) {
            event.getToolTip().add(Localizer.translate("tooltip.melonlib.key_for_dict_entries",
                    Keyboard.getKeyName(getKeyForDictEntries())));
        }
    }

}
