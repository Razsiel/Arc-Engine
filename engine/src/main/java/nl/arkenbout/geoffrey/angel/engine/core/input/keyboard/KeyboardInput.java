package nl.arkenbout.geoffrey.angel.engine.core.input.keyboard;

import nl.arkenbout.geoffrey.angel.engine.Window;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput {
    private static final Set<KeyboardListener> callbacks = new HashSet<>();

    private final List<Key> keysPressed = new ArrayList<>();
    private Window window;

    private KeyboardInput(Window window) {
        this.window = window;
    }

    public static boolean registerKeyboardListener(KeyboardListener callback) {
        if (callbacks.contains(callback)) {
            throw new IllegalArgumentException("Trying to register an already registered keyboard callback from " + callback.getClass().getName());
        }
        return callbacks.add(callback);
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
                callbacks.forEach(keyboardListener -> keyboardListener.onKeyDown(key, modifiers));
                if (!keysPressed.contains(key)) {
                    keysPressed.add(key);
                }
            } else if (action == GLFW_RELEASE) {
                callbacks.forEach(keyboardListener -> keyboardListener.onKeyUp(key, modifiers));
                keysPressed.remove(key);
            }
        });
    }

    public void input() {
        callbacks.forEach(keyboardListener -> keyboardListener.onKeys(keysPressed, null));
    }

    public void cleanup(Window window) {
        glfwFreeCallbacks(window.getWindowHandle());
    }

}

