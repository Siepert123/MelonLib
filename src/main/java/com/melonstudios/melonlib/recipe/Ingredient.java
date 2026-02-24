package com.melonstudios.melonlib.recipe;

import com.melonstudios.melonlib.misc.Localizer;
import com.melonstudios.melonlib.misc.StackUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Better alternative to simple {@link ItemStack} inputs.
 * Allows for many different options, and contains helper functions for serialization.
 * Also has a {@link FluidStack} alternative.
 * @since 1.10.0
 * @see ItemStack
 * @see FluidIngredient
 */
public abstract class Ingredient implements Predicate<ItemStack> {
    private static ItemStack createEmptyOreDict(String oredict) {
        ItemStack stack = new ItemStack(Blocks.BARRIER, 1, 0);
        stack.setStackDisplayName(Localizer.translate("tooltip.melonlib.empty_oredict", oredict));
        return stack;
    }

    protected Ingredient() {

    }

    @Override
    public boolean test(ItemStack stack) {
        return this.matches(stack);
    }
    public abstract boolean matches(ItemStack stack);

    //For recipe viewers such as JEI
    public abstract List<ItemStack> getDisplayItems();
    public int getAmount() {
        return 1;
    }
    public String getDisplayName() {
        return "[invalid ingredient]";
    }

    public void serialize(ByteBuf buf) {
        throw new UnsupportedOperationException("This Ingredient does not support serialization");
    }
    public NBTTagCompound serialize(NBTTagCompound nbt) {
        throw new UnsupportedOperationException("This Ingredient does not support serialization");
    }

    public static Ingredient of(ItemStack item, boolean includeNBT) {
        if (includeNBT) {
            return new ItemBasedNBT(item);
        } else {
            item.setTagCompound(null);
            return new ItemBased(item);
        }
    }
    public static Ingredient of(String oredict) {
        return new OreDictBased(oredict);
    }
    public static Ingredient custom(Predicate<ItemStack> checker) {
        return new Custom(checker, Ingredient.createEmptyOreDict("custom ingredient"));
    }
    public static Ingredient empty() {
        return Empty.INSTANCE;
    }

