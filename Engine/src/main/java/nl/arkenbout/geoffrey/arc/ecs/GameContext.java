package nl.arkenbout.geoffrey.arc.ecs;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class GameContext {
    private Map<Integer, Entity> entities = new HashMap<>();
    private HashSet<System> systems = new HashSet<>();
    private Map<Integer, List<Component>> components = new HashMap<>();

    public <T extends System<C>, C extends Component> T registerSystem(Class<T> newSystemClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (newSystemClass == null) {
            throw new IllegalArgumentException("A system type must be given");
        }

        var newSystem = newSystemClass.getDeclaredConstructor().newInstance();
        addSystem(newSystem);
        return newSystem;
    }

    public <T extends System<C>, C extends Component> T registerSystem(T newSystem) {
        try {
            addSystem(newSystem);
            return newSystem;
        } catch (Exception e) {
            newSystem.setContext(null);
            throw e;
        }
    }

    private <T extends Component> void addSystem(System<T> newSystem) {
        if (newSystem == null)
            throw new IllegalArgumentException("A system must be passed");
        newSystem.setContext(this);
        systems.add(newSystem);
    }

    public boolean addComponent(int entityId, Component newComponent) {
        if (newComponent == null)
            throw new IllegalArgumentException("A component must be passed to add it");

        if (!components.containsKey(entityId)) {
            components.put(entityId, new ArrayList<>());
        }

        return components.get(entityId).add(newComponent);
    }

    public <T> List<T> getComponents(Class<T> type) {
        return components.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(component -> component.getClass().equals(type))
                .map(component -> (T) component)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Component> getComponents(int entityId) {
        return Collections.unmodifiableList(components.get(entityId));
    }
    public List<Component> getComponents(Entity entity) {
        return Collections.unmodifiableList(components.get(entity.getId()));
    }

    public Set<System> getSystems() {
        return Collections.unmodifiableSet(systems);
    }
}
