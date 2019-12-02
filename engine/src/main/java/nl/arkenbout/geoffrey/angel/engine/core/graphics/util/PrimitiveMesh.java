package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Mesh;

public class PrimitiveMesh {
    public static Mesh createCube(float size) {
        size /= 2f;
        var vertices = new float[]{
                // VO
                -size, size, size,
                // V1
                -size, -size, size,
                // V2
                size, -size, size,
                // V3
                size, size, size,
                // V4
                -size, size, -size,
                // V5
                size, size, -size,
                // V6
                -size, -size, -size,
                // V7
                size, -size, -size,
        };
        var indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,
        };
        var colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        return new Mesh(vertices, indices, colours);
    }

    public static Mesh createPlane(float width, float depth) {
        width /= 2;
        depth /= 2;
        var vertices = new float[]{
                -width, 0, -depth,
                width, 0, -depth,
                width, 0, depth,
                -width, 0, depth
        };
        var indices = new int[]{
                0, 1, 3,
                3, 1, 2
        };
        var colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.5f, 0.5f, 0.5f
        };
        return new Mesh(vertices, indices, colours);
    }
}
