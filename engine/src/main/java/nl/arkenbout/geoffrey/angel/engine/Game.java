package nl.arkenbout.geoffrey.angel.engine;

import nl.arkenbout.geoffrey.angel.engine.options.VideoOptions;
import nl.arkenbout.geoffrey.angel.engine.options.WindowOptions;

public interface Game {
    void init() throws Exception;
    void cleanup();

    WindowOptions getWindowOptions();
    VideoOptions getVideoOptions();
}
