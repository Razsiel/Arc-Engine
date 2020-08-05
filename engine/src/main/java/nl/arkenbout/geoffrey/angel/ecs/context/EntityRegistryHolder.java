package nl.arkenbout.geoffrey.angel.ecs.context;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.ecs.Entity;

import java.util.List;

public interface EntityRegistryHolder {
    Entity createEntity(Component... components);

    List<Entity> getEntities();
}
