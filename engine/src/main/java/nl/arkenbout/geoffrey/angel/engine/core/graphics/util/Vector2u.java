package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import org.joml.Vector2f;

public class Vector2u {
    public static Vector2f one() {
        return new Vector2f(1, 1);
    }

    public static Vector2f zero() {
        return new Vector2f(0, 0);
    }

    public static Vector2f up() {
        return new Vector2f(0, 1);
    }

    public static Vector2f down() {
        return new Vector2f(0, -1);
    }

    public static Vector2f left() {
        return new Vector2f(-1, 0);
    }

    public static Vector2f right() {
        return new Vector2f(1, 0);
    }
}
