package nl.arkenbout.geoffrey.angel.engine.core.graphics.mesh;

import java.util.List;
import java.util.stream.IntStream;

public class Triangle {
    private final int index0;
    private final int index1;
    private final int index2;

    public Triangle(int index0, int index1, int index2) {
        this.index0 = index0;
        this.index1 = index1;
        this.index2 = index2;
    }

    private Triangle(List<Vertex> vertices, Vertex v0, Vertex v1, Vertex v2) {
        this.index0 = vertices.indexOf(v0);
        this.index1 = vertices.indexOf(v1);
        this.index2 = vertices.indexOf(v2);
    }

    public static IntStream getIndicesAsStream(Triangle triangle) {
        return IntStream.of(triangle.index0, triangle.index1, triangle.index2);
    }

    public static Triangle of(List<Vertex> vertices, Vertex v0, Vertex v1, Vertex v2) {
        return new Triangle(vertices, v0, v1, v2);
    }

    public int getIndex0() {
        return index0;
    }

    public int getIndex1() {
        return index1;
    }

    public int getIndex2() {
        return index2;
    }
}
