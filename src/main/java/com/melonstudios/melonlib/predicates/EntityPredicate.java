package com.melonstudios.melonlib.predicates;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("all")
public abstract class EntityPredicate implements Predicate<Entity> {
    @Override
    public final boolean apply(@Nullable Entity entity) {
        return test(entity);
    }

    @Override
    public abstract boolean test(@Nonnull Entity entity);

    public EntityPredicate() {}
}
