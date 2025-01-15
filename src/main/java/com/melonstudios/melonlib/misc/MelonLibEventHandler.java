package com.melonstudios.melonlib.misc;

import com.melonstudios.melonlib.blockdict.BlockDictionary;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber(modid = "melonlib")
public class MelonLibEventHandler {

    @SubscribeEvent
    public static void expandItemTooltip(ItemTooltipEvent event) {
        if (event.getFlags().isAdvanced()) {
            ItemStack stack = event.getItemStack();
            Item item = stack.getItem();

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
                        event.getToolTip().add("BlockDict entries:");
                        for (int id : ids) {
                            String name = BlockDictionary.getOreName(id);
                            event.getToolTip().add(" *" + name);
                        }
                    }
                } catch (NullPointerException e) {
                    event.getToolTip().add("*failed to obtain blockdict data*");
                }
            }
            int[] ids = OreDictionary.getOreIDs(stack.copy());
            if (ids.length > 0) {
                event.getToolTip().add("OreDict entries:");
                for (int id : ids) {
                    String name = OreDictionary.getOreName(id);
                    event.getToolTip().add(" *" + name);
                }
            }
        }
    }

}
