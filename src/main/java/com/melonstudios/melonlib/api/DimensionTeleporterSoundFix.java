package com.melonstudios.melonlib.api;

import net.minecraftforge.common.util.ITeleporter;

import java.util.HashSet;
import java.util.Set;

public class DimensionTeleporterSoundFix {
    public static void add(Class<? extends ITeleporter> clazz) {
        APPLICABLE.add(clazz);
    }
    private static final Set<Class<? extends ITeleporter>> APPLICABLE = new HashSet<>();
    public static boolean applies(ITeleporter teleporter) {
        return APPLICABLE.contains(teleporter.getClass());
    }
}
