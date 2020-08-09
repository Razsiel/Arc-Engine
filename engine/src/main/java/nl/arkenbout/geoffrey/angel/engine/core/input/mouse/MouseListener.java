package nl.arkenbout.geoffrey.angel.engine.core.input.mouse;

import nl.arkenbout.geoffrey.angel.engine.util.Cleanup;
import org.joml.Vector2d;
import org.joml.Vector2f;

public interface MouseListener extends Cleanup {
    void onLeftButtonDown(Vector2f mouseDelta);

    void onLeftButtonUp();

    void onRightButtonDown(Vector2f mouseDelta);

    void onRightButtonUp();

    void onScroll(float scrollDelta);

    void onMouseMove(Vector2d mouseDelta);

    void onMouseEnter(Vector2d enterPosition);
}
