package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Texture;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class TexturedShader extends Shader {
    private Texture texture;

    public TexturedShader(Texture texture) throws Exception {
        super("textured");
        this.texture = texture;
        createUniform("texture_sampler");
    }

    @Override
    public void render(Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        super.render(projectionMatrix, modelViewMatrix, this::render);
    }

    @Override
    public Map<VboType, Integer> prepareVertexBufferObjects() {
        // prepare texture and coordinates
        Map<VboType, Integer> vbos = new HashMap<>();

        FloatBuffer textureCoordinatesBuffer = null;
        float[] textureCoords = new float[]{
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        try{
            int texCoordsVboId = glGenBuffers();
            vbos.put(VboType.TEXTURE_COORDS, texCoordsVboId);
            textureCoordinatesBuffer = MemoryUtil.memAllocFloat(textureCoords.length);
            textureCoordinatesBuffer.put(textureCoords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, texCoordsVboId);
            glBufferData(GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        } finally {
            if (textureCoordinatesBuffer != null) {
                MemoryUtil.memFree(textureCoordinatesBuffer);
            }
        }

        return vbos;
    }

    @Override
    public void preRender(int vboLastIndex) {
        bind();
        glEnableVertexAttribArray(vboLastIndex);
    }

    private void render() {
        setUniform("texture_sampler", 0);
        glActiveTexture(GL_TEXTURE0);

        int textureId = texture.getId();
        glBindTexture(GL_TEXTURE_2D, textureId);

    }

    @Override
    public void postRender(int vboLastIndex) {
        glDisableVertexAttribArray(vboLastIndex);
        glBindTexture(GL_TEXTURE_2D, 0);
        unbind();
    }
}
