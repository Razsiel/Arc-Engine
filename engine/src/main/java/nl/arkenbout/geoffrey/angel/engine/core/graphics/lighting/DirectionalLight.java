package nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting;

import org.joml.Vector3f;
import org.lwjgl.util.ReadableColor;

public class DirectionalLight {

    private Vector3f color;
    private Vector3f direction;
    private float intensity;

    public DirectionalLight(Vector3f color, Vector3f direction, float intensity) {
        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
    }

    public DirectionalLight(ReadableColor color, Vector3f direction, float intensity) {
        this(new Vector3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f), direction, intensity);
    }

    public DirectionalLight(DirectionalLight light) {
        this(new Vector3f(light.getColor()), new Vector3f(light.getDirection()), light.getIntensity());
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public float getIntensity() {
        return intensity;
    }
}
