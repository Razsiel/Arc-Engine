package nl.arkenbout.geoffrey.angel.ecs.system;

import nl.arkenbout.geoffrey.angel.ecs.Context;
import nl.arkenbout.geoffrey.angel.ecs.GameContext;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ComponentSystemRegistry {
    private HashSet<ComponentSystem> componentSystems = new HashSet<>();

    private Context context;

    public ComponentSystemRegistry(Context context) {
        this.context = context;
    }

    public <T extends ComponentSystem> T registerSystem(Class<T> newSystemClass) throws IllegalArgumentException, InvocationTargetException, InstantiationException {
        if (newSystemClass == null) {
            throw new IllegalArgumentException("A system type must be given");
        }

        try {
            var newSystem = newSystemClass.getDeclaredConstructor().newInstance();
            addSystem(newSystem);
            return newSystem;
        } catch (IllegalAccessException | NoSuchMethodException e) {
            throw new IllegalArgumentException("A componentSystem can not have a private constructor when calling the Class<> version of \'registerComponent()\'", e);
        } catch (InvocationTargetException | InstantiationException e) {
            throw e;
        }
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
