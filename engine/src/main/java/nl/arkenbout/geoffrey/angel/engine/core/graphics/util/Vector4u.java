package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vector4u {
    public static Vector3f xyz(Vector4f v) {
        return new Vector3f(v.x(), v.y(), v.z());
    }
}
