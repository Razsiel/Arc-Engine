package nl.arkenbout.geoffrey.angel.engine.core.input.keyboard;

import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.util.Cleanup;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput implements Cleanup {
    private static final Set<KeyboardListener> keyboardListeners = new HashSet<>();

    private final Set<Key> keysPressed = new HashSet<>();
    private Window window;

    private KeyboardInput(Window window) {
        this.window = window;
    }

    public static boolean registerKeyboardListener(KeyboardListener callback) {
        if (keyboardListeners.contains(callback)) {
            throw new IllegalArgumentException("Trying to register an already registered keyboard callback from " + callback.getClass().getName());
        }
        return keyboardListeners.add(callback);
    }

    public static KeyboardInput forWindow(Window window) {
        return new KeyboardInput(window);
    }

    public void init() {
        long handle = this.window.getWindowHandle();
        glfwSetKeyCallback(handle, (windowHandle, keycode, scancode, action, mods) -> {
            var key = Key.fromGlfwKeyCode(keycode);
            var modifiers = KeyModifier.fromGlfwModifierCode(mods);

            if (action == GLFW_PRESS || action == GLFW_REPEAT) {
                keyboardListeners.forEach(keyboardListener -> keyboardListener.onKeyDown(key, modifiers));
                keysPressed.add(key);
            } else if (action == GLFW_RELEASE) {
                keyboardListeners.forEach(keyboardListener -> keyboardListener.onKeyUp(key, modifiers));
                keysPressed.remove(key);
            }
        });
    }

    public void input() {
        keyboardListeners.forEach(keyboardListener -> keyboardListener.onKeyboardUpdate(keysPressed, null));
    }

    public void cleanup() {
        glfwFreeCallbacks(window.getWindowHandle());
    }

}

