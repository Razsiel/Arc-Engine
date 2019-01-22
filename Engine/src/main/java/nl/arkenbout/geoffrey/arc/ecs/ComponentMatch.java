package nl.arkenbout.geoffrey.arc.ecs;

import java.util.List;
import java.util.Optional;

public class ComponentMatch {
    private final int entityId;
    private List<Component> matchedComponents;

    public ComponentMatch(int entityId, List<Component> matchedComponents) {
        this.entityId = entityId;
        this.matchedComponents = matchedComponents;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> matchClass) {
        Optional<Component> component = matchedComponents.stream()
                .filter(c -> c.getClass().equals(matchClass))
                .findFirst();
        return (T) component.get();
    }

    public List<Component> getMatchedComponents() {
        return this.matchedComponents;
    }

    public int getEntityId() {
        return entityId;
    }
}
