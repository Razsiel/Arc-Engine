package nl.arkenbout.geoffrey.arc.engine.core.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {
    public static Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        var projectionMatrix = new Matrix4f();
        float aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    public static Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
        var worldMatrix = new Matrix4f();
        worldMatrix.identity()
                .translate(offset)
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateY((float) Math.toRadians(rotation.y))
                .rotateZ((float) Math.toRadians(rotation.z))
                .scale(scale);
        return worldMatrix;
    }
}
