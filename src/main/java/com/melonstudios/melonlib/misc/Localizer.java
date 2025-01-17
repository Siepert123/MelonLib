package com.melonstudios.melonlib.misc;

import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("all")
public class Localizer {
    public static String translate(String key, Object... formatting) {
        String base = I18n.translateToLocal(key);
        if (formatting.length > 0) {
            return String.format(key, formatting);
        }
        return base;
    }
}
