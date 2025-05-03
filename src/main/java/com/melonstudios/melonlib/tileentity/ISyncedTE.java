package com.melonstudios.melonlib.tileentity;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.network.PacketRequestSyncTE;
import com.melonstudios.melonlib.network.PacketSyncTE;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public interface ISyncedTE {
    default TileEntity self_ISyncedTE() {
        return (TileEntity) this;
    }

    NBTTagCompound writePacket();
    void readPacket(NBTTagCompound nbt);
    boolean compressPacketNBT();

    //Call this server side
    default void sync() {
        MelonLib.net.sendToAllTracking(new PacketSyncTE(this), this.getTargetPoint());
    }
    //Call this on da client
    default void requestSync() {
        MelonLib.net.sendToServer(new PacketRequestSyncTE());
    }
    default double synchronizationRange() {
        return 64.0;
    }
    //Target point
    default NetworkRegistry.TargetPoint getTargetPoint() {
        TileEntity te = this.self_ISyncedTE();
        return new NetworkRegistry.TargetPoint(te.getWorld().provider.getDimension(),
                te.getPos().getX() + 0.5,
                te.getPos().getY() + 0.5,
                te.getPos().getZ() + 0.5,
                this.synchronizationRange());
    }

}
