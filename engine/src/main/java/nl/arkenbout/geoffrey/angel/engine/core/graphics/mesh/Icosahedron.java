package nl.arkenbout.geoffrey.angel.engine.core.graphics.mesh;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Mesh;

import java.util.Arrays;

public class Icosahedron {
    @SuppressWarnings("SuspiciousNameCombination")
    public static Mesh create(int subdivision) {
        var x = (float) Math.sqrt((5.0 - Math.sqrt(5.0)) / 10.0);
        var z = (float) Math.sqrt((5.0 + Math.sqrt(5.0)) / 10.0);
        var n = 0f;

        var v0 = Vertex.of(-x, n, z);
        var v1 = Vertex.of(x, n, z);
        var v2 = Vertex.of(-x, n, -z);
        var v3 = Vertex.of(x, n, -z);
        var v4 = Vertex.of(n, z, x);
        var v5 = Vertex.of(n, z, -x);
        var v6 = Vertex.of(n, -z, x);
        var v7 = Vertex.of(n, -z, -x);
        var v8 = Vertex.of(z, x, n);
        var v9 = Vertex.of(-z, x, n);
        var v10 = Vertex.of(z, -x, n);
        var v11 = Vertex.of(-z, -x, n);
        var vertices = Arrays.asList(v0, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11);

        var t0 = Triangle.of(vertices, v0, v4, v1);
        var t1 = Triangle.of(vertices, v0, v9, v4);
        var t2 = Triangle.of(vertices, v9, v5, v4);
        var t3 = Triangle.of(vertices, v4, v5, v8);
        var t4 = Triangle.of(vertices, v4, v8, v1);
        var t5 = Triangle.of(vertices, v8, v10, v1);
        var t6 = Triangle.of(vertices, v8, v3, v10);
        var t7 = Triangle.of(vertices, v5, v3, v8);
        var t8 = Triangle.of(vertices, v5, v2, v3);
        var t9 = Triangle.of(vertices, v2, v7, v3);
        var t10 = Triangle.of(vertices, v7, v10, v3);
        var t11 = Triangle.of(vertices, v7, v6, v10);
        var t12 = Triangle.of(vertices, v7, v11, v6);
        var t13 = Triangle.of(vertices, v11, v0, v6);
        var t14 = Triangle.of(vertices, v0, v1, v6);
        var t15 = Triangle.of(vertices, v6, v1, v10);
        var t16 = Triangle.of(vertices, v9, v0, v11);
        var t17 = Triangle.of(vertices, v9, v11, v2);
        var t18 = Triangle.of(vertices, v9, v2, v5);
        var t19 = Triangle.of(vertices, v7, v2, v11);
        var indices = Arrays.asList(t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);

        return new Mesh(vertices, indices);
    }

    private static void getMiddlePoint(int index1, int index2) {

    }
}
