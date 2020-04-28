package nl.arkenbout.geoffrey.angel.ecs;

import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystemRegistry;

public class GameContext implements Context {
    private static GameContext instance;

    private final ComponentRegistry componentRegistry;
    private final ComponentSystemRegistry componentSystemRegistery;
    private final EntityRegistry entityRegistry;
    private SceneContext activeSceneContext;

    private GameContext() {
        componentRegistry = new ComponentRegistry();
        componentSystemRegistery = new ComponentSystemRegistry(this);
        entityRegistry = new EntityRegistry();
    }

    public static GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
        }
        return instance;
    }

    @Override
    public ComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }

    public ComponentSystemRegistry getComponentSystemRegistery() {
        return componentSystemRegistery;
    }

    public Entity createEntity(Component... components) {
        var entity = new Entity(entityRegistry.getNextEntityId());
        entity.setContext(this);
        entityRegistry.addEntity(entity);
        for (var component : components) {
            componentRegistry.addComponent(entity, component);
        }
        return entity;
    }

    public SceneContext getActiveSceneContext() {
        return this.activeSceneContext;
    }

    public void setActiveSceneContext(SceneContext sceneContext) {
        sceneContext.load();
        this.activeSceneContext = sceneContext;
    }

    public void cleanup() {
        instance = null;
        this.componentSystemRegistery
                .getComponentSystems()
                .stream()
                .parallel()
                .forEach(ComponentSystem::cleanup);
    }

    public ComponentSystem registerSystem(ComponentSystem system) {
        return this.componentSystemRegistery.registerSystem(system);
    }
}
