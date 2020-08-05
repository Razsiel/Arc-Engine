package nl.arkenbout.geoffrey.angel.ecs.match;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.ecs.Entity;

import java.util.Collection;
import java.util.Map;

public class EntityComponentMatch {
    private final Entity entity;
    private final Map<Class<? extends Component>, ? extends Component> matchedComponents;

    public EntityComponentMatch(Entity entity, Map<Class<? extends Component>, Component> matchedComponents) {
        this.entity = entity;
        this.matchedComponents = matchedComponents;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> matchClass) {
        return (T) matchedComponents.get(matchClass);
    }

    public Collection<? extends Component> getMatchedComponents() {
        return matchedComponents.values();
    }

    public Entity getEntity() {
        return entity;
    }
}
