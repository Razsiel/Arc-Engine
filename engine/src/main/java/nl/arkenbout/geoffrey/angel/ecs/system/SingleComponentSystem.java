package nl.arkenbout.geoffrey.angel.ecs.system;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.ecs.Entity;

import java.util.function.Predicate;

public abstract class SingleComponentSystem<C extends Component> extends ComponentSystem {

    public SingleComponentSystem(Class<C> c1) {
        this(c1, null);
    }

    public SingleComponentSystem(Class<C> c1, Predicate<Entity> matchPredicate) {
        super(matchPredicate, c1);
        this.update = match -> update(match.getComponent(c1));
    }

    protected abstract void update(C component);
}
