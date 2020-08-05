package nl.arkenbout.geoffrey.angel.ecs.context;

import nl.arkenbout.geoffrey.angel.ecs.BaseContext;
import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Scene extends BaseContext {
    public Scene(String name) {
        super(name);
    }

    @Override
    public void update(Set<ComponentSystem> globalSystems) {
        var sceneSystems = this.componentSystemRegistery.getComponentSystems();

        Stream.concat(globalSystems.stream(), sceneSystems.stream())
                .parallel()
                .forEach(componentSystem -> componentSystem.update(this.getEntities()));
    }
}
