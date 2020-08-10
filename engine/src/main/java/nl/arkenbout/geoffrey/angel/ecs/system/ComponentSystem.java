package nl.arkenbout.geoffrey.angel.ecs.system;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.match.EntityComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.EntityComponentMatcher;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class ComponentSystem {
    protected final EntityComponentMatcher matcher;
    protected final Predicate<Entity> predicate;
    protected Consumer<EntityComponentMatch> update;

    @SafeVarargs
    protected ComponentSystem(Class<? extends Component>... components) {
        this(null, components);
    }

    @SafeVarargs
    protected ComponentSystem(Predicate<Entity> predicate, Class<? extends Component>... components) {
        this.matcher = new EntityComponentMatcher(components);
        this.predicate = (predicate == null) ? (entity -> true) : predicate;
    }

    public void update(Collection<Entity> entities) {
        matcher.match(entities, predicate)
                .forEach(update);
    }

    public void cleanup() {
    }
}

