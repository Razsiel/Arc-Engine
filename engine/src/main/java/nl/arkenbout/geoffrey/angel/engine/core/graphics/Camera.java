package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Cameras;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import org.joml.Vector3d;

public class Camera {
    private final double near;
    private final double far;
    private final double fov;
    private Vector3d position;
    private Vector3d rotation;
    private boolean isOrtho;

    public Camera(Vector3d position, Vector3d rotation, double near, double far, double fov, boolean isOrtho) {
        this.position = position;
        this.rotation = rotation;
        this.near = near;
        this.far = far;
        this.fov = fov;
        this.isOrtho = isOrtho;
        Cameras.addCamera(this);
    }

    public Vector3d getPosition() {
        return position;
    }

    public Vector3d getRotation() {
        return rotation;
    }

    public void move(double x, double y, double z) {
        if (z != 0) {
            position.x += Math.sin(Math.toRadians(rotation.y)) * -1.0f * z;
            position.z += Math.cos(Math.toRadians(rotation.y)) * z;
        }
        if (x != 0) {
            position.x += Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * x;
            position.z += Math.cos(Math.toRadians(rotation.y - 90)) * x;
        }
        position.y += y;
    }

    public void rotate(double x, double y, double z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
    }

    public double getNear() {
        return near;
    }

    public double getFar() {
        return far;
    }

    public double getFov() {
        return fov;
    }

    public boolean isOrtho() {
        return this.isOrtho;
    }

    public Vector3d forward() {
        return Matrices.getViewMatrix(this).invert().positiveZ(Vector3u.zero()).negate();
    }
}
