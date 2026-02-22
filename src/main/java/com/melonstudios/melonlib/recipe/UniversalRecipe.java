package com.melonstudios.melonlib.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class UniversalRecipe {
    public final String recipeType;
    public final String recipeID;
    public final List<List<Ingredient>> itemInputs;
    public final List<List<FluidIngredient>> fluidInputs;
    public final List<List<ItemStack>> itemOutputs;
    public final List<List<FluidStack>> fluidOutputs;
    public final NBTTagCompound extraData;

    public UniversalRecipe(
            String recipeType,
            String recipeID,
            List<List<Ingredient>> itemInputs,
            List<List<FluidIngredient>> fluidInputs,
            List<List<ItemStack>> itemOutputs,
            List<List<FluidStack>> fluidOutputs,
            NBTTagCompound extraData
    ) {
        this.recipeType = recipeType;
        this.recipeID = recipeID;
        this.itemInputs = itemInputs;
        this.fluidInputs = fluidInputs;
        this.itemOutputs = itemOutputs;
        this.fluidOutputs = fluidOutputs;
        this.extraData = extraData;
    }

    /**
     * Helper function for writing a Universal Recipe to NBT, as it's arguable easier to create a temporary class
     * than to encode the entire NBT tag by hand.
     */
    public NBTTagCompound write(NBTTagCompound nbt) {
        nbt.setString("type", this.recipeType);
        nbt.setString("id", this.recipeID);
        NBTTagList itemInputsListNBT = new NBTTagList();
        NBTTagList fluidInputsListNBT = new NBTTagList();
        NBTTagList itemOutputsListNBT = new NBTTagList();
        NBTTagList fluidOutputsListNBT = new NBTTagList();
        for (List<Ingredient> list : this.itemInputs) {
            NBTTagList inputs = new NBTTagList();
            for (Ingredient input : list) {
                inputs.appendTag(input.serialize(new NBTTagCompound()));
            }
            itemInputsListNBT.appendTag(inputs);
        }
        for (List<FluidIngredient> list : this.fluidInputs) {
            NBTTagList inputs = new NBTTagList();
            for (FluidIngredient input : list) {
                inputs.appendTag(input.serialize(new NBTTagCompound()));
            }
            fluidInputsListNBT.appendTag(inputs);
        }
        for (List<ItemStack> list : this.itemOutputs) {
            NBTTagList outputs = new NBTTagList();
            for (ItemStack output : list) {
                outputs.appendTag(output.writeToNBT(new NBTTagCompound()));
            }
            itemOutputsListNBT.appendTag(outputs);
        }
        for (List<FluidStack> list : this.fluidOutputs) {
            NBTTagList outputs = new NBTTagList();
            for (FluidStack output : list) {
                outputs.appendTag(output.writeToNBT(new NBTTagCompound()));
            }
            fluidOutputsListNBT.appendTag(outputs);
        }
        nbt.setTag("ExtraData", this.extraData);
        return nbt;
    }

    /**
     * Decodes a {@link UniversalRecipe} from NBT.
     * @param nbt The NBT tag to decode
     * @return The decoded Universal Recipe
     * @throws RecipeException When something goes wrong decoding the Universal Recipes
     */
    public static UniversalRecipe read(NBTTagCompound nbt) throws RecipeException {
        String recipeType = nbt.getString("type").trim();
        String recipeID = nbt.getString("id").trim();
        if (recipeType.isEmpty()) throw new RecipeException("Recipe type cannot be empty");
        if (recipeID.isEmpty()) throw new RecipeException("Recipe ID cannot be empty");
        NBTTagList itemInputsListNBT = nbt.getTagList("ItemInputs", 9);
        List<List<Ingredient>> itemInputsList = new ArrayList<>(itemInputsListNBT.tagCount());
        NBTTagList fluidInputsListNBT = nbt.getTagList("FluidInputs", 9);
        List<List<FluidIngredient>> fluidInputsList = new ArrayList<>(fluidInputsListNBT.tagCount());
        NBTTagList itemOutputsListNBT = nbt.getTagList("ItemOutputs", 9);
        List<List<ItemStack>> itemOutputsList = new ArrayList<>(itemOutputsListNBT.tagCount());
        NBTTagList fluidOutputsListNBT = nbt.getTagList("FluidOutputs", 9);
        List<List<FluidStack>> fluidOutputsList = new ArrayList<>(fluidOutputsListNBT.tagCount());
        try {
            for (int i = 0; i < itemInputsListNBT.tagCount(); i++) {
                NBTTagList inputs = (NBTTagList) itemInputsListNBT.get(i);
                List<Ingredient> list = new ArrayList<>(inputs.tagCount());
                for (int j = 0; j < inputs.tagCount(); j++) {
                    list.add(Ingredient.read(inputs.getCompoundTagAt(j)));
                }
                itemInputsList.add(list);
            }
            for (int i = 0; i < fluidInputsListNBT.tagCount(); i++) {
                NBTTagList inputs = (NBTTagList) fluidInputsListNBT.get(i);
                List<FluidIngredient> list = new ArrayList<>(inputs.tagCount());
                for (int j = 0; j < inputs.tagCount(); j++) {
                    list.add(FluidIngredient.read(inputs.getCompoundTagAt(j)));
                }
                fluidInputsList.add(list);
            }
            for (int i = 0; i < itemOutputsListNBT.tagCount(); i++) {
                NBTTagList outputs = (NBTTagList) itemOutputsListNBT.get(i);
                List<ItemStack> list = new ArrayList<>(outputs.tagCount());
                for (int j = 0; j < outputs.tagCount(); j++) {
                    list.add(new ItemStack(outputs.getCompoundTagAt(j)));
                }
                itemOutputsList.add(list);
            }
            for (int i = 0; i < fluidOutputsListNBT.tagCount(); i++) {
                NBTTagList outputs = (NBTTagList) fluidOutputsListNBT.get(i);
                List<FluidStack> list = new ArrayList<>(outputs.tagCount());
                for (int j = 0; j < outputs.tagCount(); j++) {
                    list.add(FluidStack.loadFluidStackFromNBT(outputs.getCompoundTagAt(j)));
                }
                fluidOutputsList.add(list);
            }
        } catch (Exception e) {
            throw new RecipeException("Exception loading recipe I/O", e);
        }
        NBTTagCompound extraData = nbt.getCompoundTag("ExtraData");
        return new UniversalRecipe(recipeType, recipeID, itemInputsList, fluidInputsList, itemOutputsList, fluidOutputsList, extraData);
    }
}
