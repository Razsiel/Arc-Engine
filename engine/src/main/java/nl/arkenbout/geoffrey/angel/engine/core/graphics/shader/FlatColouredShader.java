package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting.DirectionalLight;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Lights;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector4u;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.util.ReadableColor;

import java.util.Collections;
import java.util.Map;

public class FlatColouredShader extends Shader {
    private final ReadableColor color;

    public FlatColouredShader(ReadableColor color) throws Exception {
        super("flat");
        this.color = color;
        createUniform("projectionMatrix");
        createUniform("modelViewMatrix");
        createUniform("color");
    }

    public ReadableColor getColor() {
        return color;
    }

    @Override
    public void render(Matrix4f projectionMatrix, Matrix4f modelViewMatrix, Matrix4f viewMatrix) {
        super.render(projectionMatrix, modelViewMatrix, this::render);
    }

    @Override
    public Map<VboType, Integer> prepareVertexBufferObjects(int vboIdIndex) {
        return Collections.emptyMap();
    }

    @Override
    public void preRender(int vboLastIndex) {
        bind();
    }

    private void render() {
        // set uniforms
        setUniform("color", color);
    }

    @Override
    public void postRender(int vboLastIndex) {
        // disable buffers
        unbind();
    }
}

