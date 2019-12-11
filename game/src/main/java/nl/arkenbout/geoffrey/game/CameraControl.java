package nl.arkenbout.geoffrey.game;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Cameras;
import nl.arkenbout.geoffrey.angel.engine.core.input.MouseListener;
import org.joml.Vector2f;

public class CameraControl implements MouseListener {
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static final float SCROLL_SENSITIVITY = 0.2f;

    @Override
    public void onLeftButtonDown(Vector2f mouseDelta) {
        Camera mainCamera = Cameras.main();
        mainCamera.rotate(mouseDelta.x() * MOUSE_SENSITIVITY, mouseDelta.y() * MOUSE_SENSITIVITY, 0);
    }

    @Override
    public void onLeftUp() {

    }

    @Override
    public void onRightButtonDown(Vector2f mouseDelta) {

    }

    @Override
    public void onRightUp() {

    }

    @Override
    public void onScroll(double scrollDelta) {
        Camera mainCamera = Cameras.main();
        mainCamera.moveForwards((float) -scrollDelta * SCROLL_SENSITIVITY);
    }

    @Override
    public void onMouseMove(Vector2f mouseDelta) {

    }
}
