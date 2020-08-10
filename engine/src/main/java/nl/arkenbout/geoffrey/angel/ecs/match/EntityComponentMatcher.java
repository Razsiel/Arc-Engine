package nl.arkenbout.geoffrey.angel.ecs.match;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.ecs.Entity;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EntityComponentMatcher {

    private HashMap<Class<? extends Component>, List<Component>> componentsToMatch;

    @SafeVarargs
    public EntityComponentMatcher(Class<? extends Component>... componentsToMatch) {
        this.componentsToMatch = new HashMap<>(componentsToMatch.length);
        Arrays.stream(componentsToMatch)
                .forEach(componentClass -> this.componentsToMatch.put(componentClass, new ArrayList<>()));
    }

    public List<EntityComponentMatch> match(Collection<Entity> entities, Predicate<Entity> matchPredicate) {
        return entities.stream()
                .filter(entity -> entity.hasComponents(this.getComponentTypes()))
                .filter(matchPredicate)
                .map(entity -> {
                    Map<Class<? extends Component>, Component> matchedComponents = getMatchingComponents(entity);
                    return new EntityComponentMatch(entity, matchedComponents);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    private Map<Class<? extends Component>, Component> getMatchingComponents(Entity entity) {
        return componentsToMatch.keySet().stream()
                .map(componentClass -> entity
                        .getComponent(componentClass)
                        .orElseThrow(() -> new IllegalStateException("Unexpected state whilst matching components")))
                .collect(Collectors.toUnmodifiableMap(Component::getClass, component -> component));
    }

    public Set<Class<? extends Component>> getComponentTypes() {
        return Collections.unmodifiableSet(componentsToMatch.keySet());
    }
}


