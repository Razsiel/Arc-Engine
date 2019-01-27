package nl.arkenbout.geoffrey.arc.ecs.test;

import nl.arkenbout.geoffrey.arc.ecs.ComponentSystem;

public class NoDefaultConstructorTestComponentSystem extends ComponentSystem {

    public NoDefaultConstructorTestComponentSystem(String param) {
    }

    @Override
    public void update() {

    }
}
