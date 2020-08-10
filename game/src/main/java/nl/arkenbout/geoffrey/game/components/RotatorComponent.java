package nl.arkenbout.geoffrey.game.components;

import nl.arkenbout.geoffrey.angel.ecs.BaseComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import org.joml.Vector3d;

public class RotatorComponent extends BaseComponent {
    private float speed;
    private final Vector3d axis;

    public RotatorComponent(float speed) {
        this(speed, Vector3u.one());
    }

    public RotatorComponent(float speed, Vector3d axis) {
        this.speed = speed;
        this.axis = axis;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector3d getAxis() {
        return axis;
    }
}
