package nl.arkenbout.geoffrey.angel.engine.core.input.mouse;

import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyModifier;
import nl.arkenbout.geoffrey.angel.engine.util.Cleanup;
import org.joml.Vector2d;

import java.util.List;
import java.util.Set;

public interface MouseListener extends Cleanup {
    void onScroll(double scrollDelta);

    void onMouseMove(Vector2d mouseDelta);

    void onMouseEnter(Vector2d enterPosition);

    void onMouseDown(MouseButton button, List<KeyModifier> modifiers, Vector2d position);

    void onMouseUp(MouseButton button, List<KeyModifier> modifiers, Vector2d position);

    void onMouseUpdate(Set<MouseButton> buttonsPressed, List<KeyModifier> modifiers, Vector2d mouseDelta, Vector2d position);
}
