package nl.arkenbout.geoffrey.game.components;

import nl.arkenbout.geoffrey.angel.ecs.BaseComponent;

public class ScalePingPongComponent extends BaseComponent {

    private final float speed;
    private final float minSize;
    private final float maxSize;

    public ScalePingPongComponent() {
        this(0.1f, 0.5f, 2f);
    }

    public ScalePingPongComponent(float speed, float minSize, float maxSize) {
        this.speed = speed;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public float getSpeed() {
        return speed;
    }

    public float getMinSize() {
        return minSize;
    }

    public float getMaxSize() {
        return maxSize;
    }
}
