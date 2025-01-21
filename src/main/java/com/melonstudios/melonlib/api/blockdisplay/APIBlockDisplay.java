package com.melonstudios.melonlib.api.blockdisplay;

import com.melonstudios.melonlib.misc.Localizer;
import com.melonstudios.melonlib.misc.MetaBlock;
import net.minecraft.block.Block;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * The API class to interact with Block Display.
 * @since 1.2
 */
@SuppressWarnings("unused")
public final class APIBlockDisplay {
    private static final Map<Block, IAdditionalBlockInfo> customBlockInfoHandlers = new HashMap<>();

    public static void addCustomBlockInfoHandler(@Nonnull Block block, @Nonnull IAdditionalBlockInfo handler) {
        if (customBlockInfoHandlers.containsKey(block)) {
            customBlockInfoHandlers.replace(block, handler);
        } else customBlockInfoHandlers.put(block, handler);
    }
    public static IAdditionalBlockInfo getCustomBlockInfoHandler(@Nonnull Block block) {
        return customBlockInfoHandlers.getOrDefault(block, IAdditionalBlockInfo.EMPTY);
    }
    public static boolean hasCustomBlockInfoHandler(@Nonnull Block block) {
        return customBlockInfoHandlers.containsKey(block);
    }


    private static final Map<MetaBlock, String> customMetaBlockNames = new HashMap<>();

    public static void addCustomMetaBlockName(MetaBlock metaBlock, String langKey) {
        if (customMetaBlockNames.containsKey(metaBlock)) {
            customMetaBlockNames.replace(metaBlock, langKey);
        } else customMetaBlockNames.put(metaBlock, langKey);
    }
    public static String getMetaBlockName(MetaBlock metaBlock) {
        return customMetaBlockNames.getOrDefault(metaBlock, "Error!");
    }
    public static String getLocalizedMetaBlockName(MetaBlock metaBlock) {
        return Localizer.translate(getMetaBlockName(metaBlock));
    }
    public static boolean hasCustomName(MetaBlock metaBlock) {
        return customMetaBlockNames.containsKey(metaBlock);
    }
}
