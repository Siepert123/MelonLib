package com.melonstudios.melonlib.blockdict;

import com.google.common.collect.Maps;
import com.melonstudios.melonlib.MelonLib;
import com.melonstudios.melonlib.misc.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.*;

/**
 * The BlockDictionary is a useful tool for tagging blocks in a way that is compatible with other mods (as long as they have MelonLib installed).
 * @since 1.0
 */
public final class BlockDictionary {
    private BlockDictionary() {}

    private static boolean initialized = false;
    private static final List<String> idToName = new ArrayList<>();
    private static final Map<String, Integer> nameToId = new HashMap<>(128);
    private static final List<NonNullList<MetaBlock>> idToBlock = new ArrayList<>();
    private static final List<NonNullList<MetaBlock>> idToBlockUn = new ArrayList<>();
    private static final Map<Integer, List<Integer>> blockToId = Maps.newHashMapWithExpectedSize((int) (128 * 0.75f));
    private static final NonNullList<MetaBlock> EMPTY_LIST = NonNullList.create(); //This is an empty list

    static {
        initVanillaEntries();
    }

    /**
     * Registers all vanilla entries.
     */
    private static void initVanillaEntries() {
        if (!initialized) {
            //tree & wood related stuff
            registerOre("logWood", Blocks.LOG, 12);
            registerOre("logWood", Blocks.LOG2.getDefaultState()
                    .withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA)
                    .withProperty(BlockNewLog.LOG_AXIS, BlockLog.EnumAxis.X));
            registerOre("logWood", Blocks.LOG2.getDefaultState()
                    .withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA)
                    .withProperty(BlockNewLog.LOG_AXIS, BlockLog.EnumAxis.Y));
            registerOre("logWood", Blocks.LOG2.getDefaultState()
                    .withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA)
                    .withProperty(BlockNewLog.LOG_AXIS, BlockLog.EnumAxis.Z));
            registerOre("logWood", Blocks.LOG2.getDefaultState()
                    .withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK)
                    .withProperty(BlockNewLog.LOG_AXIS, BlockLog.EnumAxis.X));
            registerOre("logWood", Blocks.LOG2.getDefaultState()
                    .withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK)
                    .withProperty(BlockNewLog.LOG_AXIS, BlockLog.EnumAxis.Y));
            registerOre("logWood", Blocks.LOG2.getDefaultState()
                    .withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK)
                    .withProperty(BlockNewLog.LOG_AXIS, BlockLog.EnumAxis.Z));
            registerOre("plankWood", Blocks.PLANKS, 6);
            registerOre("stairWood", Blocks.OAK_STAIRS, 8);
            registerOre("stairWood", Blocks.SPRUCE_STAIRS, 8);
            registerOre("stairWood", Blocks.BIRCH_STAIRS, 8);
            registerOre("stairWood", Blocks.JUNGLE_STAIRS, 8);
            registerOre("stairWood", Blocks.ACACIA_STAIRS, 8);
            registerOre("stairWood", Blocks.DARK_OAK_STAIRS, 8);
            registerOre("fenceWood", Blocks.OAK_FENCE, false);
            registerOre("fenceWood", Blocks.SPRUCE_FENCE, false);
            registerOre("fenceWood", Blocks.BIRCH_FENCE, false);
            registerOre("fenceWood", Blocks.JUNGLE_FENCE, false);
            registerOre("fenceWood", Blocks.DARK_OAK_FENCE, false);
            registerOre("fenceWood", Blocks.ACACIA_FENCE, false);
            registerOre("fenceGateWood", Blocks.OAK_FENCE_GATE, 8);
            registerOre("fenceGateWood", Blocks.SPRUCE_FENCE_GATE, 8);
            registerOre("fenceGateWood", Blocks.BIRCH_FENCE_GATE, 8);
            registerOre("fenceGateWood", Blocks.JUNGLE_FENCE_GATE, 8);
            registerOre("fenceGateWood", Blocks.DARK_OAK_FENCE_GATE, 8);
            registerOre("fenceGateWood", Blocks.ACACIA_FENCE_GATE, 8);
            registerOre("treeSapling", Blocks.SAPLING, 6);
            registerOre("vine", Blocks.VINE, true);
            BlockDictionary.registerOre("treeLeaves", Blocks.LEAVES, true);
            for (int i = 0; i < 2; i++) {
                BlockDictionary.registerOre("treeLeaves", MetaBlock.of(Blocks.LEAVES2, i));
                BlockDictionary.registerOre("treeLeaves", MetaBlock.of(Blocks.LEAVES2, i | 4));
                BlockDictionary.registerOre("treeLeaves", MetaBlock.of(Blocks.LEAVES2, i | 8));
            }

            //ores
            registerOre("oreCoal", Blocks.COAL_ORE, false);
            registerOre("oreIron", Blocks.IRON_ORE, false);
            registerOre("oreLapis", Blocks.LAPIS_ORE, false);
            registerOre("oreRedstone", Blocks.REDSTONE_ORE, false);
            registerOre("oreRedstone", Blocks.LIT_REDSTONE_ORE, false);
            registerOre("oreGold", Blocks.GOLD_ORE, false);
            registerOre("oreDiamond", Blocks.DIAMOND_ORE, false);
            registerOre("oreEmerald", Blocks.EMERALD_ORE, false);
            registerOre("oreQuartz", Blocks.QUARTZ_ORE, false);

            //storage blocks
            registerOre("blockGold", Blocks.GOLD_BLOCK, false);
            registerOre("blockIron", Blocks.IRON_BLOCK, false);
            registerOre("blockLapis", Blocks.LAPIS_BLOCK, false);
            registerOre("blockDiamond", Blocks.DIAMOND_BLOCK, false);
            registerOre("blockRedstone", Blocks.REDSTONE_BLOCK, false);
            registerOre("blockEmerald", Blocks.EMERALD_BLOCK, false);
            registerOre("blockQuartz", Blocks.QUARTZ_BLOCK, false);
            registerOre("blockCoal", Blocks.COAL_BLOCK, false);

            // blocks
            registerOre("dirt", Blocks.DIRT, false);
            registerOre("grass", Blocks.GRASS, false);
            registerOre("stone", Blocks.STONE, false);
            registerOre("cobblestone", Blocks.COBBLESTONE, false);
            registerOre("gravel", Blocks.GRAVEL, false);
            registerOre("sand", Blocks.SAND, 2);
            registerOre("sandstone", Blocks.SANDSTONE, 3);
            registerOre("sandstone", Blocks.RED_SANDSTONE, 3);
            registerOre("netherrack", Blocks.NETHERRACK, false);
            registerOre("obsidian", Blocks.OBSIDIAN, false);
            registerOre("glowstone", Blocks.GLOWSTONE, false);
            registerOre("endstone", Blocks.END_STONE, false);
            registerOre("torch",  Blocks.TORCH, 5);
            registerOre("workbench", Blocks.CRAFTING_TABLE, false);
            registerOre("blockSlime", Blocks.SLIME_BLOCK, false);
            registerOre("blockPrismarine", MetaBlock.of(Blocks.PRISMARINE, 0));
            registerOre("blockPrismarineBrick", MetaBlock.of(Blocks.PRISMARINE, 1));
            registerOre("blockPrismarineDark", MetaBlock.of(Blocks.PRISMARINE, 2));
            registerOre("stoneGranite", MetaBlock.of(Blocks.STONE, 1));
            registerOre("stoneGranitePolished", MetaBlock.of(Blocks.STONE, 2));
            registerOre("stoneDiorite", MetaBlock.of(Blocks.STONE, 3));
            registerOre("stoneDioritePolished", MetaBlock.of(Blocks.STONE, 4));
            registerOre("stoneAndesite", MetaBlock.of(Blocks.STONE, 5));
            registerOre("stoneAndesitePolished", MetaBlock.of(Blocks.STONE, 6));
            registerOre("blockGlassColorless", Blocks.GLASS, false);
            registerOre("blockGlass", Blocks.GLASS, false);
            registerOre("blockGlass", Blocks.STAINED_GLASS, true);
            //blockGlass{Color} is added below with dyes
            registerOre("paneGlassColorless", Blocks.GLASS_PANE, false);
            registerOre("paneGlass", Blocks.GLASS_PANE, false);
            registerOre("paneGlass", Blocks.STAINED_GLASS_PANE, true);
            //paneGlass{Color} is added below with dyes
            registerOre("wool", Blocks.WOOL, true);
            //wool{Color} is added below with dyes

            // chests
            registerOre("chest", Blocks.CHEST, false);
            registerOre("chest", Blocks.ENDER_CHEST, false);
            registerOre("chest", Blocks.TRAPPED_CHEST, false);
            registerOre("chestWood", Blocks.CHEST, false);
            registerOre("chestEnder", Blocks.ENDER_CHEST, false);
            registerOre("chestTrapped", Blocks.TRAPPED_CHEST, false);

            String[] dyes = {
                    "Black",
                    "Red",
                    "Green",
                    "Brown",
                    "Blue",
                    "Purple",
                    "Cyan",
                    "LightGray",
                    "Gray",
                    "Pink",
                    "Lime",
                    "Yellow",
                    "LightBlue",
                    "Magenta",
                    "Orange",
                    "White"
            };

            for (int i = 0; i < 16; i++) {
                MetaBlock wool = MetaBlock.of(Blocks.WOOL, 15 - i);
                MetaBlock glass = MetaBlock.of(Blocks.STAINED_GLASS, 15 - i);
                MetaBlock pane = MetaBlock.of(Blocks.STAINED_GLASS_PANE, 15 - i);
                MetaBlock terracotta = MetaBlock.of(Blocks.STAINED_HARDENED_CLAY, 15 - i);

                registerOre("wool" + dyes[i], wool);
                registerOre("blockGlass" + dyes[i], glass);
                registerOre("paneGlass" + dyes[i], pane);
                registerOre("terracotta" + dyes[i], terracotta);
            }

            //Miscellaneous
            registerOre("carpetWool", Blocks.CARPET, true);
            registerOre("terracotta", Blocks.HARDENED_CLAY, false);
            registerOre("terracotta", Blocks.STAINED_HARDENED_CLAY, true);
            registerOre("terracottaColorless", Blocks.HARDENED_CLAY, false);

            initialized = true;
        }
    }

    /**
     * Gets the integer ID for the specified ore name.
     * If the name does not have an ID it assigns it a new one.
     *
     * @param name The unique name for this ore 'oreIron', 'blockGold', etc..
     * @return A number representing the ID for this ore type
     * @since 1.0
     */
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

    /**
     * Reverse of getOreID, will not create new entries.
     *
     * @param id The ID to translate to a string
     * @return The String name, or "Unknown" if not found.
     * @since 1.0
     */
    public static String getOreName(@Nonnegative int id) {
        return (id < idToName.size()) ? idToName.get(id) : "Unknown";
    }

    /**
     * Gets all the integer ID for the ores that the specified meta block is registered to.
     * If the meta block is not linked to any ore, this will return an empty array and no new entry will be created.
     *
     * @param block The meta block of the ore.
     * @return An array of ids that this ore is registered as.
     * @since 1.0
     */
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

    /**
     * Retrieves the NonNullList of blocks that are registered to this ore type.
     * Creates the list as empty if it did not exist.
     * <p>
     * The returned List is unmodifiable, but will be updated if a new ore
     * is registered using registerOre
     *
     * @param name The ore name, directly calls getOreID
     * @return An NonNullList containing MetaBlocks registered for this ore
     * @since 1.0
     */
    public static NonNullList<MetaBlock> getOres(String name) {
        return getOres(getOreID(name));
    }

    /**
     * Retrieves the List of items that are registered to this ore type at this instant.
     * If the flag is TRUE, then it will create the list as empty if it did not exist.
     * <p>
     * This option should be used by modders who are doing blanket scans in postInit.
     * It greatly reduces clutter in the BlockDictionary is the responsible and proper
     * way to use the dictionary in a large number of cases.
     *
     * @param name The ore name, directly calls getOreID if the flag is TRUE
     * @param alwaysCreateEntry Flag - should a new entry be created if empty
     * @return An NonNullList containing MetaBlocks registered for this ore
     * @since 1.0
     */
    public static NonNullList<MetaBlock> getOres(String name, boolean alwaysCreateEntry) {
        if (alwaysCreateEntry) {
            return getOres(getOreID(name));
        }
        return nameToId.get(name) != null ? getOres(getOreID(name)) : EMPTY_LIST;
    }

    /**
     * Returns whether an oreName exists in the dictionary.
     * This function can be used to safely query the Block Dictionary without
     * adding needless clutter to the underlying map structure.
     * <p>
     * Please use this when possible and appropriate.
     *
     * @param name The ore name
     * @return Whether that name is in the Block Dictionary.
     * @since 1.0
     */
    public static boolean doesOreNameExist(String name) {
        return nameToId.get(name) != null;
    }

    /**
     * Retrieves a list of all unique ore names that are already registered.
     *
     * @return All unique ore names that are currently registered.
     * @since 1.0
     */
    public static String[] getOreNames() {
        return idToName.toArray(new String[0]);
    }

    /**
     * Retrieves the List of blocks that are registered to this ore type.
     * Creates the list as empty if it did not exist.
     *
     * @param id The ore ID, see getOreID
     * @return A List containing MetaBlocks registered for this ore
     * @since 1.0
     */
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

    /**
     * Checks if the supplied MetaBlock is tagged with the supplied ore name.
     * @param block The MetaBlock to test
     * @param name The ore name to check for
     * @return Whether the MetaBlock is tagged with the ore name
     * @since 1.0
     */
    public static boolean isBlockTagged(MetaBlock block, String name) {
        return getOres(name, false).contains(block);
    }
    /**
     * Checks if the supplied blockstate is tagged with the supplied ore name.
     * @param state The blockstate to test (automatically converted to MetaBlock)
     * @param name The ore name to check for
     * @return Whether the blockstate is tagged with the ore name
     * @since 1.0
     */
    public static boolean isBlockTagged(IBlockState state, String name) {
        return getOres(name, false).contains(MetaBlock.of(state));
    }

    /**
     * Registers an ore block into the dictionary.
     * @param name The name of the ore
     * @param ore The ore's Block
     * @param allMetadataValues Whether to use all 16 meta values or only 0
     * @since 1.0
     */
    public static void registerOre(String name, Block ore, boolean allMetadataValues) {
        if (allMetadataValues) {
            for (int i = 0; i < 16; i++) {
                registerOre(name, MetaBlock.of(ore, i));
            }
        } else registerOre(name, MetaBlock.of(ore, 0));
    }

    /**
     * Registers an ore block into the dictionary.
     * @param name The name of the ore
     * @param ore The ore's Block
     * @param limitMeta How many metadata values to use
     * @since 1.0
     */
    public static void registerOre(String name, Block ore, int limitMeta) {
        for (int i = 0; i < limitMeta; i++) {
            registerOre(name, MetaBlock.of(ore, i));
        }
    }

    /**
     * Registers an ore block into the dictionary.
     * @param name The name of the ore
     * @param ore The ore's blockstate (automatically converted to MetaBlock)
     * @since 1.0
     */
    public static void registerOre(String name, IBlockState ore) {
        registerOre(name, MetaBlock.of(ore));
    }

    /**
     * Registers an ore block into the dictionary.
     * @param name The name of the ore
     * @param ore The ore's MetaBlock
     * @since 1.0
     */
    public static void registerOre(String name, MetaBlock ore) {
        registerOreImpl(name, ore);
    }

    /**
     * Registers an ore block into the dictionary.
     * Raises the registerOre function in all registered handlers.
     *
     * @param name The name of the ore
     * @param ore The ore's MetaBlock
     * @since 1.0
     */
    private static void registerOreImpl(String name, MetaBlock ore) {
        if ("Unknown".equals(name)) return;

        int oreID = getOreID(name);

        ResourceLocation registry = ore.getBlock().delegate.name();
        int hash;
        if (registry == null) {
            ModContainer modContainer = Loader.instance().activeModContainer();
            String modContainerName = modContainer == null ? null : modContainer.getName();
            MelonLib.logger.warn("A broken block dictionary registration with name {} has occurred. It adds a meta block (type: {}) which is currently unknown to the game registry. This dictionary item can only support a single value when registered with ores like this, and NO I am not going to turn this spam off. Just register your block dictionary entries after the GameRegistry.\nTO USERS: YES this is a BUG in the mod {} report it to them!", name, ore.getBlock().getClass(), modContainerName);
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

    /**
     * Rebakes the BlockDictionary blockToId map.
     * Called when the IDs change, so that the dictionary does not get all messed up.
     * @since 1.0
     */
    public static void rebakeMap() {
        MelonLib.logger.debug("Baking BlockDictionary");
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
                if (extraDebug) MelonLib.logger.debug("{} {}{} {}", id, getOreName(id), Integer.toHexString(hash), ore);
            }
        }
    }

    private static final boolean extraDebug = false;
}
