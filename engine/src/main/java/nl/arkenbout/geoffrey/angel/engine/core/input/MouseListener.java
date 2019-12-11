package nl.arkenbout.geoffrey.angel.engine.core.input;

import org.joml.Vector2f;

public interface MouseListener {
    void onLeftButtonDown(Vector2f mouseDelta);
    void onLeftUp();

    void onRightButtonDown(Vector2f mouseDelta);
    void onRightUp();

    void onScroll(float scrollDelta);

    void onMouseMove(Vector2f mouseDelta);
}
