package nl.arkenbout.geoffrey.angel.engine.core.graphics.mesh;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Material;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private final float[] vertices;
    private final int[] indices;

    private int vaoId;
    private Map<VboType, Integer> vboIds = new HashMap<>();

    public Mesh(float[] vertices, int[] indices) {
        this.indices = indices;
        this.vertices = vertices;
    }

    public void prepare(Material material) {
        FloatBuffer positionBuffer = null;
        IntBuffer indicesBuffer = null;

        try {
            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            //VERTEX
            int positionVbo = glGenBuffers();
            positionBuffer = MemoryUtil.memAllocFloat(vertices.length);
            positionBuffer.put(vertices).flip();
            glBindBuffer(GL_ARRAY_BUFFER, positionVbo);
            glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            vboIds.put(VERTEX, positionVbo);

            //INDEX
            int indexVboId = glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
            vboIds.put(INDEX, indexVboId);

            //MATERIAL SPECIFIC BUFFERS
            Map<VboType, Integer> materialVboIds = material.prepare();
            vboIds.putAll(materialVboIds);
        } finally {
            if (positionBuffer != null) {
                MemoryUtil.memFree(positionBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void cleanup() {
        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        vboIds.values().forEach(GL15::glDeleteBuffers);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public void render(Material material, Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        glBindVertexArray(vaoId);

        var vboIdSize = (int) this.vboIds.keySet()
                .stream()
                .filter(vboType -> vboType != INDEX)
                .count();

        for (int i = 0; i < vboIdSize; i++) {
            // if indices, skip
            glEnableVertexAttribArray(i);
        }
        material.preRender(vboIdSize);

        material.render(projectionMatrix, modelViewMatrix);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        for (int i = 0; i < vboIdSize; i++) {
            // if indices, skip
            glDisableVertexAttribArray(i);
        }
        material.postRender(vboIdSize);

        glBindVertexArray(0);
    }

    public int getVertexCount() {
        return vertices.length;
    }
}