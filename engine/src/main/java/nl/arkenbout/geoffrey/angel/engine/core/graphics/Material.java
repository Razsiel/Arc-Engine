package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.shader.Shader;
import org.joml.Matrix4f;

import java.util.Map;

public final class Material {
    private final Shader shader;

    public Material(Shader shader) {
        this.shader = shader;
    }

    public void render(Matrix4f projectionMatrix, Matrix4f modelViewProjection) {
        shader.render(projectionMatrix, modelViewProjection);
    }

    public void cleanup() {
        shader.cleanup();
    }

    public Map<VboType, Integer> prepare() {
        return shader.prepareVertexBufferObjects();
    }

    public void preRender(int vboLastIndex) {
        shader.preRender(vboLastIndex);
    }

    public void postRender(int vboLastIndex) {
        shader.postRender(vboLastIndex);
    }
}
