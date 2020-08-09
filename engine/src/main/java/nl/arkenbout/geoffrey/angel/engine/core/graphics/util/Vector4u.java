package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import org.joml.Vector3d;
import org.joml.Vector4d;

public class Vector4u {
    public static Vector3d xyz(Vector4d v) {
        return new Vector3d(v.x(), v.y(), v.z());
    }
}
