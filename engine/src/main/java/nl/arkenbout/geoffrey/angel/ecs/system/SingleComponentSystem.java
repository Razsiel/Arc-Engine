package nl.arkenbout.geoffrey.angel.ecs.system;

import nl.arkenbout.geoffrey.angel.ecs.Component;

public abstract class SingleComponentSystem<C extends Component> extends ComponentSystem {

    public SingleComponentSystem(Class<C> c1) {
        super(c1);
        this.updater = match -> update(match.getComponent(c1));
    }

    protected abstract void update(C component);
}
