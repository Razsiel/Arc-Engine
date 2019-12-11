package nl.arkenbout.geoffrey.angel.engine.core.input;

import nl.arkenbout.geoffrey.angel.engine.Window;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.util.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {
    private final Vector2d currentPosition = new Vector2d(-1, -1);
    private final Vector2d previousPosition = new Vector2d(0, 0);
    private final Vector2f mouseDelta = new Vector2f();
    private boolean inWindow;

    private boolean leftButtonPressed;
    private boolean leftButtonReleased;
    private boolean rightButtonPressed;
    private boolean rightButtonReleased;

    private static List<MouseListener> mouseListeners = new ArrayList<>();

    public void init(Window window) {
        long handle = window.getWindowHandle();
        glfwSetCursorPosCallback(handle, (windowHandle, x, y) -> {
            this.currentPosition.set(x, y);
        });
        glfwSetCursorEnterCallback(handle, (windowHandle, entered) -> {
            this.inWindow = entered;
        });
        glfwSetMouseButtonCallback(handle, (windowHandle, button, action, mode) -> {
            var leftButton = button == GLFW_MOUSE_BUTTON_1;
            var rightButton = button == GLFW_MOUSE_BUTTON_2;

            var pressed = action == GLFW_PRESS;
            var released = action == GLFW_RELEASE;

            leftButtonPressed = leftButton && pressed;
            leftButtonReleased = leftButton && released;

            rightButtonPressed = rightButton && pressed;
            rightButtonReleased = rightButton && released;
        });
        glfwSetScrollCallback(handle, (windowHandle, scrollX, scrollY) -> {
            mouseListeners.forEach(mouseListener -> mouseListener.onScroll(scrollY));
        });
    }

    public void input() {
        updateMouseDelta();
        updateMouseListeners();
    }

    private void updateMouseListeners() {
        if (leftButtonPressed) {
            mouseListeners.forEach(mouseListener -> mouseListener.onLeftButtonDown(mouseDelta));
        }
        if (leftButtonReleased) {
            mouseListeners.forEach(MouseListener::onLeftUp);
        }

        if (rightButtonPressed) {
            mouseListeners.forEach(mouseListener -> mouseListener.onRightButtonDown(mouseDelta));
        }
        if (rightButtonReleased) {
            mouseListeners.forEach(MouseListener::onRightUp);
        }
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

    public static boolean registerMouseCallback(MouseListener mouseListener) {
        return mouseListeners.add(mouseListener);
    }

    public static boolean unregisterMouseListener(MouseListener mouseListener) {
        return mouseListeners.remove(mouseListener);
    }

    public void cleanup(Window window) {
        glfwFreeCallbacks(window.getWindowHandle());
    }
}
