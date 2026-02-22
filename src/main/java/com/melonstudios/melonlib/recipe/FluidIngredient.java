package com.melonstudios.melonlib.recipe;

import com.google.common.collect.BiMap;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class FluidIngredient implements Predicate<FluidStack> {
    private final FluidStack wrapped;
    protected FluidIngredient(FluidStack wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean test(FluidStack fluidStack) {
        return this.matches(fluidStack);
    }
    public boolean matches(FluidStack stack) {
        return this.wrapped.containsFluid(stack);
    }

    public List<FluidStack> getDisplayFluids() {
        return Collections.singletonList(this.wrapped);
    }

    public static FluidIngredient of(FluidStack stack) {
        return new FluidIngredient(stack);
    }

    public void serialize(ByteBuf buf) {
        BiMap<Fluid, Integer> map = (BiMap<Fluid, Integer>) FluidRegistry.getRegisteredFluidIDs();
        int id = map.get(this.wrapped.getFluid());
        int amount = this.wrapped.amount;
        NBTTagCompound tag = this.wrapped.tag;
        buf.writeInt(id);
        buf.writeInt(amount);
        if (tag != null) {
            buf.writeBoolean(true);
            new PacketBuffer(buf).writeCompoundTag(tag);
        } else {
            buf.writeBoolean(false);
        }
    }
    public NBTTagCompound serialize(NBTTagCompound nbt) {
        return this.wrapped.writeToNBT(nbt);
    }

    public static FluidIngredient read(ByteBuf buf) throws IOException {
        BiMap<Fluid, Integer> map = (BiMap<Fluid, Integer>) FluidRegistry.getRegisteredFluidIDs();
        Fluid fluid = map.inverse().get(buf.readInt());
        int amount = buf.readInt();
        NBTTagCompound nbt = buf.readBoolean() ? new PacketBuffer(buf).readCompoundTag() : null;
        return new FluidIngredient(new FluidStack(fluid, amount, nbt));
    }
    public static FluidIngredient read(NBTTagCompound nbt) {
        return new FluidIngredient(FluidStack.loadFluidStackFromNBT(nbt));
    }
}
