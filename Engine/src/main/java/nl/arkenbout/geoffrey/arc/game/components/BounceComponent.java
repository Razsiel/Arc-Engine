package nl.arkenbout.geoffrey.arc.game.components;

import nl.arkenbout.geoffrey.arc.ecs.Component;

public class BounceComponent implements Component {

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
