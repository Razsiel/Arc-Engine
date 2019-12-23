package nl.arkenbout.geoffrey.angel.ecs;

import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystemRegistry;

import java.util.List;

public class SceneContext implements Context {
    private final String name;

    private final ComponentRegistry sceneComponentRegistry;
    private final ComponentSystemRegistry sceneComponentSystemRegistery;
    private final EntityRegistry sceneEntityRegistry;

    public SceneContext(String name, GameContext context) {
        this.name = name;
        this.sceneComponentRegistry = new ComponentRegistry();
        this.sceneComponentSystemRegistery = new ComponentSystemRegistry(this);
        this.sceneEntityRegistry = new EntityRegistry();
    }

    public Entity createEntity(Component... components) {
        var entity = new Entity(sceneEntityRegistry.getNextEntityId());
        entity.setContext(this);
        sceneEntityRegistry.addEntity(entity);
        for (var component : components) {
            sceneComponentRegistry.addComponent(entity, component);
        }
        return entity;
    }

    public Entity createEntity(Entity prototype) {
        List<Component> components = sceneComponentRegistry.getComponents(prototype);
        return createEntity(components.toArray(Component[]::new));
    }

    @Override
    public ComponentRegistry getComponentRegistry() {
        return sceneComponentRegistry;
    }

    @Override
    public ComponentSystemRegistry getComponentSystemRegistery() {
        return sceneComponentSystemRegistery;
    }

    public void load() {

    }
}
