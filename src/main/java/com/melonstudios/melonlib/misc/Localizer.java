package com.melonstudios.melonlib.misc;

import net.minecraft.util.text.translation.I18n;

/**
 * A simple class to localize entries.
 */
@SuppressWarnings("deprecation")
public class Localizer {
    public static String translate(String key, Object... formatting) {
        if (I18n.canTranslate(key)) {
            if (formatting.length > 0) {
                return I18n.translateToLocalFormatted(key, formatting);
            }
            return I18n.translateToLocal(key);
        }
        if (formatting.length > 0) {
            return String.format(I18n.translateToFallback(key), formatting);
        }
        return I18n.translateToFallback(key);
    }
}
