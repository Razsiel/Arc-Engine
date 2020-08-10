package nl.arkenbout.geoffrey.angel.ecs.system;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.ecs.Entity;

import java.util.function.Predicate;

public abstract class DualComponentSystem<C1 extends Component, C2 extends Component> extends ComponentSystem {

    public DualComponentSystem(Class<C1> c1, Class<C2> c2) {
        this(c1, c2, null);
    }

    public DualComponentSystem(Class<C1> c1, Class<C2> c2, Predicate<Entity> matchPredicate) {
        super(matchPredicate, c1, c2);
        this.update = (match) -> update(match.getEntity(), match.getComponent(c1), match.getComponent(c2));
    }

    protected abstract void update(Entity entity, C1 component1, C2 component2);
}