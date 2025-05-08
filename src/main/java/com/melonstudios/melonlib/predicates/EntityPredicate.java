package com.melonstudios.melonlib.predicates;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
/**
 * A predicate for Entities.
 * Can be passed into a function requiring a {@code Predicate<Entity>}.
 *
 * @since 1.2
 * @see StatePredicate
 * @see StackPredicate
 */
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
