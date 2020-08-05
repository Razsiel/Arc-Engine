package nl.arkenbout.geoffrey.angel.ecs.context;

import nl.arkenbout.geoffrey.angel.ecs.registry.ComponentSystemRegistry;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;

import java.lang.reflect.InvocationTargetException;

public interface SystemRegistryHolder {
    ComponentSystemRegistry getComponentSystemRegistery();
    ComponentSystem registerSystem(ComponentSystem system);
    <T extends ComponentSystem> T registerSystem(Class<T> system) throws InvocationTargetException, InstantiationException;
}
