package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import org.joml.Matrix4d;
import org.joml.Vector3d;

public class Matrices {
    public static Matrix4d viewMatrix = new Matrix4d();
    public static Matrix4d projectionMatrix = new Matrix4d();
    public static Matrix4d worldMatrix = new Matrix4d();
    public static Matrix4d modelViewMatrix = new Matrix4d();
    public static Matrix4d lightViewMatrix = new Matrix4d();
    public static Matrix4d orthographicMatrix = new Matrix4d();

    public static Matrix4d getProjectionMatrix(Window window, Camera camera) {
        return getProjectionMatrix(camera.getFov(), window.getWidth(), window.getHeight(), camera.getNear(), camera.getFar());
    }

    public static Matrix4d getProjectionMatrix(double fov, double width, double height, double zNear, double zFar) {
        var aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    public static Matrix4d getWorldMatrix(TransformComponent t) {
        return getWorldMatrix(t.getPosition(), t.getRotation(), t.getScale());
    }

    public static Matrix4d getWorldMatrix(Vector3d offset, Vector3d rotation, double scale) {
        worldMatrix.identity()
                .translate(offset)
                .rotateX(Math.toRadians(rotation.x))
                .rotateY(Math.toRadians(rotation.y))
                .rotateZ(Math.toRadians(rotation.z))
                .scale(scale);
        return worldMatrix;
    }

    public static Matrix4d getViewMatrix(Camera camera) {
        var cameraPosition = camera.getPosition();
        var cameraRotation = camera.getRotation();

        var cameraRotationXRadians = Math.toRadians(cameraRotation.x());
        var cameraRotationYRadians = Math.toRadians(cameraRotation.y());
        viewMatrix.identity()
                .rotate(cameraRotationXRadians, Vector3u.right())
                .rotate(cameraRotationYRadians, Vector3u.up())
                .translate(-cameraPosition.x(), -cameraPosition.y(), -cameraPosition.z());
        return viewMatrix;
    }

    public static Matrix4d getModelViewMatrix(TransformComponent transformComponent, Matrix4d viewMatrix) {
        modelViewMatrix.identity();
        modelViewMatrix = transformComponent.getModelViewMatrix(modelViewMatrix);

        var viewCurrent = new Matrix4d(viewMatrix);
        return viewCurrent.mul(modelViewMatrix);

    }

    public static Matrix4d getLightViewMatrix(Vector3d position, Vector3d rotation) {
        lightViewMatrix
                .identity()
                .rotate(Math.toRadians(rotation.x), Vector3u.right())
                .rotate(Math.toRadians(rotation.y), Vector3u.up())
                .translate(-position.x, -position.y, -position.z);
        return lightViewMatrix;
    }

    public static Matrix4d getOrthoProjectionMatrix(double left, double right, double bottom, double top, double near, double far) {
        orthographicMatrix
                .identity()
                .setOrtho(left, right, bottom, top, near, far);
        return orthographicMatrix;
    }
}
