package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Mesh;

public class PrimitiveMesh {
    public static Mesh createCube(float size) {
        var vertices = new float[]{
                // FRONT FACE
                // F0VO
                -1, 1, -1,
                // F0V1
                -1, -1, -1,
                // F0V2
                1, -1, -1,
                // F0V3
                1, 1, -1,

                // LEFT FACE
                // F1V0
                -1, 1, 1,
                // F1V1
                -1, -1, 1,
                // F1V2
                -1, -1, -1,
                // F1V3
                -1, 1, -1,

                // RIGHT FACE
                // F2V0
                1, 1, -1,
                // F2V1
                1, -1, -1,
                // F2V2
                1, -1, 1,
                // F2V3
                1, 1, 1,

                // TOP FACE
                // F3V0
                1, 1, -1,
                // F3V1
                1, 1, 1,
                // F3V2
                -1, 1, 1,
                // F3V3
                -1 ,1, -1,


                // BOTTOM FACE
                // F4V0
                -1, -1, -1,
                // F4V1
                -1, -1, 1,
                // F4V2
                1, -1, 1,
                // F4V3
                1, -1, -1,

                // BACK FACE
                // F5VO
                1, 1, 1,
                // F5V1
                1, -1, 1,
                // F5V2
                -1, -1, 1,
                // F5V3
                -1, 1, 1,
        };
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = (vertices[i] / 2) * size;
        }
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
                // Front face
                0, 0,
                1, 0,
                1, 1,
                0, 1,
                // Left face
                0, 0,
                1, 0,
                1, 1,
                0, 1,
                // Right face
                0, 0,
                1, 0,
                1, 1,
                0, 1,
                // Top Face
                0, 0,
                1, 0,
                1, 1,
                0, 1,
                // Bottom face
                0, 0,
                1, 0,
                1, 1,
                0, 1,
                // Back face
                0, 0,
                1, 0,
                1, 1,
                0, 1,
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
