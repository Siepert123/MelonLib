package com.melonstudios.melonlib.tileentity;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.misc.TileSyncFix;
import com.melonstudios.melonlib.network.PacketRequestSyncTE;
import com.melonstudios.melonlib.network.PacketSyncTE;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Interface for any TileEntity that makes synchronizing some NBT data really easy.
 * @since 1.4
 * @see PacketSyncTE
 */
public interface ISyncedTE {
    default TileEntity self_ISyncedTE() {
        return (TileEntity) this;
    }

    /**
     * Write some data to NBT for sending in a packet.
     * @return Data to synchronize as NBT
     */
    NBTTagCompound writePacket();

    /**
     * Read the NBT data from an incoming sync packet.
     * @param nbt The NBT received via synchronization packet
     */
    void readPacket(NBTTagCompound nbt);

    /**
     * Influences whether the NBT data is compressed before being sent over the network.
     * Decreases network impact, increases time before packet can be sent and read.
     * @return Whether to GZIP the NBT data in the packet.
     */
    boolean compressPacketNBT();

    /**
     * Sends synchronization packet to tracking players.
     * The contents of the packet will be determined by {@link #writePacket()}.
     * When the client receives the packet, the NBT contents will be read out by {@link #readPacket(NBTTagCompound)}.
     */
    default void sync() {
        TileSyncFix.getInstance().sync(this);
    }

    /**
     * Call this function on the client (preferably in {@link TileEntity#onLoad()}) to request a synchronization.
     */
    default void requestSync() {
        MelonLib.net.sendToServer(new PacketRequestSyncTE(this));
    }

    default double synchronizationRange() {
        return 64.0;
    }
    default NetworkRegistry.TargetPoint getTargetPoint() {
        TileEntity te = this.self_ISyncedTE();
        return new NetworkRegistry.TargetPoint(te.getWorld().provider.getDimension(),
                te.getPos().getX() + 0.5,
                te.getPos().getY() + 0.5,
                te.getPos().getZ() + 0.5,
                this.synchronizationRange());
    }

}
