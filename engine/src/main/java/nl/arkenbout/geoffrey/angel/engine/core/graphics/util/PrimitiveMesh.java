package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Mesh;

public class PrimitiveMesh {
    public static Mesh createCube(float size) {
        size /= 2f;
        var vertices = new float[]{
                // FRONT FACE
                // F0VO
                -size, size, size,
                // F0V1
                -size, -size, size,
                // F0V2
                size, -size, size,
                // F0V3
                size, size, size,


                // LEFT FACE
                // F1V0
                -size, size, -size,
                // F1V1
                -size, -size, -size,
                // F1V2
                -size, -size, size,
                // F1V3
                -size, size, size,


                // RIGHT FACE
                // F2V0
                size, size, size,
                // F2V1
                size, -size, size,
                // F2V2
                size, -size, -size,
                // F2V3
                size, size, -size,


                // TOP FACE
                // F3V0
                -size, size, -size,
                // F3V1
                -size, size, size,
                // F3V2
                size, size, size,
                // F3V3
                size, size, -size,


                // BOTTOM FACE
                // F4V0
                size, -size, size,
                // F4V1
                size, -size, -size,
                // F4V2
                -size, -size, -size,
                // F4V3
                -size, -size, size,


                // BACK FACE
                // F6V0
                -size, size, -size,
                // F6V1
                -size, -size, -size,
                // F6V2
                size, -size, -size,
                // F6V3
                size, size, -size,
        };
        var indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Left face
                4, 5, 7, 7, 5, 6,
                // Right face
                8, 9, 11, 11, 9, 10,
                // Top Face
                12, 13, 15, 15, 13, 14,
                // Bottom face
                16, 17, 19, 19, 17, 18,
                // Back face
                20, 21, 23, 23, 21, 22
        };
        var texCoords = new float[]{
                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f,

                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f,

                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f,

                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f,

                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f,

                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f
        };
        return new Mesh(vertices, indices, texCoords);
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
        var texCoords = new float[] {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
        return new Mesh(vertices, indices, texCoords);
    }
}
