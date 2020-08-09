package nl.arkenbout.geoffrey.angel.engine.core.graphics.mesh;

import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.stream.Stream;

public class Vertex {
    private final Vector3f position;

    public Vertex(float x, float y, float z) {
        position = new Vector3f(x, y, z);
    }

    public static Vertex of(float x, float y, float z) {
        return new Vertex(x, y, z);
    }

    public static Stream<Float> getPositionElements(Vertex v) {
        return Stream.of(v.getX(), v.getY(), v.getZ());
    }

    public float getX() {
        return position.x();
    }

    public float getY() {
        return position.y();
    }

    public float getZ() {
        return position.z();
    }

    public Vector3d getPosition() {
        return new Vector3d(position);
    }
}
