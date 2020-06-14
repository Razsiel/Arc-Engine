package nl.arkenbout.geoffrey.angel.ecs;

import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;

import java.util.*;
import java.util.stream.Collectors;

public class ComponentRegistry {
    private Map<String, List<Component>> components = new HashMap<>();

    Component addComponent(Entity entity, Component newComponent) {
        return addComponent(entity.getId(), newComponent);
    }

    Component addComponent(String entityId, Component newComponent) {
        if (newComponent == null) {
            throw new IllegalArgumentException("A component must be passed to add it");
        }

        if (!components.containsKey(entityId)) {
            components.put(entityId, new ArrayList<>());
        }

        components.get(entityId).add(newComponent);
        return newComponent;
    }

    public List<Component> getComponents(String entityId) {
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
                .parallel()
                .collect(Collectors.toList());
    }

    //TODO: Optimize with caching
    public List<ComponentMatch> getComponents(ComponentMatcher matcher) {
        var matchTypes = matcher.getMatchTypes();

        // create a shadow map of the entities + the classed version of their components
        var classedComponents = this.components.entrySet()
                .parallelStream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(Object::getClass).collect(Collectors.toSet())
                ));

        return this.components.entrySet().parallelStream()
                .filter(entry -> classedComponents.get(entry.getKey()).containsAll(matchTypes))
                .map(entitySetContainingComponents -> new ComponentMatch(entitySetContainingComponents.getKey(), entitySetContainingComponents.getValue()))
                .collect(Collectors.toList());
    }
}
