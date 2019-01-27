package nl.arkenbout.geoffrey.arc.ecs;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ComponentSystemRegistry {
    private HashSet<ComponentSystem> componentSystems = new HashSet<>();

    private GameContext context;

    public ComponentSystemRegistry(GameContext context) {
        this.context = context;
    }

    public <T extends ComponentSystem> T registerSystem(Class<T> newSystemClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (newSystemClass == null) {
            throw new IllegalArgumentException("A system type must be given");
        }

        var newSystem = newSystemClass.getDeclaredConstructor().newInstance();
        addSystem(newSystem);
        return newSystem;
    }

    public <T extends ComponentSystem> T registerSystem(T newSystem) {
        try {
            addSystem(newSystem);
            return newSystem;
        } catch (Exception e) {
            newSystem.setComponentRegistry(null);
            throw e;
        }
    }

    private void addSystem(ComponentSystem newComponentSystem) {
        if (newComponentSystem == null)
            throw new IllegalArgumentException("A system must be passed");
        newComponentSystem.setComponentRegistry(context.getComponentRegistry());
        componentSystems.add(newComponentSystem);
    }

    public Set<ComponentSystem> getComponentSystems() {
        return Collections.unmodifiableSet(componentSystems);
    }
}
