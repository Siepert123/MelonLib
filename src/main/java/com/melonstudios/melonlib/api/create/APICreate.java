package com.melonstudios.melonlib.api.create;

import com.google.common.base.Predicate;
import net.minecraft.block.state.IBlockState;

import java.util.ArrayList;
import java.util.List;

public class APICreate {
    private static final List<Predicate<IBlockState>> WASHING_CATALYSTS = new ArrayList<>();
    private static final List<Predicate<IBlockState>> COOKING_CATALYSTS = new ArrayList<>();
    private static final List<Predicate<IBlockState>> HAUNTING_CATALYSTS = new ArrayList<>();
    private static final List<Predicate<IBlockState>> FAN_PASSES = new ArrayList<>();

    public static void copyFanContents(int id, List<Predicate<IBlockState>> list) {
        if (id == 0) {
            list.addAll(FAN_PASSES);
        } else if (id == 1) {
            list.addAll(WASHING_CATALYSTS);
        } else if (id == 2) {
            list.addAll(COOKING_CATALYSTS);
        } else if (id == 3) {
            list.addAll(HAUNTING_CATALYSTS);
        }
    }

    public static void addWashingCatalyst(Predicate<IBlockState> predicate) {
        WASHING_CATALYSTS.add(predicate);
    }
    public static void addCookingCatalyst(Predicate<IBlockState> predicate) {
        COOKING_CATALYSTS.add(predicate);
    }
    public static void addHauntingCatalyst(Predicate<IBlockState> predicate) {
        HAUNTING_CATALYSTS.add(predicate);
    }
    public static void addFanPass(Predicate<IBlockState> predicate) {
        FAN_PASSES.add(predicate);
    }
}
