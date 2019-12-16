package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting.DirectionalLight;
import org.joml.Vector3f;
import org.lwjgl.util.Color;

public class Lights {
    private static DirectionalLight directionalLight = new DirectionalLight(Color.CYAN, new Vector3f(-1f, 1f, -1f), 0.3f);

    public static DirectionalLight getDirectionalLight() {
        return directionalLight;
    }
}
