package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.shader.Shader;
import nl.arkenbout.geoffrey.angel.engine.util.Cleanup;
import org.joml.Matrix4d;

import java.util.Map;

public final class Material implements Cleanup {
    private final Shader shader;

    public Material(Shader shader) {
        this.shader = shader;
    }

    public void render(Matrix4d projectionMatrix, Matrix4d modelViewProjection, Matrix4d viewMatrix) {
        shader.render(projectionMatrix, modelViewProjection, viewMatrix);
    }

    public Map<VboType, Integer> prepare(int vboIdIndex) {
        return shader.prepareVertexBufferObjects(vboIdIndex);
    }

    public void preRender(int vboLastIndex) {
        shader.preRender(vboLastIndex);
    }

    public void postRender(int vboLastIndex) {
        shader.postRender(vboLastIndex);
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }
}
