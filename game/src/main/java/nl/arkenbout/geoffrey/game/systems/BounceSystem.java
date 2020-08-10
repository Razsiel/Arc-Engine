package nl.arkenbout.geoffrey.game.systems;

import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.system.DualComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.GameTimer;
import nl.arkenbout.geoffrey.angel.engine.util.MathUtils;
import nl.arkenbout.geoffrey.game.components.BounceComponent;

public class BounceSystem extends DualComponentSystem<TransformComponent, BounceComponent> {

    public BounceSystem() {
        super(TransformComponent.class, BounceComponent.class);
    }

    @Override
    protected void update(Entity entity, TransformComponent transform, BounceComponent bouncer) {
        var position = transform.getPosition();
        var newY = (float) Math.abs(Math.sin(GameTimer.getInstance().getTimeSinceStart() + position.x()));
        newY = MathUtils.remap(newY, 0, 1, bouncer.getMinHeight(), bouncer.getMaxHeight());

        transform.setPosition(position.x(), newY, position.z());
    }
}
