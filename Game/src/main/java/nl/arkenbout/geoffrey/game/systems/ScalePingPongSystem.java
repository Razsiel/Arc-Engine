package nl.arkenbout.geoffrey.game.systems;

import nl.arkenbout.geoffrey.arc.ecs.ComponentMatch;
import nl.arkenbout.geoffrey.arc.ecs.ComponentMatcher;
import nl.arkenbout.geoffrey.arc.ecs.ComponentSystem;
import nl.arkenbout.geoffrey.arc.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.arc.engine.core.GameTimer;
import nl.arkenbout.geoffrey.arc.engine.util.MathUtils;
import nl.arkenbout.geoffrey.game.components.ScalePingPongComponent;

import java.util.List;

public class ScalePingPongSystem extends ComponentSystem {
    @Override
    public void update() {
        var matcher = new ComponentMatcher(TransformComponent.class, ScalePingPongComponent.class);
        List<ComponentMatch> matchedComponents = getComponents(matcher);

        for (var match : matchedComponents) {
            var transform = match.getComponent(TransformComponent.class);
            var pingpong = match.getComponent(ScalePingPongComponent.class);

            var speed = pingpong.getSpeed();

            var newScale = (float)Math.sin(GameTimer.getInstance().getTimeSinceStart() + transform.getPosition().x * speed);
            newScale = MathUtils.remap(newScale, -1, 1, pingpong.getMinSize(), pingpong.getMaxSize());

            transform.setScale(newScale);
        }
    }
}
