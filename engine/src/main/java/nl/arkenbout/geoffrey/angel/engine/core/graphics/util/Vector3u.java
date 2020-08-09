package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import org.joml.Vector3d;

import java.util.stream.DoubleStream;

public class Vector3u {
    public static Vector3d one() {
        return new Vector3d(1);
    }

    public static Vector3d zero() {
        return new Vector3d(0);
    }

    public static Vector3d up() {
        return new Vector3d(0, 1, 0);
    }

    public static Vector3d down() {
        return new Vector3d(0, -1, 0);
    }

    public static Vector3d left() {
        return new Vector3d(-1, 0, 0);
    }

    public static Vector3d right() {
        return new Vector3d(1, 0, 0);
    }

    public static Vector3d back() {
        return new Vector3d(0, 0, -1);
    }

    public static Vector3d forward() {
        return new Vector3d(0, 0, 1);
    }

    public static DoubleStream componentsStream(Vector3d input) {
        return DoubleStream.of(input.x(), input.y(), input.z());
    }
}
