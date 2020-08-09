package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import org.joml.Vector3f;

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
        return new Camera(new Vector3f(0f, 0f, -5f), Vector3u.up().mul(180), 0.01f, 1000f, (float) Math.toRadians(60.0f), false);
    }
}
