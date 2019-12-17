package nl.arkenbout.geoffrey.angel.engine.component;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import org.joml.Vector3f;

public class TransformComponent implements Component {
    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    public TransformComponent() {
        this(Vector3u.zero(), Vector3u.zero(), 1f);
    }

    public TransformComponent(Vector3f position, Vector3f rotation, float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
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

    public static TransformComponent identity() {
        return new TransformComponent();
    }
}
