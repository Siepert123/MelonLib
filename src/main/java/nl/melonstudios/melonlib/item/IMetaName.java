package nl.melonstudios.melonlib.item;

import net.minecraft.item.ItemStack;

/**
 * An interface that allows the {@link ItemBlockVariants} to find the custom unlocalized name.
 */
public interface IMetaName {
    String getUnlocalizedName(ItemStack stack);
}
