package nl.arkenbout.geoffrey.arc.engine.component;

import nl.arkenbout.geoffrey.arc.ecs.Component;
import org.joml.Vector3f;

public class TransformComponent implements Component {
    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    public TransformComponent() {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1f);
    }

    public TransformComponent(Vector3f position, Vector3f rotation, float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
