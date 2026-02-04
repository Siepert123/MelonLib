package com.melonstudios.melonlib.imc;

import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.api.DimensionTeleporterSoundFix;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class IMCHandler {
    public static void process(FMLInterModComms.IMCMessage message) {
        if ("DimensionTeleporterSoundFix".equalsIgnoreCase(message.key)) {
            if (!message.isStringMessage()) {
                MelonLib.logger.warn("Received malformed DimensionTeleporterSoundFix IMC message");
                return;
            }
            try {
                Class<?> clazz = Class.forName(message.getStringValue());
                // noinspection unchecked
                DimensionTeleporterSoundFix.add((Class<? extends ITeleporter>) clazz);
                MelonLib.logger.debug("Received DimensionTeleporterSoundFix IMC message for {}", clazz.toString());
            } catch (Exception e) {
                MelonLib.logger.warn("Received malformed DimensionTeleporterSoundFix IMC message: {}", e.toString());
            }
        }
    }
}
