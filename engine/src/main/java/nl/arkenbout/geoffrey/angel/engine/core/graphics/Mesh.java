package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.mesh.Triangle;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.mesh.Vertex;
import nl.arkenbout.geoffrey.angel.engine.util.Cleanup;
import org.joml.Matrix4d;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh implements Cleanup {
    private final double[] vertices;
    private final double[] normals;
    private final double[] textureCoords;
    private final int[] indices;

    private int vaoId;
    private Map<VboType, Integer> vboIds = new HashMap<>();
    private int vertexAttributesSize = 0;

    public Mesh(double[] vertices, int[] indices, double[] normals, double[] textureCoords) {
        this.indices = indices;
        this.vertices = vertices;
        this.normals = normals;
        this.textureCoords = textureCoords;
    }

    public Mesh(List<Vertex> vertices, List<Triangle> triangleIndices) {
        this.vertices = vertices.stream()
                .flatMapToDouble(Vertex::getPositionElements)
                .toArray();

        this.indices = triangleIndices.stream()
                .flatMapToInt(Triangle::getIndicesAsStream)
                .toArray();

        this.normals = this.vertices;


        this.textureCoords = new double[vertices.size()];
        for (int i = 0; i < vertices.size(); i += 3) {
            textureCoords[i] = 0f;
            textureCoords[i + 1] = 0f;
            textureCoords[i + 2] = 0f;
        }
    }

    public void prepare(Material material) {
        DoubleBuffer positionBuffer = null;
        IntBuffer indicesBuffer = null;
        DoubleBuffer texCoordsBuffer = null;
        DoubleBuffer normalsBuffer = null;

        try {
            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            //VERTEX
            int positionVboId = glGenBuffers();
            positionBuffer = MemoryUtil.memAllocDouble(vertices.length);
            positionBuffer.put(vertices).flip();
            glBindBuffer(GL_ARRAY_BUFFER, positionVboId);
            glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_DOUBLE, false, 0, 0);
            vboIds.put(VERTEX, positionVboId);
            vertexAttributesSize = 0;

            //INDEX
            int indexVboId = glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
            vboIds.put(INDEX, indexVboId);

            //TEXTURE COORDINATES
            int texCoordsVboId = glGenBuffers();
            texCoordsBuffer = MemoryUtil.memAllocDouble(textureCoords.length);
            texCoordsBuffer.put(textureCoords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, texCoordsVboId);
            glBufferData(GL_ARRAY_BUFFER, texCoordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_DOUBLE, false, 0, 0);
            vboIds.put(TEXTURE_COORDS, texCoordsVboId);
            vertexAttributesSize += 1;

            //NORMALS
            int normalsVboId = glGenBuffers();
            normalsBuffer = MemoryUtil.memAllocDouble(normals.length);
            normalsBuffer.put(normals).flip();
            glBindBuffer(GL_ARRAY_BUFFER, normalsVboId);
            glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(2, 3, GL_DOUBLE, false, 0, 0);
            vboIds.put(NORMAL, normalsVboId);
            vertexAttributesSize += 1;

            //MATERIAL SPECIFIC BUFFERS
            Map<VboType, Integer> materialVboIds = material.prepare(normalsVboId);
            vboIds.putAll(materialVboIds);
            this.vertexAttributesSize += materialVboIds.values().size();
        } finally {
            if (positionBuffer != null) {
                MemoryUtil.memFree(positionBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
            if (texCoordsBuffer != null) {
                MemoryUtil.memFree(texCoordsBuffer);
            }
            if (normalsBuffer != null) {
                MemoryUtil.memFree(normalsBuffer);
            }
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void cleanup() {
        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        vboIds.values().forEach(GL15::glDeleteBuffers);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public void render(Material material, Matrix4d projectionMatrix, Matrix4d modelViewMatrix, Matrix4d viewMatrix) {
        glBindVertexArray(vaoId);

        for (int i = 0; i < vertexAttributesSize; i++) {
            // if indices, skip
            glEnableVertexAttribArray(i);
        }
        material.preRender(vertexAttributesSize);

        material.render(projectionMatrix, modelViewMatrix, viewMatrix);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        for (int i = 0; i < vertexAttributesSize; i++) {
            // if indices, skip
            glDisableVertexAttribArray(i);
        }
        material.postRender(vertexAttributesSize);

        glBindVertexArray(0);
    }

    public int getVertexCount() {
        return vertices.length;
    }
}