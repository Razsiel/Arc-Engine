package nl.arkenbout.geoffrey.game.systems;

import nl.arkenbout.geoffrey.arc.ecs.ComponentMatch;
import nl.arkenbout.geoffrey.arc.ecs.ComponentMatcher;
import nl.arkenbout.geoffrey.arc.ecs.ComponentSystem;
import nl.arkenbout.geoffrey.arc.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.arc.engine.core.GameTimer;
import nl.arkenbout.geoffrey.game.components.RotatorComponent;
import org.joml.Vector3f;

import java.util.List;

public class RotatorSystem extends ComponentSystem {
    @Override
    public void update() {
        var matcher = new ComponentMatcher(TransformComponent.class, RotatorComponent.class);
        List<ComponentMatch> matchedComponents = getComponents(matcher);

        for (var match : matchedComponents) {
            var transform = match.getComponent(TransformComponent.class);
            var rotator = match.getComponent(RotatorComponent.class);

            var angle = (float)(GameTimer.getInstance().getTimeSinceStart() * rotator.getSpeed()) % 360;
            var axis = rotator.getAxis();
            //System.out.println("rotation (" + match.getEntityId() + "): " + angle + " on axis: " + axis);
            var rotation = new Vector3f(axis).mul(angle);

            transform.setRotation(rotation);
        }
    }
}
