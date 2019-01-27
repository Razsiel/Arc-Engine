package nl.arkenbout.geoffrey.arc.ecs;

import java.util.*;
import java.util.stream.Collectors;

public class ComponentRegistry {
    private Map<Integer, List<Component>> components = new HashMap<>();

    Component addComponent(Entity entity, Component newComponent) {
        return addComponent(entity.getId(), newComponent);
    }

    Component addComponent(int entityId, Component newComponent) {
        if (newComponent == null)
            throw new IllegalArgumentException("A component must be passed to add it");

        if (!components.containsKey(entityId)) {
            components.put(entityId, new ArrayList<>());
        }

        components.get(entityId).add(newComponent);
        return newComponent;
    }

    public List<Component> getComponents(int entityId) {
        return Collections.unmodifiableList(components.get(entityId));
    }

    public List<Component> getComponents(Entity entity) {
        return getComponents(entity.getId());
    }

    <T extends Component> List<T> getComponents(Class<T> type) {
        var matcher = new ComponentMatcher(type);
        return getComponents(matcher).stream()
                .map(componentMatch -> componentMatch.getComponent(type))
                .collect(Collectors.toList());
    }

    List<ComponentMatch> getComponents(ComponentMatcher matcher) {
        // containsAll
        List<ComponentMatch> matched = new ArrayList<>();
        var match = matcher.getMatchTypes();
        var matchSize = match.size();
        for (var componentsByEntity : this.components.entrySet()) {
            var components = componentsByEntity.getValue();
            boolean matchesAll = false;
            List<Component> matchedComponents = new ArrayList<>();
            for (var component : components) {
                var matches = match.contains(component.getClass());
                if (matches) {
                    matchedComponents.add(component);
                    if (matchedComponents.size() == matchSize) {
                        matchesAll = true;
                        break;
                    }
                }

            }
            if (matchesAll)
                matched.add(new ComponentMatch(componentsByEntity.getKey(), matchedComponents));
        }
        return matched;
    }
}
