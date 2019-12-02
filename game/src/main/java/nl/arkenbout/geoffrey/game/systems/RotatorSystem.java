package nl.arkenbout.geoffrey.game.systems;

import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.system.DualComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.GameTimer;
import nl.arkenbout.geoffrey.game.components.RotatorComponent;
import org.joml.Vector3f;

public class RotatorSystem extends DualComponentSystem<TransformComponent, RotatorComponent> {

    public RotatorSystem() {
        super(TransformComponent.class, RotatorComponent.class);
    }

    @Override
    protected void doEachComponent(ComponentMatch match) {
        var transform = match.getComponent(TransformComponent.class);
        var rotator = match.getComponent(RotatorComponent.class);

        double timeSinceStart = GameTimer.getInstance().getTimeSinceStart();
        var angle = (float) (timeSinceStart * rotator.getSpeed()) % 360f;
        var axis = rotator.getAxis();
        var rotation = new Vector3f(axis).mul(angle);

        transform.setRotation(rotation);
    }
}
