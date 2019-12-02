package nl.arkenbout.geoffrey.game.components;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import org.joml.Vector3f;

public class RotatorComponent implements Component {
    private float speed;
    private final Vector3f axis;

    public RotatorComponent(float speed) {
        this(speed, Vector3u.one());
    }

    public RotatorComponent(float speed, Vector3f axis) {
        this.speed = speed;
        this.axis = axis;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector3f getAxis() {
        return axis;
    }
}
