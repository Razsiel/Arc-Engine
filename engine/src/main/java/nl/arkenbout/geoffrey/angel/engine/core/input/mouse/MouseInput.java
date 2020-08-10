package nl.arkenbout.geoffrey.angel.engine.core.input.mouse;

import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyModifier;
import nl.arkenbout.geoffrey.angel.engine.util.Cleanup;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class MouseInput implements Cleanup {
    private static List<MouseListener> mouseListeners = new ArrayList<>();
    private final Vector2d currentPosition = new Vector2d(-1, -1);
    private final Vector2d previousPosition = new Vector2d(0, 0);
    private final Vector2d mouseDelta = new Vector2d();
    private final Window window;
    private boolean inWindow;

    private Set<MouseButton> buttonsPressed = new HashSet<>();
    private List<KeyModifier> modifiers;

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
            this.modifiers = KeyModifier.fromGlfwModifierCode(mods);

            if (action == GLFW_PRESS || action == GLFW_REPEAT) {
                mouseListeners.forEach(mouseListener -> mouseListener.onMouseDown(button, modifiers, currentPosition));
                this.buttonsPressed.add(button);
            } else if (action == GLFW_RELEASE) {
                mouseListeners.forEach(mouseListener -> mouseListener.onMouseUp(button, modifiers, currentPosition));
                this.buttonsPressed.remove(button);
            }
        });
        glfwSetScrollCallback(handle, (windowHandle, scrollX, scrollY) -> mouseListeners.forEach(mouseListener -> mouseListener.onScroll(scrollY)));
    }

    public void input() {
        updateMouseDelta();
        updateMouseListeners();
    }

    private void updateMouseListeners() {
        mouseListeners.forEach(mouseListener -> mouseListener.onMouseUpdate(this.buttonsPressed, this.modifiers, mouseDelta, currentPosition));
    }

    private void updateMouseDelta() {
        mouseDelta.set(0, 0);
        if (inWindow && previousPosition.x() > 0 && previousPosition.y() > 0) {
            var deltaY = currentPosition.x() - previousPosition.x();
            var deltaX = currentPosition.y() - previousPosition.y();
            var delta = new Vector2d(deltaX, deltaY);
            mouseDelta.set(delta);
        }
        previousPosition.set(currentPosition);
    }

    public void cleanup() {
        mouseListeners.forEach(Cleanup::cleanup);
        glfwFreeCallbacks(window.getWindowHandle());
    }
}
