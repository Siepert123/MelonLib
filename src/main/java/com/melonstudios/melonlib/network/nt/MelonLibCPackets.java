package com.melonstudios.melonlib.network.nt;

import com.melonstudios.melonlib.network.nt.cpacket.CPacketBulkSyncTE;
import com.melonstudios.melonlib.network.nt.cpacket.CPacketSendRecipes;
import com.melonstudios.melonlib.network.nt.cpacket.CPacketSyncTE;

public final class MelonLibCPackets {
    public static final CPacketSyncTE SYNC_TE = new CPacketSyncTE();
    public static final CPacketBulkSyncTE BULK_SYNC_TE = new CPacketBulkSyncTE();
    public static final CPacketSendRecipes SEND_RECIPES = new CPacketSendRecipes();
}
