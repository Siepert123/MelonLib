package nl.melonstudios.melonlib.math;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class OrientedBB {
    Vec3d center;
    Vec3d extents;
    Matrix3d rotation;

    public OrientedBB(AxisAlignedBB aabb) {
        this(aabb.getCenter(), extentsFromBB(aabb), new Matrix3d().asIdentity());
    }

    public OrientedBB() {
        this(Vec3d.ZERO, Vec3d.ZERO, new Matrix3d().asIdentity());
    }

    public OrientedBB(Vec3d center, Vec3d extents, Matrix3d rotation) {
        this.setCenter(center);
        this.extents = extents;
        this.setRotation(rotation);
    }

    public OrientedBB copy() {
        return new OrientedBB(this.center, this.extents, this.rotation);
    }

    public Vec3d intersect(AxisAlignedBB aabb) {
        Vec3d extentsA = extentsFromBB(aabb);
        return OBBCollider.separateBBs(aabb.getCenter(), this.center, extentsA, this.extents, this.rotation);
    }

    public ContinuousOBBCollider.ContinuousSeparationManifold intersect(AxisAlignedBB aabb, Vec3d motion) {
        Vec3d extentsA = extentsFromBB(aabb);
        return ContinuousOBBCollider.separateBBs(aabb.getCenter(), this.center, extentsA, this.extents, this.rotation, motion);
    }

    private static Vec3d extentsFromBB(AxisAlignedBB aabb) {
        return new Vec3d((aabb.maxX - aabb.minX) / 2, (aabb.maxY - aabb.minY) / 2, (aabb.maxZ - aabb.minZ) / 2);
    }

    public Matrix3d getRotation() {
        return this.rotation;
    }
    public void setRotation(Matrix3d rotation) {
        this.rotation = rotation;
    }

    public Vec3d getCenter() {
        return this.center;
    }
    public void setCenter(Vec3d center) {
        this.center = center;
    }
    public void move(Vec3d offset) {
        this.setCenter(this.getCenter().add(offset));
    }

    public AxisAlignedBB getAsAABB() {
        double x = this.extents.x / 2;
        double y = this.extents.y / 2;
        double z = this.extents.z / 2;
        return new AxisAlignedBB(0, 0, 0, 0, 0, 0).offset(this.center)
                .expand(x, y, z).expand(-x, -y, -z);
    }
}
