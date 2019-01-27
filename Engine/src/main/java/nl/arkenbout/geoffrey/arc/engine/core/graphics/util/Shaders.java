package nl.arkenbout.geoffrey.arc.engine.core.graphics.util;

import nl.arkenbout.geoffrey.arc.engine.core.graphics.Shader;
import nl.arkenbout.geoffrey.arc.engine.util.Utils;

public class Shaders {


    private static Shader defaultShader;
    public static Shader defaultShader() {
        if (defaultShader == null) {
            try {
                defaultShader = new Shader(Utils.loadResource("/vertex.vs"), Utils.loadResource("/fragment.fs"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultShader;
    }
}
