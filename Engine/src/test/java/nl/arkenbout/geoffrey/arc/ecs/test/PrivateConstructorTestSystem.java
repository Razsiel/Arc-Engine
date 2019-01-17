package nl.arkenbout.geoffrey.arc.ecs.test;

import nl.arkenbout.geoffrey.arc.ecs.System;

public class PrivateConstructorTestSystem extends System<TestComponent> {

    private PrivateConstructorTestSystem() {
    }

    @Override
    public void update() {

    }
}
