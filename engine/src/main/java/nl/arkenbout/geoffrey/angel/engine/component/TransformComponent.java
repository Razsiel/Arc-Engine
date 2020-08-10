package nl.arkenbout.geoffrey.angel.engine.component;

import nl.arkenbout.geoffrey.angel.ecs.BaseComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import org.joml.Math;
import org.joml.Matrix4d;
import org.joml.Vector3d;

public class TransformComponent extends BaseComponent {
    private Vector3d position;
    private Vector3d rotation;
    private double scale;
    private TransformComponent parent;

    private static Matrix4d TRANSLATION_MATRIX = new Matrix4d();

    public TransformComponent() {
        this(null);
    }

    public TransformComponent(TransformComponent parent) {
        this(Vector3u.zero(), Vector3u.zero(), 1f, parent);
    }

    public TransformComponent(Vector3d position, Vector3d rotation, double scale) {
        this(position, rotation, scale, null);
    }

    public TransformComponent(Vector3d position, Vector3d rotation, double scale, TransformComponent parent) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.parent = parent;
    }

    public static TransformComponent origin() {
        return new TransformComponent();
    }

    public static TransformComponent parented(TransformComponent parent) {
        return new TransformComponent(parent);
    }

    public void move(Vector3d vector, double scalar) {
        Vector3d offset = vector.mul(scalar);
        TRANSLATION_MATRIX.identity()
                .translation(offset);
        this.position = this.getPosition().mulDirection(TRANSLATION_MATRIX);
    }

    public Vector3d getRotation() {
        return rotation;
    }

    public void setRotation(Vector3d rotation) {
        this.rotation = rotation;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public void setPosition(double x, double y, double z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public TransformComponent getParent() {
        return parent;
    }

    public void setParent(TransformComponent parent) {
        if (parent == null) {
            this.parent = null;
            return;
        }

        if (parent == this) {
            throw new IllegalArgumentException("Parent cannot be 'this'");
        }

        TransformComponent p = parent;

        do {
            p = p.getParent();
            if (p == this) {
                throw new IllegalArgumentException("The parent transform could not be added since it would create a recursive parent loop");
            }
        } while (p != null && p.hasParent());

        this.parent = parent;
    }

    public Matrix4d getModelViewMatrix(Matrix4d modelViewMatrix) {
        Matrix4d matrix = hasParent() ? parent.getModelViewMatrix(modelViewMatrix) : modelViewMatrix;
        return new Matrix4d(matrix)
                .translate(position)
                .rotateX(Math.toRadians(-rotation.x()))
                .rotateY(Math.toRadians(-rotation.y()))
                .rotateZ(Math.toRadians(-rotation.z()))
                .scale(scale);
    }
}
