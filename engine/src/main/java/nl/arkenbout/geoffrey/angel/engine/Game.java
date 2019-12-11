package nl.arkenbout.geoffrey.angel.engine;

import nl.arkenbout.geoffrey.angel.engine.core.input.MouseInput;

public interface Game {
    void init(MouseInput mouse) throws Exception;

    void input(Window window);

    void cleanup();
}
