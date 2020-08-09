package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Texture;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting.DirectionalLight;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Lights;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector2u;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector4u;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.util.ReadableColor;

import java.util.Collections;
import java.util.Map;

import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class TexturedShader extends Shader {
    private final Vector2f repeat;
    private final ReadableColor color;
    private final Texture texture;

    public TexturedShader(Texture texture, ReadableColor color) throws Exception {
        this(texture, Vector2u.one(), color);
    }

    public TexturedShader(Texture texture, Vector2f repeat, ReadableColor color) throws Exception {
        super("textured");
        this.texture = texture;
        this.repeat = repeat;
        this.color = color;
        createUniform("projectionMatrix");
        createUniform("modelViewMatrix");
        createUniform("texture_sampler");
        createUniform("texture_repeat");
        createDirectionalLightUniform("directional_light");
        createUniform("shadow");
        createUniform("color");
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
    public void render(Matrix4f projectionMatrix, Matrix4f modelViewMatrix, Matrix4f viewMatrix) {
        super.render(projectionMatrix, modelViewMatrix, () -> this.render(viewMatrix));
    }

    private void render(Matrix4f viewMatrix) {
        setUniform("texture_sampler", 0);
        setUniform("texture_repeat", this.repeat);
        setUniform("shadow", 1);
        DirectionalLight directionalLight = new DirectionalLight(Lights.getDirectionalLight());
        Vector4f viewSpaceDirection = new Vector4f(directionalLight.getDirection(), 0f);
        viewSpaceDirection.mul(viewMatrix);
        directionalLight.setDirection(Vector4u.xyz(viewSpaceDirection));
        setUniform("directional_light", directionalLight);
        setUniform("color", color);
        if (texture != null) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture.getId());
        }
    }

    @Override
    public void postRender(int vboLastIndex) {
        glDisableVertexAttribArray(vboLastIndex);
        glBindTexture(GL_TEXTURE_2D, 0);
        unbind();
    }
}
