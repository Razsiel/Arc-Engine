package nl.arkenbout.geoffrey.angel.engine.core.input.mouse;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public enum MouseButton {
    LEFT(GLFW_MOUSE_BUTTON_LEFT),
    RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
    MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),

    BACK(GLFW_MOUSE_BUTTON_3),
    FORWARD(GLFW_MOUSE_BUTTON_4),

    UNKNOWN(-1);

    private static Map<Integer, MouseButton> buttonMap = new HashMap<>();

    static {
        for (MouseButton button : values()) {
            buttonMap.put(button.value, button);
        }
    }

    private final int value;

    MouseButton(int value) {
        this.value = value;
    }

    public static MouseButton fromGlfwButtonCode(int glfwButtonCode) {
        if (!buttonMap.containsKey(glfwButtonCode)) {
            return UNKNOWN;
        }
        return buttonMap.get(glfwButtonCode);
    }
}
