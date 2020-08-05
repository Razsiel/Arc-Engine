package nl.arkenbout.geoffrey.angel.ecs.system;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.match.EntityComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.EntityComponentMatcher;

import java.util.Collection;
import java.util.function.Consumer;

public abstract class ComponentSystem {
    protected final EntityComponentMatcher matcher;
    protected Consumer<EntityComponentMatch> updater;

    @SafeVarargs
    protected ComponentSystem(Class<? extends Component>... components) {
        this.matcher = new EntityComponentMatcher(components);
    }

    public void update(Collection<Entity> entities) {
        matcher.match(entities).forEach(match -> updater.accept(match));
    }

    public void cleanup() {
    }
}

