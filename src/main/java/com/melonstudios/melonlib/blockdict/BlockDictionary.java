package com.melonstudios.melonlib.blockdict;

import com.google.common.collect.Maps;
import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.misc.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import scala.Int;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.*;

public class BlockDictionary {
    private static boolean initialized = false;
    private static final List<String> idToName = new ArrayList<>();
    private static final Map<String, Integer> nameToId = new HashMap<>(128);
    private static final List<NonNullList<MetaBlock>> idToBlock = new ArrayList<>();
    private static final List<NonNullList<MetaBlock>> idToBlockUn = new ArrayList<>();
    private static final Map<Integer, List<Integer>> blockToId = Maps.newHashMapWithExpectedSize((int) (128 * 0.75f));
    private static final NonNullList<MetaBlock> EMPTY_LIST = NonNullList.create();

    static {
        initVanillaEntries();
    }

    private static void initVanillaEntries() {

    }

    public static int getOreID(String name) {
        Integer id = nameToId.get(name);
        if (id == null) {
            idToName.add(name);
            id = idToName.size() - 1;
            nameToId.put(name, id);
            NonNullList<MetaBlock> back = NonNullList.create();
            idToBlock.add(back);
            idToBlockUn.add(back);
        }
        return id;
    }

    public static String getOreName(@Nonnegative int id) {
        return (id < idToName.size()) ? idToName.get(id) : "Unknown";
    }

    public static int[] getOreIDs(@Nonnull MetaBlock block) {
        Set<Integer> set = new HashSet<>();

        List<Integer> ids = blockToId.get(block.getStateId());
        if (ids != null) set.addAll(ids);

        Integer[] tmp = set.toArray(new Integer[0]);
        int[] ret = new int[tmp.length];
        for (int x = 0; x < tmp.length; x++) {
            ret[x] = tmp[x];
        }
        return ret;
    }

    public static NonNullList<MetaBlock> getOres(String name) {
        return getOres(getOreID(name));
    }
    public static NonNullList<MetaBlock> getOres(String name, boolean alwaysCreateEntry) {
        if (alwaysCreateEntry) {
            return getOres(getOreID(name));
        }
        return nameToId.get(name) != null ? getOres(getOreID(name)) : EMPTY_LIST;
    }

    public static boolean doesOreNameExist(String name) {
        return nameToId.get(name) != null;
    }

    public static String[] getOreNames() {
        return idToName.toArray(new String[0]);
    }

    private static NonNullList<MetaBlock> getOres(@Nonnegative int id) {
        return idToBlockUn.size() > id ? idToBlockUn.get(id) : EMPTY_LIST;
    }

    public static boolean containsMatch(boolean accountMeta, MetaBlock[] inputs, @Nonnull MetaBlock... targets) {
        for (MetaBlock input : inputs) {
            for (MetaBlock target : targets) {
                if (blockMatches(target, input, accountMeta)) return true;
            }
        }
        return false;
    }

    public static boolean containsMatch(boolean accountMeta, NonNullList<MetaBlock> inputs, @Nonnull MetaBlock... targets) {
        for (MetaBlock input : inputs) {
            for (MetaBlock target : targets) {
                if (blockMatches(target, input, accountMeta)) return true;
            }
        }
        return false;
    }

    public static boolean blockMatches(@Nonnull MetaBlock target, @Nonnull MetaBlock input, boolean accountMeta) {
        if (accountMeta) return target.equals(input);
        return target.getBlock() == input.getBlock();
    }

    public static void registerOre(String name, Block ore) {
        registerOre(name, MetaBlock.of(ore, 0));
    }
    public static void registerOre(String name, IBlockState ore) {
        registerOre(name, MetaBlock.of(ore));
    }
    public static void registerOre(String name, MetaBlock ore) {
        registerOreImpl(name, ore);
    }

    private static void registerOreImpl(String name, MetaBlock ore) {
        if ("Unknown".equals(name)) return;

        int oreID = getOreID(name);

        ResourceLocation registry = ore.getBlock().delegate.name();
        int hash;
        if (registry == null) {
            ModContainer modContainer = Loader.instance().activeModContainer();
            String modContainerName = modContainer == null ? null : modContainer.getName();
            MelonLib.logger.warn("A broken block dictionary registration with name {} has occurred. It adds a meta block (type: {}) which is currently unknown to the game registry. This dictionary item can only support a single value when"
                    + " registered with ores like this, and NO I am not going to turn this spam off. Just register your block dictionary entries after the GameRegistry.\n"
                    + "TO USERS: YES this is a BUG in the mod " + modContainerName + " report it to them!", name, ore.getBlock().getClass());
            hash = -1;
        } else {
            hash = ore.getStateId();
        }

        List<Integer> ids = blockToId.get(hash);
        if (ids != null && ids.contains(oreID)) return;
        if (ids == null) {
            ids = new ArrayList<>();
            blockToId.put(hash, ids);
        }
        ids.add(oreID);

        idToBlock.get(oreID).add(ore);
        MinecraftForge.EVENT_BUS.post(new BlockDictRegisterEvent(ore, name));
    }

    public static void rebakeMap() {
        MelonLib.logger.debug("Baking BlockDictionary:");
        blockToId.clear();
        for (int id = 0; id < idToBlock.size(); id++) {
            NonNullList<MetaBlock> ores = idToBlock.get(id);
            if (ores == null) continue;
            for (MetaBlock ore : ores) {
                ResourceLocation name = ore.getBlock().delegate.name();
                int hash;
                if (name == null) {
                    MelonLib.logger.debug("Defaulting unregistered block dictionary entry for block dictionary {}: type {} to -1",
                            getOreName(id), ore.getBlock().getClass());
                    hash = -1;
                } else {
                    hash = ore.getStateId();
                }
                List<Integer> ids = blockToId.computeIfAbsent(hash, k -> new ArrayList<>());
                ids.add(id);
                MelonLib.logger.debug(id + " " + getOreName(id) + Integer.toHexString(hash) + " " + ore);
            }
        }
    }
}
