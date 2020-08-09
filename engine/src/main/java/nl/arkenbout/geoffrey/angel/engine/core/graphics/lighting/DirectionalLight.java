package nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting;

import org.joml.Vector3d;
import org.lwjgl.util.ReadableColor;

public class DirectionalLight {

    private Vector3d color;
    private Vector3d direction;
    private double intensity;

    public DirectionalLight(ReadableColor color, Vector3d direction, double intensity) {
        this(new Vector3d(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f), direction, intensity);
    }

    public DirectionalLight(Vector3d color, Vector3d direction, double intensity) {
        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
    }

    public DirectionalLight(DirectionalLight light) {
        this(new Vector3d(light.getColor()), new Vector3d(light.getDirection()), light.getIntensity());
    }

    public Vector3d getColor() {
        return color;
    }

    public Vector3d getDirection() {
        return direction;
    }

    public void setDirection(Vector3d direction) {
        this.direction = direction;
    }

    public double getIntensity() {
        return intensity;
    }
}
