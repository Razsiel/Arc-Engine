package nl.arkenbout.geoffrey.game.components;

import nl.arkenbout.geoffrey.angel.ecs.BaseComponent;

public class BounceComponent extends BaseComponent {

    private float maxHeight;
    private float minHeight;

    public BounceComponent(float minHeight, float maxHeight) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public float getMinHeight() {
        return minHeight;
    }
}
