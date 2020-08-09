package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Matrices {
    public static Matrix4f getProjectionMatrix(Window window, Camera camera) {
        return getProjectionMatrix(camera.getFov(), window.getWidth(), window.getHeight(), camera.getNear(), camera.getFar());
    }

    public static Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        var projectionMatrix = new Matrix4f();
        float aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    public static Matrix4f getWorldMatrix(TransformComponent t) {
        return getWorldMatrix(t.getPosition(), t.getRotation(), t.getScale());
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

    public static Matrix4f getViewMatrix(Camera camera) {
        var cameraPosition = camera.getPosition();
        var cameraRotation = camera.getRotation();

        var viewMatrix = new Matrix4f();
        double cameraRotationXRadians = Math.toRadians(cameraRotation.x());
        double cameraRotationYRadians = Math.toRadians(cameraRotation.y());
        viewMatrix.identity()
                .rotate((float) cameraRotationXRadians, Vector3u.right())
                .rotate((float) cameraRotationYRadians, Vector3u.up())
                .translate(-cameraPosition.x(), -cameraPosition.y(), -cameraPosition.z());
        return viewMatrix;
    }

    public static Matrix4f getModelViewMatrix(TransformComponent transformComponent, Matrix4f viewMatrix) {
        var modelViewMatrix = new Matrix4f().identity();

        modelViewMatrix = transformComponent.getModelViewMatrix(modelViewMatrix);

        var viewCurrent = new Matrix4f(viewMatrix);
        return viewCurrent.mul(modelViewMatrix);

    }

    public static Matrix4f getLightViewMatrix(Vector3f position, Vector3f rotation) {
        var lightViewMatrix = new Matrix4f();
        lightViewMatrix.rotate((float) Math.toRadians(rotation.x), Vector3u.right())
                .rotate((float) Math.toRadians(rotation.y), Vector3u.up())
                .translate(-position.x, -position.y, -position.z);
        return lightViewMatrix;
    }

    public static Matrix4f getOrthoProjectionMatrix(float left, float right, float bottom, float top, float near, float far) {
        var orthoMatrix = new Matrix4f();
        orthoMatrix.setOrtho(left, right, bottom, top, near, far);
        return orthoMatrix;
    }
}
