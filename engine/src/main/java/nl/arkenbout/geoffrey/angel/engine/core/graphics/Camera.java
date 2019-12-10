package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Cameras;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private Vector3f rotation;

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
        Cameras.addCamera(this);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void moveForwards(float offset) {
        if (offset != 0f) {
            position.z += Math.cos(Math.toRadians(rotation.y)) * offset;
        }
    }

    public void rotate(float x, float y, int z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
    }
}
