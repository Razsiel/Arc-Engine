package nl.arkenbout.geoffrey.angel.ecs;

import nl.arkenbout.geoffrey.angel.ecs.context.Context;
import nl.arkenbout.geoffrey.angel.ecs.registry.ComponentSystemRegistry;
import nl.arkenbout.geoffrey.angel.ecs.registry.EntityRegistry;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseContext implements Context {

    protected final ComponentSystemRegistry componentSystemRegistery;
    protected final EntityRegistry entityRegistry;
    private final String name;

    public BaseContext(String name) {
        this.name = name;
        componentSystemRegistery = new ComponentSystemRegistry(this);
        entityRegistry = new EntityRegistry();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Entity createEntity(Component... components) {
        var entity = new Entity(entityRegistry.getNextEntityId(), Arrays.asList(components));
        return entityRegistry.addEntity(entity);
    }

    @Override
    public List<Entity> getEntities() {
        return new ArrayList<>(this.entityRegistry.getEntities());
    }

    @Override
    public ComponentSystemRegistry getComponentSystemRegistery() {
        return this.componentSystemRegistery;
    }

    @Override
    public ComponentSystem registerSystem(ComponentSystem system) {
        return this.componentSystemRegistery.registerSystem(system);
    }

    @Override
    public <T extends ComponentSystem> T registerSystem(Class<T> system) throws InvocationTargetException, InstantiationException {
        return this.componentSystemRegistery.registerSystem(system);
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public void cleanup() {
        this.componentSystemRegistery
                .getComponentSystems()
                .stream()
                .parallel()
                .forEach(ComponentSystem::cleanup);
        this.entityRegistry.getEntities()
                .stream()
                .parallel()
                .forEach(Entity::cleanup);
    }
}
