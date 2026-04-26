package com.melonstudios.melonlib.entitydict;

import net.minecraft.entity.Entity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

/**
 * The EntityDictionary is a useful tool for tagging entities in a way that
 * blah blah blah ok whatever no one will read this anyway
 * @since 1.11.2
 */
public class EntityDictionary {
    private EntityDictionary() {}

    //this can be really simplified as entities do not have subtypes!
    private static final Map<Class<? extends Entity>, NonNullList<String>> entityToNames = new HashMap<>();
    private static final Map<String, NonNullList<Class<? extends Entity>>> nameToEntities = new HashMap<>();
    private static final NonNullList<Class<? extends Entity>> EMPTY_LIST = NonNullList.withSize(0, Entity.class);
    private static final NonNullList<String> ANOTHER_EMPTY_LIST = NonNullList.withSize(0, "");

    public static void register(Class<? extends Entity> entity, String name) {
        if ("Unknown".equals(name)) return;

        entityToNames.computeIfAbsent(entity, k -> NonNullList.create()).add(name);
        nameToEntities.computeIfAbsent(name, k -> NonNullList.create()).add(entity);
        MinecraftForge.EVENT_BUS.post(new EntityDictRegisterEvent(entity, name));
    }
    public static void register(Class<? extends Entity> entity, String... names) {
        for (String name : names) register(entity, name);
    }

    public static NonNullList<Class<? extends Entity>> getTaggedEntities(String name) {
        return nameToEntities.getOrDefault(name, EMPTY_LIST);
    }
    public static NonNullList<String> getTags(Class<? extends Entity> entity) {
        return entityToNames.getOrDefault(entity, ANOTHER_EMPTY_LIST);
    }
    public static NonNullList<String> getTags(Entity entity) {
        return getTags(entity.getClass());
    }
}
