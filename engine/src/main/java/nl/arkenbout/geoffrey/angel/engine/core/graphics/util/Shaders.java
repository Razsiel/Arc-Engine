package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Shader;
import nl.arkenbout.geoffrey.angel.engine.util.Utils;

public class Shaders {
    private static Shader vertexColouredShader;
    private static Shader texturedShader;

    public static Shader loadShader(String vertexResource, String fragmentResource) throws Exception {
        return new Shader(
                Utils.loadResource(vertexResource),
                Utils.loadResource(fragmentResource)
        );
    }

    private static Shader loadDefaultShader(String name) throws Exception {
        return loadShader(String.format("/shaders/%s/vertex.vs", name), String.format("/shaders/%s/fragment.fs", name));
    }

    private static Shader lazyLoadShader(Shader reference, String name) throws Exception {
        if (reference == null) {
            reference = loadDefaultShader(name);
        }
        return reference;
    }

    public static Shader getVertexColouredShader() throws Exception {
        return lazyLoadShader(vertexColouredShader, "vertex-coloured");
    }

    public static Shader getTexturedShader() throws Exception {
        return lazyLoadShader(texturedShader, "textured");
    }
}
