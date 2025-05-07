package nl.melonstudios.melonlib.misc;

import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;

public class BlockStateProperties {
    public static final PropertyBool POWERED = BlockLever.POWERED;
    public static final PropertyDirection FACING = BlockDirectional.FACING;
    public static final PropertyDirection HORIZONTAL_FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<EnumFacing.Axis> AXIS = BlockRotatedPillar.AXIS;
    public static final PropertyEnum<EnumFacing.Axis> HORIZONTAL_AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class,
            EnumFacing.Axis.X, EnumFacing.Axis.Z);
}
