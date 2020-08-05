package nl.arkenbout.geoffrey.game.systems;

import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.match.EntityComponentMatch;
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
    protected void update(Entity entity, TransformComponent transform, RotatorComponent rotator) {
        double timeSinceStart = GameTimer.getInstance().getTimeSinceStart();
        var angle = (float) (timeSinceStart * rotator.getSpeed()) % 360f;
        var axis = rotator.getAxis();
        var rotation = new Vector3f(axis).mul(angle);

        transform.setRotation(rotation);
    }
}
