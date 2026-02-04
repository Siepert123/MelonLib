package com.melonstudios.melonlib.mixin;

import com.melonstudios.melonlib.api.DimensionTeleporterSoundFix;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ITeleporter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("all")
@Mixin(EntityPlayerMP.class)
public class MixinEntityPlayerMP {
    @Shadow
    private boolean invulnerableDimensionChange;
    @Shadow @Final
    public MinecraftServer mcServer;
    @Shadow
    private int lastExperience;
    @Shadow
    private float lastHealth;
    @Shadow
    private int lastFoodLevel;

    @Inject(method = "changeDimension", at = @At("HEAD"), cancellable = true)
    public void disableNetherPortalSFX(int dimensionIn, ITeleporter teleporter, CallbackInfoReturnable<Entity> cir) {
        if (DimensionTeleporterSoundFix.applies(teleporter)) {
            cir.cancel();
            if (!ForgeHooks .onTravelToDimension((Entity) (Object) this, dimensionIn)) {
                cir.setReturnValue((Entity) (Object) this);
                return;
            }
            this.invulnerableDimensionChange = true;

            this.mcServer.getPlayerList().transferPlayerToDimension((EntityPlayerMP) (Object) this, dimensionIn, teleporter);
            this.lastExperience = -1;
            this.lastHealth = -1.0F;
            this.lastFoodLevel = -1;
            cir.setReturnValue((EntityPlayerMP) (Object) this);
        }
    }
}