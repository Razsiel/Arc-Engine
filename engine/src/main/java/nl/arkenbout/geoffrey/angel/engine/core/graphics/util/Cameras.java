package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;

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
}
