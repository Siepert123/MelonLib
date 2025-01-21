package com.melonstudios.melonlib.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemBlockVariants extends ItemBlock  {
    public ItemBlockVariants(Block block) {
        super(block);

        setMaxDamage(0);
        setHasSubtypes(true);

        setCreativeTab(block.getCreativeTabToDisplayOn());
    }

    @Override
    @Nonnull
    public String getUnlocalizedName(ItemStack stack) {
        ItemBlock item = (ItemBlock) stack.getItem();
        if (item.getBlock() instanceof IMetaName) {
            return ((IMetaName)item.getBlock()).getUnlocalizedName(stack);
        }
        return getUnlocalizedName();
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
