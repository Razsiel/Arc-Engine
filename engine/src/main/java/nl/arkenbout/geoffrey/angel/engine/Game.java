package nl.arkenbout.geoffrey.angel.engine;

public interface Game {
    void init() throws Exception;

    void input(Window window);

    void cleanup();
}
