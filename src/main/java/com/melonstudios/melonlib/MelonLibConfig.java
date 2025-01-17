package com.melonstudios.melonlib;

import net.minecraftforge.common.config.Config;
import org.lwjgl.input.Keyboard;

@Config(modid = "melonlib")
public class MelonLibConfig {
    @Config.Comment("The key to display the dict entries tooltip\n(LSHIFT is " + Keyboard.KEY_LSHIFT + ", LCONTROL is " + Keyboard.KEY_LCONTROL + ")")
    public static int keyForDictEntriesTooltip = Keyboard.KEY_LSHIFT;
}
