package nl.melonstudios.melonlib.tileentity;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Extended implementation of the {@link ITileEntityProvider} with extra utility functions.
 * @param <T> The type parameter for the tile entity
 * @since 1.6
 */
@ParametersAreNonnullByDefault
public interface IEntityTile<T extends TileEntity> extends ITileEntityProvider {
    /**
     * @return The class of the TileEntity.
     */
    Class<T> getTEClass();

    /**
     * Creates a new and fresh TileEntity.
     * @param world The world the TileEntity is in
     * @param meta The metadata of the block to create this TileEntity for
     * @return A newly created TileEntity
     */
    @Nullable
    @Override
    T createNewTileEntity(World world, int meta);

    /**
     * Gets the tile entity at the given position, or returns null if there is no tile entity there
     * or if the tile entity class doesn't match the type parameter {@link T}.
     * @param world The world to get the tile entity from
     * @param pos The location of the tile entity
     * @return The TileEntity, or null if it's not there / doesn't match
     */
    @SuppressWarnings("unchecked")
    default T getTileEntity(World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (this.getTEClass().isInstance(te)) return (T) te;
        return null;
    }

    /**
     * Gets the tile entity at the given positions, or returns an Optional.empty() if there is no there entity there
     * or if the tile entity class doesn't match the type parameter {@link  T}.
     * @param world The world to get the tile entity from
     * @param pos The location of the tile entity
     * @return The Optional containing the tile entity, or an empty optional if it's not there / doesn't match
     */
    @SuppressWarnings("unchecked")
    default Optional<T> getTileEntityOptional(World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (this.getTEClass().isInstance(te)) return Optional.of((T)te);
        return Optional.empty();
    }

    /**
     * Checks if the tile entity is present, and if it is, performs an arbitrary action with it.
     * @param world The world of the tile entity
     * @param pos The location of the tile entity
     * @param action The action to perform if the tile entity is present
     */
    default void ifTEPresent(World world, BlockPos pos, Consumer<T> action) {
        this.getTileEntityOptional(world, pos).ifPresent(action);
    }
}
