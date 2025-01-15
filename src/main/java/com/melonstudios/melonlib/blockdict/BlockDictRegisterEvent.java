package com.melonstudios.melonlib.blockdict;

import com.melonstudios.melonlib.misc.MetaBlock;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BlockDictRegisterEvent extends Event {
    public final String name;
    public final MetaBlock block;

    public BlockDictRegisterEvent(MetaBlock block, String name) {
        this.block = block;
        this.name = name;
    }
}
