package nl.arkenbout.geoffrey.angel.engine.core.graphics.util;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Mesh;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.mesh.Vertex;

import java.util.stream.Stream;

public class PrimitiveMesh {
    public static Mesh createCube(double size) {
        var vertices = new double[]{
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
                -1, 1, -1,


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
        var normals = new double[]{
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,

                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,

                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,

                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,

                0, -1, 0,
                0, -1, 0,
                0, -1, 0,
                0, -1, 0,

                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1
        };
        var texCoords = new double[]{
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
        return new Mesh(vertices, indices, normals, texCoords);
    }

    public static Mesh createPlane(double width, double height) {
        width /= 2d;
        height /= 2d;
        var vertices = new double[]{
                -width, 0, -height,
                width, 0, -height,
                width, 0, height,
                -width, 0, height
        };
        var indices = new int[]{
                0, 1, 3,
                3, 1, 2
        };
        var normals = new double[]{
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0
        };
        var texCoords = new double[]{
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
        return new Mesh(vertices, indices, normals, texCoords);
    }

    public static Mesh plane(int width, int height) {
        var verticesArray = new Vertex[(width + 1) * (height + 1)];
        for (int i = 0, z = 0; i <= height; z++) {
            for (int x = 0; x < width; x++, i++) {
                verticesArray[i] = Vertex.of(x, 0, z);
            }
        }

        var vertices = Stream.of(verticesArray)
                .flatMapToDouble(Vertex::getPositionElements)
                .toArray();

        var triangles = new int[width * height * 6];
        for (int triangleIndex = 0, vertexIndex = 0, y = 0; y < height; y++, vertexIndex++) {
            for (int x = 0; x < width; x++, triangleIndex += 6, vertexIndex++) {
                triangles[triangleIndex] = vertexIndex;
                triangles[triangleIndex + 3] = triangles[triangleIndex + 2] = vertexIndex + 1;
                triangles[triangleIndex + 4] = triangles[triangleIndex + 1] = vertexIndex + width + 1;
                triangles[triangleIndex + 5] = vertexIndex + width + 2;
            }
        }

        var normals = new double[vertices.length];
        for (int i = 0; i < normals.length; i += 3) {
            normals[i] = 0;
            normals[i + 1] = 1;
            normals[i + 2] = 0;
        }

        return new Mesh(vertices, triangles, normals, new double[]{});
    }
}
