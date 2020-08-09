package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import org.joml.Matrix4d;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class VertexColouredShader extends Shader {
    public VertexColouredShader() throws Exception {
        super("vertex-coloured");
        createUniform("projectionMatrix");
        createUniform("modelViewMatrix");
    }

    @Override
    public Map<VboType, Integer> prepareVertexBufferObjects(int vboIdIndex) {
        Map<VboType, Integer> vbos = new HashMap<>();

        FloatBuffer colorBuffer = null;
        float[] colors = new float[]{};

        try {
            int colorVboId = glGenBuffers();
            vbos.put(VboType.VERTEX_COLOR, colorVboId);
            colorBuffer = MemoryUtil.memAllocFloat(colors.length);
            colorBuffer.put(colors).flip();
            glBindBuffer(GL_ARRAY_BUFFER, colorVboId);
            glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(vboIdIndex, 3, GL_FLOAT, false, 0, 0);
        } finally {
            if (colorBuffer != null) {
                MemoryUtil.memFree(colorBuffer);
            }
        }

        return vbos;
    }

    @Override
    public void preRender(int vboLastIndex) {
        glEnableVertexAttribArray(vboLastIndex);
        bind();
    }

    @Override
    public void render(Matrix4d projectionMatrix, Matrix4d modelViewMatrix, Matrix4d viewMatrix) {
        super.render(projectionMatrix, modelViewMatrix, () -> {
        });
    }

    @Override
    public void postRender(int vboLastIndex) {
        glDisableVertexAttribArray(vboLastIndex);
    }
}
