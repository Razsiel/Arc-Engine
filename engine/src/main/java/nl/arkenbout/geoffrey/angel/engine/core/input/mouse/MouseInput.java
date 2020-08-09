package nl.arkenbout.geoffrey.angel.engine.core.input.mouse;

import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyModifier;
import nl.arkenbout.geoffrey.angel.engine.util.Cleanup;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class MouseInput implements Cleanup {
    private static List<MouseListener> mouseListeners = new ArrayList<>();
    private final Vector2d currentPosition = new Vector2d(-1, -1);
    private final Vector2d previousPosition = new Vector2d(0, 0);
    private final Vector2f mouseDelta = new Vector2f();
    private final Window window;
    private boolean inWindow;
    private boolean leftButtonPressed;
    private boolean leftButtonReleased;
    private boolean rightButtonPressed;
    private boolean rightButtonReleased;
    private boolean middleButtonPressed;
    private boolean middleButtonReleased;

    public MouseInput(Window window) {
        this.window = window;
    }

    public static MouseInput forWindow(Window window) {
        return new MouseInput(window);
    }

    public static boolean registerMouseListener(MouseListener mouseListener) {
        return mouseListeners.add(mouseListener);
    }

    public static boolean unregisterMouseListener(MouseListener mouseListener) {
        return mouseListeners.remove(mouseListener);
    }

    public void init() {
        long handle = this.window.getWindowHandle();
        glfwSetCursorPosCallback(handle, (windowHandle, x, y) -> {
            this.currentPosition.set(x, y);
            mouseListeners.forEach(mouseListener -> mouseListener.onMouseMove(new Vector2d(x, y)));
        });
        glfwSetCursorEnterCallback(handle, (windowHandle, entered) -> {
            this.inWindow = entered;
            if (entered)
                mouseListeners.forEach(mouseListener -> mouseListener.onMouseEnter(this.currentPosition));
        });
        glfwSetMouseButtonCallback(handle, (windowHandle, buttonCode, action, mods) -> {
            var button = MouseButton.fromGlfwButtonCode(buttonCode);
            var modifier = KeyModifier.fromGlfwModifierCode(mods);
            System.out.println("Button " + buttonCode + "with " + modifier);
            // TODO: Refactor to be more like Keyboard input
//            var leftButton = button == GLFW_MOUSE_BUTTON_1;
//            var rightButton = button == GLFW_MOUSE_BUTTON_2;
//            var middleButton = button == GLFW_MOUSE_BUTTON_3;
//
//            var pressed = action == GLFW_PRESS;
//            var released = action == GLFW_RELEASE;
//
//            leftButtonPressed = leftButton && pressed;
//            leftButtonReleased = leftButton && released;
//
//            rightButtonPressed = rightButton && pressed;
//            rightButtonReleased = rightButton && released;
//
//            middleButtonPressed = middleButton && pressed;
//            middleButtonReleased = middleButton && released;
        });
        glfwSetScrollCallback(handle, (windowHandle, scrollX, scrollY) -> {
            mouseListeners.forEach(mouseListener -> mouseListener.onScroll((float) scrollY));
        });
    }

    public void input() {
        updateMouseDelta();
        updateMouseListeners();
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

    private void updateMouseListeners() {
        if (leftButtonPressed) {
            mouseListeners.forEach(mouseListener -> mouseListener.onLeftButtonDown(mouseDelta));
        }
        if (leftButtonReleased) {
            mouseListeners.forEach(MouseListener::onLeftButtonUp);
        }

        if (rightButtonPressed) {
            mouseListeners.forEach(mouseListener -> mouseListener.onRightButtonDown(mouseDelta));
        }
        if (rightButtonReleased) {
            mouseListeners.forEach(MouseListener::onRightButtonUp);
        }
    }

    public void cleanup() {
        mouseListeners.forEach(Cleanup::cleanup);
        glfwFreeCallbacks(window.getWindowHandle());
    }
}
