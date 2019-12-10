package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import org.joml.*;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import java.util.Collections;
import java.util.Map;

public class FlatColouredShader extends Shader {
    private final ReadableColor color;

    public FlatColouredShader(ReadableColor color) throws Exception {
        super("flat");
        this.color = color;
        createUniform("color");
        setColor(color);
    }

    public Color getColor() {
        return super.getProperty("color");
    }

    public void setColor(ReadableColor color) {
        this.setProperty("color", color);
    }

    @Override
    public void render(Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
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
        setUniform("color", getColor());
    }

    @Override
    public void postRender(int vboLastIndex) {
        // disable buffers
        unbind();
    }
}

