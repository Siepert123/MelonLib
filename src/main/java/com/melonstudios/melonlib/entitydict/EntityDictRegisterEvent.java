package com.melonstudios.melonlib.entitydict;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @since 1.11.2
 */
public class EntityDictRegisterEvent extends Event {
    public final String name;
    public final Class<? extends Entity> entity;

    public EntityDictRegisterEvent(Class<? extends Entity> entity, String name) {
        this.entity = entity;
        this.name = name;
    }
}
