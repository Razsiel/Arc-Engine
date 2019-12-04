package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import nl.arkenbout.geoffrey.angel.engine.util.FloatArrayCollector;
import org.javatuples.Triplet;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private final int[] indices;
    private final int vaoId;
    private final List<Integer> vboIds;
    private final Consumer<Shader> renderFunction;
    private final Optional<Texture> texture;
    private float[] vertices;

    private Mesh(float[] vertices, int[] indices, int vaoId, List<Integer> vboIds, Consumer<Shader> renderFunction, Texture texture) {
        this.indices = indices;
        this.vertices = vertices;
        this.vaoId = vaoId;
        this.vboIds = vboIds;
        this.renderFunction = renderFunction;
        this.texture = Optional.ofNullable(texture);
    }

    public static MeshBuilder builder(List<Vector3f> vertices, List<Triplet<Integer, Integer, Integer>> indices) {
        float[] verticesArray = vertices.stream()
                .flatMapToDouble(v -> DoubleStream.of(v.x, v.y, v.z))
                .collect(FloatArrayCollector::new, FloatArrayCollector::add, FloatArrayCollector::join)
                .toArray();

        int[] indicesArray = indices.stream()
                .flatMapToInt(index -> IntStream.of(index.getValue0(), index.getValue1(), index.getValue2()))
                .toArray();

        return builder(verticesArray, indicesArray);
    }

    public static MeshBuilder builder(float[] vertices, int[] indices) {
        return new MeshBuilder(vertices, indices);
    }

    public boolean hasTexture() {
        return texture.isPresent();
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
        vboIds.forEach(GL15::glDeleteBuffers);

        texture.ifPresent(Texture::cleanup);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public int getVaoId() {
        return vaoId;
    }

    public void render(Shader shader) {
        renderFunction.accept(shader);
    }

    public static class MeshBuilder {

        private final int vaoId;

        private final float[] vertices;
        private final int[] indices;
        private List<Integer> vboIds;

        private int vboCurrentIndex = 0;
        private Texture texture;
        private Consumer<Shader> renderFunction;

        private MeshBuilder(float[] vertices, int[] indices) {
            this.vertices = vertices;
            this.indices = indices;
            this.vboIds = new ArrayList<>();

            FloatBuffer positionBuffer = null;
            IntBuffer indicesBuffer = null;

            try {
                // Create Vertex Array Object (VAO)
                vaoId = glGenVertexArrays();
                glBindVertexArray(vaoId);

                // Position VBO
                positionBuffer = getVertexPositionBuffer(vertices, vboCurrentIndex);

                // Index VBO
                indicesBuffer = getVertexIndicesBuffer(indices);
            } finally {
                if (positionBuffer != null)
                    MemoryUtil.memFree(positionBuffer);
                if (indicesBuffer != null)
                    MemoryUtil.memFree(indicesBuffer);
            }
        }

        public MeshFinalizer textured(float[] texCoordinates, Texture texture) {
            this.texture = texture;

            FloatBuffer texCoordsBuffer = null;
            try {
                texCoordsBuffer = getTexCoordsBuffer(texCoordinates, vboCurrentIndex);
            } finally {
                if (texCoordsBuffer != null)
                    MemoryUtil.memFree(texCoordsBuffer);
            }

            this.renderFunction = (shader) -> {
                int textureId = texture.getId();

                // set the 'texture_sampler' to use texture index 0
                shader.setUniform("texture_sampler", 0);

                // Activate first texture bank
                glActiveTexture(GL_TEXTURE0);
                // Bind the texture to the texture bank
                glBindTexture(GL_TEXTURE_2D, textureId);

                // Draw the mesh
                drawMesh();
                cleanupMesh();
            };

            return new MeshFinalizer();
        }

        private void drawMesh() {
            glBindVertexArray(vaoId);
            for (int i = 0; i < vboIds.size() - 1; i++) {
                glEnableVertexAttribArray(i);
            }

            glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        }

        public MeshFinalizer vertexShaded(float[] colors) {
            // Vertex color VBO
            FloatBuffer colorBuffer = null;
            try {
                colorBuffer = getColorBuffer(colors, vboCurrentIndex);
            } finally {
                if (colorBuffer != null)
                    MemoryUtil.memFree(colorBuffer);
            }

            this.renderFunction = (shader) -> {
                drawMesh();
                cleanupMesh();
            };

            return new MeshFinalizer();
        }

        private void cleanupMesh() {
            for (int i = 0; i < vboIds.size() - 1; i++) {
                glDisableVertexAttribArray(i);
            }
            glBindVertexArray(0);
        }

        private IntBuffer getVertexIndicesBuffer(int[] indices) {
            int indexVboId = glGenBuffers();
            vboIds.add(indexVboId);
            IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
            return indicesBuffer;
        }

        private FloatBuffer getVertexPositionBuffer(float[] vertices, int vboCurrentIndex) {
            return getVertexBuffer(vertices, vboCurrentIndex);
        }

        private FloatBuffer getColorBuffer(float[] colors, int vboCurrentIndex) {
            return getVertexBuffer(colors, vboCurrentIndex);
        }

        private FloatBuffer getVertexBuffer(float[] data, int vboCurrentIndex) {
            int vbo = glGenBuffers();
            vboIds.add(vbo);
            FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(data.length);
            vertexBuffer.put(data).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(vboCurrentIndex, 3, GL_FLOAT, false, 0, 0);
            this.vboCurrentIndex++;
            return vertexBuffer;
        }

        private FloatBuffer getTexCoordsBuffer(float[] texCoordinates, int vboCurrentIndex) {
            int texCoordsVboId = glGenBuffers();
            vboIds.add(texCoordsVboId);
            FloatBuffer texCoordsBuffer = MemoryUtil.memAllocFloat(texCoordinates.length);
            texCoordsBuffer.put(texCoordinates).flip();
            glBindBuffer(GL_ARRAY_BUFFER, texCoordsVboId);
            glBufferData(GL_ARRAY_BUFFER, texCoordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(vboCurrentIndex, 2, GL_FLOAT, false, 0, 0);
            return texCoordsBuffer;
        }

        public class MeshFinalizer {
            private MeshFinalizer() {

            }

            public Mesh build() {
                glBindBuffer(GL_ARRAY_BUFFER, 0);
                glBindVertexArray(0);

                return new Mesh(vertices, indices, vaoId, List.copyOf(vboIds), renderFunction, texture);
            }
        }
    }
}