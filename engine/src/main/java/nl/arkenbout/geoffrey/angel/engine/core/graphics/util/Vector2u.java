package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import org.joml.Vector2d;

public class Vector2u {
    public static Vector2d one() {
        return new Vector2d(1);
    }

    public static Vector2d zero() {
        return new Vector2d(0);
    }

    public static Vector2d up() {
        return new Vector2d(0, 1);
    }

    public static Vector2d down() {
        return new Vector2d(0, -1);
    }

    public static Vector2d left() {
        return new Vector2d(-1, 0);
    }

    public static Vector2d right() {
        return new Vector2d(1, 0);
    }
}
