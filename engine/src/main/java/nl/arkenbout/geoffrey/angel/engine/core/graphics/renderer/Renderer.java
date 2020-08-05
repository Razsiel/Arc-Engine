package nl.arkenbout.geoffrey.angel.engine.core.graphics.renderer;

import nl.arkenbout.geoffrey.angel.ecs.context.Context;
import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;

public interface Renderer {
    void render(Window window, Context activeContext, Camera camera);
}
