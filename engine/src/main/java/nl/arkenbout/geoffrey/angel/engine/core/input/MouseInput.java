package nl.arkenbout.geoffrey.angel.engine.core.input;

import nl.arkenbout.geoffrey.angel.engine.Window;
import org.joml.Vector2d;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {
    private final Vector2d currentPosition = new Vector2d(-1, -1);
    private final Vector2d previousPosition = new Vector2d(0, 0);
    private final Vector2f mouseDelta = new Vector2f();
    private boolean inWindow;
    private boolean leftButtonPressed;
    private boolean rightButtonPressed;

    public void init(Window window) {
        long handle = window.getWindowHandle();
        glfwSetCursorPosCallback(handle, (windowHandle, x, y) -> {
            this.currentPosition.set(x, y);
        });
        glfwSetCursorEnterCallback(handle, (windowHandle, entered) -> {
            this.inWindow = true;
        });
        glfwSetMouseButtonCallback(handle, (windowHandle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }

    public void input() {
        updateMouseDelta();
    }

    private void updateMouseDelta() {
        mouseDelta.set(0, 0);
        if (inWindow && previousPosition.x() > 0 && previousPosition.y() > 0) {
            double deltaY = currentPosition.x() - previousPosition.x();
            double deltaX = currentPosition.y() - previousPosition.y();
            Vector2d delta = new Vector2d(deltaX, deltaY);
            mouseDelta.set(delta);
        }
        previousPosition.set(currentPosition);
    }

    public Vector2f getMouseDelta() {
        return new Vector2f(mouseDelta);
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
}
