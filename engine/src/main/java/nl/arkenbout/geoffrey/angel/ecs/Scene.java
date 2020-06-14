package nl.arkenbout.geoffrey.angel.ecs;

import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystemRegistry;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Scene implements Context {
    private final String name;

    private final ComponentRegistry sceneComponentRegistry;
    private final ComponentSystemRegistry sceneComponentSystemRegistery;
    private final EntityRegistry sceneEntityRegistry;

    public Scene(String name) {
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

    public void update(Set<ComponentSystem> globalSystems) {
        var sceneSystems = this.getComponentSystemRegistery().getComponentSystems();

        Stream.concat(globalSystems.stream(), sceneSystems.stream())
                .parallel()
                .forEach(ComponentSystem::update);
    }

    public List<ComponentMatch> getRenderComponentMatches(ComponentMatcher renderMatcher) {
        return sceneComponentRegistry.getComponents(renderMatcher);
    }
}
