package com.melonstudios.melonlib.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * An inheritor of ItemBlock that allows for variants. Automatically passes the metadata into the
 * {@link Block#getStateForPlacement(World, BlockPos, EnumFacing, float, float, float, int, EntityLivingBase, EnumHand) Block.getStateForPlacement()}
 * function.
 * @since 1.2
 */
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
