package nl.arkenbout.geoffrey.angel.ecs;

import java.util.*;

public class Entity {

    private final String id;

    private List<Component> components;

    Entity(String id) {
        this(id, null);
    }

    Entity(String id, List<Component> components) {
        this.id = id;
        this.components = components;
        if (this.components == null) {
            this.components = new ArrayList<>();
        }
    }

    public void addComponent(Component component) {
        this.components.add(component);
    }

    public List<Component> getComponents() {
        return Collections.unmodifiableList(this.components);
    }

    public String getId() {
        return id;
    }

    public void cleanup() {
        this.components.forEach(Component::cleanup);
    }

    public boolean hasComponents(Collection<Class<? extends Component>> componentTypes) {
        return componentTypes.stream()
                .map(this::hasComponent)
                .reduce(Boolean::equals)
                .orElse(false);
    }

    public boolean hasComponent(Class<? extends Component> componentType) {
        return this.components.stream().anyMatch(component -> component.getClass().equals(componentType));
    }

    public Optional<Component> getComponent(Class<? extends Component> componentClass) {
        return this.components.stream()
                .filter(component -> component.getClass().equals(componentClass))
                .findFirst();
    }
}
