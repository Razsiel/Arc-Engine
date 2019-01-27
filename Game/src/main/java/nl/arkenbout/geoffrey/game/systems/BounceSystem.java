package nl.arkenbout.geoffrey.game.systems;

import nl.arkenbout.geoffrey.arc.ecs.ComponentMatch;
import nl.arkenbout.geoffrey.arc.ecs.ComponentMatcher;
import nl.arkenbout.geoffrey.arc.ecs.ComponentSystem;
import nl.arkenbout.geoffrey.arc.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.arc.engine.core.GameTimer;
import nl.arkenbout.geoffrey.arc.engine.util.MathUtils;
import nl.arkenbout.geoffrey.game.components.BounceComponent;

import java.util.List;

public class BounceSystem extends ComponentSystem {
    @Override
    public void update() {
        var matcher = new ComponentMatcher(TransformComponent.class, BounceComponent.class);
        List<ComponentMatch> matchedComponents = getComponents(matcher);

        for (var match : matchedComponents) {
            var transform = match.getComponent(TransformComponent.class);
            var bouncer = match.getComponent(BounceComponent.class);

            var newY = (float)Math.abs(Math.sin(GameTimer.getInstance().getTimeSinceStart() + transform.getPosition().x));
            newY = MathUtils.remap(newY, -1, 1, bouncer.getMinHeight(), bouncer.getMaxHeight());

            transform.getPosition().y = newY;
        }
    }
}
