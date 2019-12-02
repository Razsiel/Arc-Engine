package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private final int[] indices;
    private final float[] colors;
    private final int vaoId;
    private final int positionVboId;
    private final int indexVboId;
    private final int colorVboId;
    private float[] vertices;

    public Mesh(float[] vertices, int[] indices, float[] colors) {
        this.indices = indices;
        this.vertices = vertices;
        this.colors = colors;

        FloatBuffer positionBuffer = null;
        IntBuffer indicesBuffer = null;
        FloatBuffer colorBuffer = null;
        try {
            //create VAO
            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            //Position VBO
            positionVboId = glGenBuffers();
            positionBuffer = MemoryUtil.memAllocFloat(vertices.length);
            positionBuffer.put(vertices).flip();
            glBindBuffer(GL_ARRAY_BUFFER, positionVboId);
            glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            //VColour VBO
            colorVboId = glGenBuffers();
            colorBuffer = MemoryUtil.memAllocFloat(colors.length);
            colorBuffer.put(colors).flip();
            glBindBuffer(GL_ARRAY_BUFFER, colorVboId);
            glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

            //Index VBO
            indexVboId = glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            //Clear
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (positionBuffer != null)
                MemoryUtil.memFree(positionBuffer);
            if (indicesBuffer != null)
                MemoryUtil.memFree(indicesBuffer);
            if (colorBuffer != null)
                MemoryUtil.memFree(colorBuffer);
        }
    }

    public float[] getVertices() {
        return vertices;
    }

    public int getVertexCount() {
        return indices.length;
    }

    public void cleanup() {
        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(positionVboId);
        glDeleteBuffers(indexVboId);
        glDeleteBuffers(colorVboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public int getVaoId() {
        return vaoId;
    }
}
