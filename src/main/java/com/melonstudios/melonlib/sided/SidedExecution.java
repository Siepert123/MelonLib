package com.melonstudios.melonlib.sided;

import com.melonstudios.melonlib.MelonLib;
import net.minecraftforge.fml.relauncher.Side;

import java.util.function.Supplier;

public class SidedExecution {
    public static Side getSide() {
        return MelonLib.proxy.getSide();
    }

    public static void onlyIn(Side side, Supplier<Runnable> function) {
        if (getSide() == side) function.get().run();
    }

    public static <T> Supplier<T> supplyIf(Side side, Supplier<T> supply) {
        return getSide() == side ? supply : () -> null;
    }
}
