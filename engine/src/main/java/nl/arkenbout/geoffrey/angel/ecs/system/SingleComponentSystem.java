package nl.arkenbout.geoffrey.angel.ecs.system;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;

public abstract class SingleComponentSystem<T extends Component> extends ComponentSystem {
    protected SingleComponentSystem(Class<T> componentType) {
        super(new ComponentMatcher(componentType));
    }
}
