package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import org.joml.Matrix4d;

import java.util.Map;

import static org.lwjgl.opengl.GL11.glDepthRange;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class SkyBoxShader extends Shader {

    public SkyBoxShader() throws Exception {
        super("skybox");
        createUniform("texture_sampler");
    }

    @Override
    public Map<VboType, Integer> prepareVertexBufferObjects(int vboIdIndex) {
        return null;
    }

    @Override
    public void preRender(int vboLastIndex) {
        glDepthRange(1, 1);
        bind();
        glEnableVertexAttribArray(vboLastIndex);
    }

    @Override
    public void render(Matrix4d projectionMatrix, Matrix4d modelViewMatrix, Matrix4d viewMatrix) {
        super.render(projectionMatrix, modelViewMatrix, () -> {

        });
    }

    @Override
    public void postRender(int vboLastIndex) {
        glDepthRange(0, 1);
        glDisableVertexAttribArray(vboLastIndex);
    }
}
