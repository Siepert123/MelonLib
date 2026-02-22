package com.melonstudios.melonlib.recipe;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface ISyncedRecipeType<T> extends IRecipeType<T> {
    /**
     * Determines whether MelonLib should synchronize this recipe type to the client.
     * When it returns true, the class will be cast to {@link ISyncedRecipeType}!!!
     * As such it is VITAL that this method returns true if and ONLY IF the class is an instance of
     * {@link ISyncedRecipeType}!!!
     * Apart from that, the method is allowed to return false even if the class
     * is an instance of {@link ISyncedRecipeType}. If, for example, the sync can be configured,
     * the class will always implement {@link ISyncedRecipeType} but may not always want to be sent to the client.
     */
    @Override
    default boolean shouldSynchronize() {
        return true;
    }

    void write(T recipe, ByteBuf buf) throws IOException;
    T read(String recipeID, ByteBuf buf) throws IOException;
}
