package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Texture;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting.DirectionalLight;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.*;
import org.joml.*;

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
        createUniform("projectionMatrix");
        createUniform("modelViewMatrix");
        createUniform("texture_sampler");
        createUniform("texture_repeat");
        createDirectionalLightUniform("directional_light");
        createUniform("shadow");
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
        super.render(projectionMatrix, modelViewMatrix,  () -> this.render(viewMatrix));
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
