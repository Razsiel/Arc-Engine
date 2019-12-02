package nl.arkenbout.geoffrey.angel.ecs.test;

import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;

public class PrivateConstructorTestComponentSystem extends ComponentSystem {

    private PrivateConstructorTestComponentSystem() {
        super(new ComponentMatcher());
    }

    @Override
    public void doEachComponent(ComponentMatch match) {

    }
}
