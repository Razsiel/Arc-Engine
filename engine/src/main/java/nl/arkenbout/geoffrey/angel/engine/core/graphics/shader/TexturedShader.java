package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Texture;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector2u;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.util.Collections;
import java.util.Map;

import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

public class TexturedShader extends Shader {
    private final Vector2f repeat;
    private final Texture texture;

    public TexturedShader(Texture texture) throws Exception {
        this(texture, Vector2u.one());
    }

    public TexturedShader(Texture texture, Vector2f repeat) throws Exception {
        super("textured");
        this.texture = texture;
        this.repeat = repeat;
        createUniform("texture_sampler");
        createUniform("texture_repeat");
    }

    @Override
    public Map<VboType, Integer> prepareVertexBufferObjects(int vboIdIndex) {
        return Collections.emptyMap();
    }

    @Override
    public void preRender(int vboLastIndex) {
        if (repeat.x() > 0f) {
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        }
        if (repeat.y() > 0f) {
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        }
        bind();
        glEnableVertexAttribArray(vboLastIndex);
    }

    @Override
    public void render(Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        super.render(projectionMatrix, modelViewMatrix, this::render);
    }

    private void render() {
        setUniform("texture_sampler", 0);
        setUniform("texture_repeat", this.repeat);
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
