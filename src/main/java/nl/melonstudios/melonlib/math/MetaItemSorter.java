package nl.melonstudios.melonlib.math;

import nl.melonstudios.melonlib.misc.MetaItem;
import net.minecraft.item.Item;

import java.util.Comparator;

/**
 * A sorter for MetaItems.
 */
public class MetaItemSorter implements Comparator<MetaItem> {
    public static final MetaItemSorter instance = new MetaItemSorter();

    private MetaItemSorter() {}

    @Override
    public int compare(MetaItem o1, MetaItem o2) {
        int id1 = Item.getIdFromItem(o1.getItem());
        int id2 = Item.getIdFromItem(o2.getItem());
        int meta1 = o1.getMetadata();
        int meta2 = o2.getMetadata();

        if (id1 == id2) {
            if (meta1 == meta2) return 0;
            return meta1 > meta2 ? 1 : -1;
        }
        return id1 > id2 ? 1 : -1;
    }
}
