package nl.arkenbout.geoffrey.angel.ecs;

import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystemRegistry;

public interface Context {
    Entity createEntity(Component... components);

    abstract ComponentRegistry getComponentRegistry();

    ComponentSystemRegistry getComponentSystemRegistery();
}
