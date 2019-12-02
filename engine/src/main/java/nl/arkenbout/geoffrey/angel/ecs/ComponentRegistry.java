package nl.arkenbout.geoffrey.angel.ecs;

import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;

import java.util.*;
import java.util.stream.Collectors;

public class ComponentRegistry {
    private Map<Integer, List<Component>> components = new HashMap<>();

    Component addComponent(Entity entity, Component newComponent) {
        return addComponent(entity.getId(), newComponent);
    }

    Component addComponent(int entityId, Component newComponent) {
        if (newComponent == null) {
            throw new IllegalArgumentException("A component must be passed to add it");
        }

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
        if (entity == null) {
            return Collections.emptyList();
        }
        return getComponents(entity.getId());
    }

    <T extends Component> List<T> getComponents(Class<T> type) {
        var matcher = new ComponentMatcher(type);
        return getComponents(matcher).stream()
                .map(componentMatch -> componentMatch.getComponent(type))
                .collect(Collectors.toList());
    }

    public List<ComponentMatch> getComponents(ComponentMatcher matcher) {
        List<ComponentMatch> matched = new ArrayList<>();
        var match = matcher.getMatchTypes();

        for (var componentsByEntity : this.components.entrySet()) {
            var components = componentsByEntity.getValue();
            if (components.size() < match.size()) {
                break;
            }

            List<Component> matchedComponents = components.stream()
                    .filter(component -> match.contains(component.getClass()))
                    .collect(Collectors.toList());

            ComponentMatch componentMatch = new ComponentMatch(componentsByEntity.getKey(), matchedComponents);
            matched.add(componentMatch);
        }
        return matched;
    }
}
