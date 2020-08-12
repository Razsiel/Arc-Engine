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
    private final Vector2d currentPosition = new Vector2d(-1);
    private final Vector2d relativeScreenPos = new Vector2d();
    private final Vector2d previousPosition = new Vector2d(0);
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
            var screenX = (2 * x) / window.getWidth() - 1.0f;
            var screenY = 1.0f - (2 * y) / window.getHeight();

            this.currentPosition.set(x, y);
            this.relativeScreenPos.set(screenX, screenY);

            mouseListeners.forEach(mouseListener -> mouseListener.onMouseMove(new Vector2d(x, y)));
        });
        glfwSetCursorEnterCallback(handle, (windowHandle, entered) -> {
            this.inWindow = entered;
            if (entered)
                mouseListeners.forEach(mouseListener -> mouseListener.onMouseEnter(this.relativeScreenPos));
        });
        glfwSetMouseButtonCallback(handle, (windowHandle, buttonCode, action, mods) -> {
            var button = MouseButton.fromGlfwButtonCode(buttonCode);
            this.modifiers = KeyModifier.fromGlfwModifierCode(mods);

            if (action == GLFW_PRESS || action == GLFW_REPEAT) {
                mouseListeners.forEach(mouseListener -> mouseListener.onMouseDown(button, modifiers, relativeScreenPos));
                this.buttonsPressed.add(button);
            } else if (action == GLFW_RELEASE) {
                mouseListeners.forEach(mouseListener -> mouseListener.onMouseUp(button, modifiers, relativeScreenPos));
                this.buttonsPressed.remove(button);
            }
        });
        glfwSetScrollCallback(handle, (windowHandle, scrollX, scrollY) -> mouseListeners.forEach(mouseListener -> mouseListener.onScroll(scrollY)));
    }

    public void update() {
        updateMouseDelta();
        updateMouseListeners();
    }

    private void updateMouseListeners() {
        mouseListeners.forEach(mouseListener -> mouseListener.update(this.buttonsPressed, this.modifiers, mouseDelta, relativeScreenPos));
    }

    private void updateMouseDelta() {
        mouseDelta.set(0, 0);
        if (inWindow && currentPosition.x() > 0 && currentPosition.y() > 0) {
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
