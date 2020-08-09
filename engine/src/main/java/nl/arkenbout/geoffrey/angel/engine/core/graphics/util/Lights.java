package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting.DirectionalLight;
import org.joml.Vector3d;
import org.lwjgl.util.Color;

public class Lights {
    private static DirectionalLight directionalLight = new DirectionalLight(Color.CYAN, new Vector3d(-1d, 1d, -1d), 0.3d);

    public static DirectionalLight getDirectionalLight() {
        return directionalLight;
    }
}
