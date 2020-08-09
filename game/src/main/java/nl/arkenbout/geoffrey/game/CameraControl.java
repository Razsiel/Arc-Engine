package nl.arkenbout.geoffrey.game;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Cameras;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.Key;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyModifier;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyboardListener;
import nl.arkenbout.geoffrey.angel.engine.core.input.mouse.MouseListener;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;

public class CameraControl implements MouseListener, KeyboardListener {
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static float CAMERA_MOVE_SENSITIVITY = 0.1f;

    private final Vector3f cameraDelta = Vector3u.zero();

    CameraControl() {
    }

    @Override
    public void onLeftButtonDown(Vector2f mouseDelta) {
        Camera mainCamera = Cameras.main();
        mainCamera.rotate(mouseDelta.x() * MOUSE_SENSITIVITY, mouseDelta.y() * MOUSE_SENSITIVITY, 0);
    }

    @Override
    public void onLeftButtonUp() {

    }

    @Override
    public void onRightButtonDown(Vector2f mouseDelta) {

    }

    @Override
    public void onRightButtonUp() {

    }

    @Override
    public void onScroll(float scrollDelta) {
        CAMERA_MOVE_SENSITIVITY = Math.abs(CAMERA_MOVE_SENSITIVITY + scrollDelta * 0.1f);
        if (CAMERA_MOVE_SENSITIVITY < 0.05f) {
            CAMERA_MOVE_SENSITIVITY = 0.05f;
        }
        if (CAMERA_MOVE_SENSITIVITY > 1f) {
            CAMERA_MOVE_SENSITIVITY = 1f;
        }
        System.out.println("CAMERA SPEED = " + CAMERA_MOVE_SENSITIVITY);
    }

    @Override
    public void onMouseMove(Vector2d mouseDelta) {

    }

    @Override
    public void onMouseEnter(Vector2d enterPosition) {

    }

    @Override
    public void onKeyDown(Key key, List<KeyModifier> modifiers) {

    }

    @Override
    public void onKeyUp(Key key, List<KeyModifier> modifiers) {

    }

    @Override
    public void onKeys(List<Key> keys, List<KeyModifier> modifiers) {
        if (keys == null || keys.size() == 0) {
            return;
        }

        Camera mainCamera = Cameras.main();
        cameraDelta.set(0);

        // Forward and back movement
        if (keys.contains(Key.W) || keys.contains(Key.UP)) {
            cameraDelta.z = -1;
        } else if (keys.contains(Key.S) || keys.contains(Key.DOWN)) {
            cameraDelta.z = 1;
        }

        // Left and right movement
        if (keys.contains(Key.A) || keys.contains(Key.LEFT)) {
            cameraDelta.x = -1;
        } else if (keys.contains(Key.D) || keys.contains(Key.RIGHT)) {
            cameraDelta.x = 1;
        }

        // Up and down movement
        if (keys.contains(Key.SPACE)) {
            cameraDelta.y = 1;
        } else if (keys.contains(Key.LEFT_SHIFT) || keys.contains(Key.RIGHT_SHIFT)) {
            cameraDelta.y = -1;
        }

        mainCamera.move(cameraDelta.x() * CAMERA_MOVE_SENSITIVITY,
                cameraDelta.y() * CAMERA_MOVE_SENSITIVITY,
                cameraDelta.z() * CAMERA_MOVE_SENSITIVITY);
    }

    @Override
    public void cleanup() {

    }
}
