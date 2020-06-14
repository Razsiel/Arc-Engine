package nl.arkenbout.geoffrey.angel.engine.core.graphics.renderer;

import nl.arkenbout.geoffrey.angel.ecs.Scene;
import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;

public interface Renderer {
    void render(Window window, Scene scene, Camera camera);
}