    public static Ingredient read(ByteBuf buf) throws IOException {
        byte type = buf.readByte();
        switch (type) {
            case 0: return ItemBased.deserialize(buf);
            case 1: return ItemBasedNBT.deserialize(buf);
            case 2: return OreDictBased.deserialize(buf);
            case 3: return Empty.INSTANCE;
            default: throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }
    public static Ingredient read(NBTTagCompound nbt) {
        String type = nbt.getString("type");
        switch (type) {
            case "ItemBased": return ItemBased.deserialize(nbt);
            case "ItemBasedNBT": return ItemBasedNBT.deserialize(nbt);
            case "OreDictBased": return OreDictBased.deserialize(nbt);
            case "Empty": return Empty.INSTANCE;
            default: throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }

    private static class ItemBased extends Ingredient {
        protected final ItemStack example;
        protected ItemBased(ItemStack example) {
            this.example = example;
        }

        @Override
        public boolean matches(ItemStack stack) {
            return StackUtil.itemMatches(this.example, stack);
        }
        @Override
        public List<ItemStack> getDisplayItems() {
            return Collections.singletonList(this.example);
        }
        @Override
        public String getDisplayName() {
            return this.example.getDisplayName();
        }

        @Override
        public void serialize(ByteBuf buf) {
            buf.writeByte(0);
            StackUtil.writeItemStack(this.example, buf, false, false);
        }

        @Override
        public NBTTagCompound serialize(NBTTagCompound nbt) {
            nbt.setString("type", "ItemBased");
            nbt.setString("item", this.example.getItem().getRegistryName().toString());
            nbt.setInteger("damage", this.example.getItemDamage());
            return nbt;
        }

        private static ItemBased deserialize(ByteBuf buf) throws IOException {
            return new ItemBased(StackUtil.readItemStack(buf, false, false));
        }
        private static ItemBased deserialize(NBTTagCompound nbt) {
            ResourceLocation id = new ResourceLocation(nbt.getString("item"));
            int damage = nbt.getInteger("damage");
            return new ItemBased(new ItemStack(ForgeRegistries.ITEMS.getValue(id), 1, damage));
        }
    }
    private static class ItemBasedNBT extends ItemBased {
        protected ItemBasedNBT(ItemStack example) {
            super(example);
        }

        @Override
        public boolean matches(ItemStack stack) {
            return super.matches(stack) && Objects.equals(this.example.getTagCompound(), stack.getTagCompound());
        }

        @Override
        public void serialize(ByteBuf buf) {
            buf.writeByte(1);
            StackUtil.writeItemStack(this.example, buf, false, true);
        }

        @Override
        public NBTTagCompound serialize(NBTTagCompound nbt) {
            nbt.setString("type", "ItemBasedNBT");
            nbt.setString("item", this.example.getItem().getRegistryName().toString());
            nbt.setInteger("damage", this.example.getItemDamage());
            NBTTagCompound itemNBT = this.example.getTagCompound();
            if (itemNBT != null) nbt.setTag("itemNBT", itemNBT);
            return nbt;
        }

        private static ItemBasedNBT deserialize(ByteBuf buf) throws IOException {
            return new ItemBasedNBT(StackUtil.readItemStack(buf, false, true));
        }
        private static ItemBasedNBT deserialize(NBTTagCompound nbt) {
            ResourceLocation id = new ResourceLocation(nbt.getString("item"));
            int damage = nbt.getInteger("damage");
            NBTTagCompound itemNBT = nbt.hasKey("itemNBT", 10) ? nbt.getCompoundTag("itemNBT") : null;
            return new ItemBasedNBT(new ItemStack(ForgeRegistries.ITEMS.getValue(id), 1, damage, itemNBT));
        }
    }
    private static class OreDictBased extends Ingredient {
        protected final String oredict;
        protected OreDictBased(String oredict) {
            this.oredict = oredict;
        }

        @Override
        public boolean matches(ItemStack stack) {
            if (stack.isEmpty()) return false;
            int oreID = OreDictionary.getOreID(this.oredict);
            for (int ore : OreDictionary.getOreIDs(stack)) {
                if (ore == oreID) return true;
            }
            return false;
        }
        @Override
        public List<ItemStack> getDisplayItems() {
            List<ItemStack> ores = OreDictionary.getOres(this.oredict, false);
            if (!ores.isEmpty()) {
                return Collections.unmodifiableList(ores);
            } else return Collections.singletonList(Ingredient.createEmptyOreDict(this.oredict));
        }
        @Override
        public String getDisplayName() {
            List<ItemStack> ores = OreDictionary.getOres(this.oredict, false);
            if (!ores.isEmpty()) {
                return ores.get((int)((System.currentTimeMillis() / 1000) % ores.size())).getDisplayName();
            } else return "[Empty OreDict: " + this.oredict + "]";
        }

        @Override
        public void serialize(ByteBuf buf) {
            buf.writeByte(2);
            buf.writeInt(this.oredict.length());
            buf.writeCharSequence(this.oredict, StandardCharsets.UTF_8);
        }

        @Override
        public NBTTagCompound serialize(NBTTagCompound nbt) {
            nbt.setString("type", "OreDictBased");
            nbt.setString("oredict", this.oredict);
            return nbt;
        }

        private static OreDictBased deserialize(ByteBuf buf) {
            int len = buf.readInt();
            String oredict = buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
            return new OreDictBased(oredict);
        }
        private static OreDictBased deserialize(NBTTagCompound nbt) {
            String oredict = nbt.getString("oredict");
            return new OreDictBased(oredict);
        }
    }
    private static class Empty extends Ingredient {
        protected static final Empty INSTANCE = new Empty();
        protected Empty() {

        }

        @Override
        public boolean matches(ItemStack stack) {
            return stack.isEmpty();
        }
        @Override
        public List<ItemStack> getDisplayItems() {
            return Collections.emptyList();
        }
        @Override
        public String getDisplayName() {
            return Localizer.translate(Items.AIR.getUnlocalizedName());
        }

        @Override
        public void serialize(ByteBuf buf) {
            buf.writeByte(3);
        }

        @Override
        public NBTTagCompound serialize(NBTTagCompound nbt) {
            nbt.setString("type", "Empty");
            return nbt;
        }
    }
    private static class Custom extends Ingredient {
        protected final Predicate<ItemStack> checker;
        protected final ItemStack display;
        protected Custom(Predicate<ItemStack> checker, ItemStack display) {
            this.checker = checker;
            this.display = display;
        }

        @Override
        public boolean matches(ItemStack stack) {
            return this.checker.test(stack);
        }
        @Override
        public List<ItemStack> getDisplayItems() {
            return Collections.singletonList(this.display);
        }
    }
}
