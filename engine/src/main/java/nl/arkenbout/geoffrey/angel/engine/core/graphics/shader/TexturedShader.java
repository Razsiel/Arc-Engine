package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Texture;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import org.joml.Matrix4f;

import java.util.Collections;
import java.util.Map;

import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

public class TexturedShader extends Shader {
    private Texture texture;

    public TexturedShader(Texture texture) throws Exception {
        super("textured");
        this.texture = texture;
        createUniform("texture_sampler");
    }

    @Override
    public Map<VboType, Integer> prepareVertexBufferObjects(int vboIdIndex) {
        return Collections.emptyMap();
    }

    @Override
    public void preRender(int vboLastIndex) {
        bind();
        glEnableVertexAttribArray(vboLastIndex);
    }

    @Override
    public void render(Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        super.render(projectionMatrix, modelViewMatrix, this::render);
    }

    private void render() {
        setUniform("texture_sampler", 0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getId());
    }

    @Override
    public void postRender(int vboLastIndex) {
        glDisableVertexAttribArray(vboLastIndex);
        glBindTexture(GL_TEXTURE_2D, 0);
        unbind();
    }
}
