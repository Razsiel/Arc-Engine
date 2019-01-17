package nl.arkenbout.geoffrey.arc.ecs.test;

import nl.arkenbout.geoffrey.arc.ecs.System;

public class NoDefaultConstructorTestSystem extends System<TestComponent> {

    public NoDefaultConstructorTestSystem(String param) {
    }

    @Override
    public void update() {

    }
}
