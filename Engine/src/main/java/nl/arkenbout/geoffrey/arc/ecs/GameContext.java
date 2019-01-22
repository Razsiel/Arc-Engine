package nl.arkenbout.geoffrey.arc.ecs;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class GameContext {
    private static GameContext instance;

    private Map<Integer, Entity> entities = new HashMap<>();
    private HashSet<ComponentSystem> componentSystems = new HashSet<>();
    private Map<Integer, List<Component>> components = new HashMap<>();

    public static GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
        }
        return instance;
    }

    private GameContext() {

    }

    public <T extends ComponentSystem<C>, C extends Component> T registerSystem(Class<T> newSystemClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (newSystemClass == null) {
            throw new IllegalArgumentException("A system type must be given");
        }

        var newSystem = newSystemClass.getDeclaredConstructor().newInstance();
        addSystem(newSystem);
        return newSystem;
    }

    public <T extends ComponentSystem<C>, C extends Component> T registerSystem(T newSystem) {
        try {
            addSystem(newSystem);
            return newSystem;
        } catch (Exception e) {
            newSystem.setContext(null);
            throw e;
        }
    }

    private <T extends Component> void addSystem(ComponentSystem<T> newComponentSystem) {
        if (newComponentSystem == null)
            throw new IllegalArgumentException("A system must be passed");
        newComponentSystem.setContext(this);
        componentSystems.add(newComponentSystem);
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

    public <T extends Component> List<T> getComponents(Class<T> type) {
        var matcher = new ComponentMatcher(type);
        return getComponents(matcher).stream()
                .map(componentMatch -> componentMatch.getComponent(type))
                .collect(Collectors.toList());
        /*
        return components.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(component -> component.getClass().equals(type))
                .map(component -> (T) component)
                .collect(Collectors.toUnmodifiableList());*/
    }

    public List<ComponentMatch> getComponents(ComponentMatcher matcher) {
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

    public List<Component> getComponents(int entityId) {
        return Collections.unmodifiableList(components.get(entityId));
    }

    public List<Component> getComponents(Entity entity) {
        return Collections.unmodifiableList(components.get(entity.getId()));
    }

    public Set<ComponentSystem> getComponentSystems() {
        return Collections.unmodifiableSet(componentSystems);
    }

    public Entity createEntity(Component... components) {
        var entity = new Entity(0);
        entity.setContext(this);
        entities.put(entity.getId(), entity);
        for (var component : components) {
            this.components.get(entity.getId()).add(component);
        }
        return entity;
    }
}
