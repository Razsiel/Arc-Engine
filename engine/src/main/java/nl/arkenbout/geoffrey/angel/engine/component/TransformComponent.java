package nl.arkenbout.geoffrey.angel.engine.component;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TransformComponent implements Component {
    private Vector3f position;
    private Vector3f rotation;
    private float scale;
    private TransformComponent parent;

    public TransformComponent() {
        this(null);
    }

    public TransformComponent(TransformComponent parent) {
        this(Vector3u.zero(), Vector3u.zero(), 1f, parent);
    }

    public TransformComponent(Vector3f position, Vector3f rotation, float scale) {
        this(position, rotation, scale, null);
    }

    public TransformComponent(Vector3f position, Vector3f rotation, float scale, TransformComponent parent) {
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

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setPositionY(float newY) {
        this.position.y = newY;
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
            throw new IllegalArgumentException("Parent cannot be \'this\'");
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

    public Matrix4f getModelViewMatrix(Matrix4f modelViewMatrix) {
        Matrix4f matrix = hasParent() ? parent.getModelViewMatrix(modelViewMatrix) : modelViewMatrix;
        return new Matrix4f(matrix)
                .translate(position)
                .rotateX((float) Math.toRadians(-rotation.x()))
                .rotateY((float) Math.toRadians(-rotation.y()))
                .rotateZ((float) Math.toRadians(-rotation.z()))
                .scale(scale);
    }

    public void move(Vector3f vector, float scalar) {
        Vector3f offset = vector.mul(scalar);
        Matrix4f translation = new Matrix4f().identity()
                .translation(offset);
        this.position = this.getPosition().mulDirection(translation);
    }
}
