package nl.arkenbout.geoffrey.game.systems;

import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.system.DualComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.GameTimer;
import nl.arkenbout.geoffrey.angel.engine.util.MathUtils;
import nl.arkenbout.geoffrey.game.components.ScalePingPongComponent;

public class ScalePingPongSystem extends DualComponentSystem<TransformComponent, ScalePingPongComponent> {
    public ScalePingPongSystem() {
        super(TransformComponent.class, ScalePingPongComponent.class);
    }

    @Override
    protected void update(Entity entity, TransformComponent transform, ScalePingPongComponent pingpong) {
        var speed = pingpong.getSpeed();

        double timeSinceStart = GameTimer.getInstance().getTimeSinceStart();
        var newScale = (float) Math.abs(Math.sin(timeSinceStart + transform.getPosition().x() * speed));
        var remappedNewScale = MathUtils.remap(newScale, 0, 1, pingpong.getMinSize(), pingpong.getMaxSize());

        transform.setScale(remappedNewScale);
    }
}
