package nl.melonstudios.melonlib.math;

import nl.melonstudios.melonlib.misc.MetaBlock;
import net.minecraft.block.Block;

import java.util.Comparator;

/**
 * A sorter for MetaBlocks.
 */
public class MetaBlockSorter implements Comparator<MetaBlock> {
    public static final MetaBlockSorter instance = new MetaBlockSorter();

    private MetaBlockSorter() {}

    @Override
    public int compare(MetaBlock o1, MetaBlock o2) {
        int id1 = Block.getIdFromBlock(o1.getBlock());
        int id2 = Block.getIdFromBlock(o2.getBlock());
        int meta1 = o1.getMetadata();
        int meta2 = o2.getMetadata();

        if (id1 == id2) {
            if (meta1 == meta2) return 0;
            return meta1 > meta2 ? 1 : -1;
        }
        return id1 > id2 ? 1 : -1;
    }
}
