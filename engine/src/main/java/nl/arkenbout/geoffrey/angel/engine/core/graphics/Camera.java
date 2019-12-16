package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Cameras;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private Vector3f rotation;
    private final float near;
    private final float far;
    private final float fov;

    public Camera(Vector3f position, Vector3f rotation, float near, float far, float fov) {
        this.position = position;
        this.rotation = rotation;
        this.near = near;
        this.far = far;
        this.fov = fov;
        Cameras.addCamera(this);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void move(float x, float y, float z) {
        if ( z != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * z;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * z;
        }
        if ( x != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * x;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * x;
        }
        position.y += y;
    }

    public void rotate(float x, float y, int z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
    }

    public float getNear() {
        return near;
    }

    public float getFar() {
        return far;
    }

    public float getFov() {
        return fov;
    }
}
