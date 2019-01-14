package nl.arkenbout.geoffrey.arc.engine;

public interface Game {
    void init() throws Exception;

    void render(Window window);

    void update(float interval);
}
