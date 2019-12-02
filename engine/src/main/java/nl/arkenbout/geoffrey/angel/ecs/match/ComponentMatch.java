package nl.arkenbout.geoffrey.angel.ecs.match;

import nl.arkenbout.geoffrey.angel.ecs.Component;

import java.util.List;
import java.util.Optional;

public class ComponentMatch {
    private final String entityId;
    private final List<? extends Component> matchedComponents;

    public ComponentMatch(String entityId, List<Component> matchedComponents) {
        this.entityId = entityId;
        this.matchedComponents = matchedComponents;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> matchClass) {
        Optional<? extends Component> component = matchedComponents.stream()
                .filter(c -> c.getClass().equals(matchClass))
                .findFirst();
        return (T) component.orElse(null);
    }

    public String getEntityId() {
        return entityId;
    }

    public List<? extends Component> getMatchedComponents() {
        return matchedComponents;
    }
}
