package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class Cameras {

    private static List<Camera> cameras = new ArrayList<>();
    private static Camera mainCamera;

    public static Camera main() {
        return mainCamera;
    }

    public static void setMainCamera(Camera camera) {
        mainCamera = camera;
    }

    public static void addCamera(Camera camera) {
        cameras.add(camera);
    }

    public static Camera defaultCamera() {
        return new Camera(new Vector3d(0d, 0d, -5d), Vector3u.up().mul(180), 0.01d, 1000d, Math.toRadians(60.0d), false);
    }
}
