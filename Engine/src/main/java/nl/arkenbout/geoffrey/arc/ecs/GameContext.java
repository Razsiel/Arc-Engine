package nl.arkenbout.geoffrey.arc.ecs;

public class GameContext {
    private static GameContext instance;

    private final ComponentRegistry componentRegistry;
    private final ComponentSystemRegistry componentSystemRegistery;
    private final EntityRegistry entityRegistry;

    public static GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
        }
        return instance;
    }

    private GameContext() {
        componentRegistry = new ComponentRegistry();
        componentSystemRegistery = new ComponentSystemRegistry(this);
        entityRegistry = new EntityRegistry();
    }

    public ComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }

    public ComponentSystemRegistry getComponentSystemRegistery() {
        return componentSystemRegistery;
    }

    public EntityRegistry getEntityRegistry() {
        return entityRegistry;
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
}
