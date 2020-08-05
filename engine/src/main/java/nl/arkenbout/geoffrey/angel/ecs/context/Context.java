package nl.arkenbout.geoffrey.angel.ecs.context;

import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.util.Cleanup;

import java.util.Set;

public interface Context extends SystemRegistryHolder, EntityRegistryHolder, Cleanup {
    String getName();

    void update(Set<ComponentSystem> parentComponentSystems);

    void load();

    void unload();
}
