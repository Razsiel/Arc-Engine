package nl.arkenbout.geoffrey.arc.ecs.test;

import nl.arkenbout.geoffrey.arc.ecs.ComponentSystem;

public class PrivateConstructorTestComponentSystem extends ComponentSystem<TestComponent> {

    private PrivateConstructorTestComponentSystem() {
    }

    @Override
    public void update() {

    }
}
