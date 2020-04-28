package nl.arkenbout.geoffrey.angel.ecs.system;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;

public abstract class DualComponentSystem<C1 extends Component, C2 extends Component> extends ComponentSystem {
    protected DualComponentSystem(Class<C1> c1, Class<C2> c2) {
        super(new ComponentMatcher(c1, c2));
    }
}