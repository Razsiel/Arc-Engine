package nl.arkenbout.geoffrey.angel.engine.core.graphics.mesh;

import org.joml.Vector3d;

import java.util.stream.DoubleStream;

public class Vertex {
    private final Vector3d position;

    public Vertex(double x, double y, double z) {
        position = new Vector3d(x, y, z);
    }

    public static Vertex of(double x, double y, double z) {
        return new Vertex(x, y, z);
    }

    public static DoubleStream getPositionElements(Vertex v) {
        return DoubleStream.of(v.getX(), v.getY(), v.getZ());
    }

    public double getX() {
        return position.x();
    }

    public double getY() {
        return position.y();
    }

    public double getZ() {
        return position.z();
    }

    public Vector3d getPosition() {
        return new Vector3d(position);
    }
}
