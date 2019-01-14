package nl.arkenbout.geoffrey.arc.game;

import nl.arkenbout.geoffrey.arc.engine.Window;

public class TestGame implements nl.arkenbout.geoffrey.arc.engine.Game {
    @Override
    public void init() throws Exception {

    }

    @Override
    public void render(Window window) {

    }

    @Override
    public void update(float interval) {
        System.out.println("Game update");
    }
}
