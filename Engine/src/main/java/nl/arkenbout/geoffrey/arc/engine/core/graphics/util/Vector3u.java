package nl.arkenbout.geoffrey.arc.engine.core.graphics.util;

import org.joml.Vector3f;

public class Vector3u {
    public static Vector3f one() {
        return new Vector3f(1, 1, 1);
    }

    public static Vector3f zero() {
        return new Vector3f(0, 0, 0);
    }

    public static Vector3f up() {
        return new Vector3f(0, 1, 0);
    }

    public static Vector3f down() {
        return new Vector3f(0, -1, 0);
    }

    public static Vector3f left() {
        return new Vector3f(-1, 0, 0);
    }

    public static Vector3f right() {
        return new Vector3f(1, 0, 0);
    }

    public static Vector3f back() {
        return new Vector3f(0, 0, -1);
    }

    public static Vector3f forward() {
        return new Vector3f(0, 0, 1);
    }
}
