package com.melonstudios.melonlib.misc;

import net.minecraft.util.text.translation.I18n;

@SuppressWarnings({"deprecation"})
public class Localizer {
    public static String translate(String key, Object... formatting) {
        if (formatting.length > 0) {
            return I18n.translateToLocalFormatted(key, formatting);
        }
        return I18n.translateToLocal(key);
    }
}
