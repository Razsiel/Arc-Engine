package nl.arkenbout.geoffrey.game.systems;

import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
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
    protected void doEachComponent(ComponentMatch match) {
        var transform = match.getComponent(TransformComponent.class);
        var bouncer = match.getComponent(BounceComponent.class);

        var newY = (float) Math.abs(Math.sin(GameTimer.getInstance().getTimeSinceStart() + transform.getPosition().x()));
        newY = MathUtils.remap(newY, 0, 1, bouncer.getMinHeight(), bouncer.getMaxHeight());

        transform.setPositionY(newY);
    }
}
