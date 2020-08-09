package nl.arkenbout.geoffrey.angel.engine.core.input.mouse;

import nl.arkenbout.geoffrey.angel.engine.util.Cleanup;
import org.joml.Vector2d;

public interface MouseListener extends Cleanup {
    void onLeftButtonDown(Vector2d mouseDelta);

    void onLeftButtonUp();

    void onRightButtonDown(Vector2d mouseDelta);

    void onRightButtonUp();

    void onScroll(double scrollDelta);

    void onMouseMove(Vector2d mouseDelta);

    void onMouseEnter(Vector2d enterPosition);
}
