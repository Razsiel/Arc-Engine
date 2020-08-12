package nl.arkenbout.geoffrey.angel.engine;

import lombok.extern.java.Log;
import nl.arkenbout.geoffrey.angel.ecs.context.GlobalContext;
import nl.arkenbout.geoffrey.angel.engine.core.GameTimer;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.renderer.ForwardRenderer;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.renderer.Renderer;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Cameras;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyboardInput;
import nl.arkenbout.geoffrey.angel.engine.core.input.mouse.MouseInput;
import nl.arkenbout.geoffrey.angel.engine.options.VideoOptions;
import nl.arkenbout.geoffrey.angel.engine.options.WindowOptions;
import nl.arkenbout.geoffrey.angel.engine.util.Cleanup;
import org.lwjgl.Version;

import java.lang.reflect.InvocationTargetException;

@Log
public class ArcEngine implements Cleanup {

    private static final int TARGET_FPS = 60;
    private static final int TARGET_UPS = 30;

    private final Window window;
    private final GameTimer timer;
    private final GlobalContext globalContext;
    private final MouseInput mouseInput;
    private final KeyboardInput keyboardInput;
    private final Game game;
    private final Renderer renderer;

    private ArcEngine(Window window, Game game) {
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");
        this.window = window;
        this.game = game;
        this.mouseInput = MouseInput.forWindow(window);
        this.keyboardInput = KeyboardInput.forWindow(window);
        this.timer = GameTimer.getInstance();
        this.globalContext = GlobalContext.getInstance();
        this.renderer = new ForwardRenderer();
    }

    public static void start(Class<? extends Game> gameClass) {
        Game game;
        try {
            game = gameClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("The passed in Game class (" + gameClass.getSimpleName() + ") does not have a parameterless constructor");
        }

        WindowOptions windowOptions = game.getWindowOptions();
        VideoOptions videoOptions = game.getVideoOptions();
        Window window = Window.fromWindowOptions(windowOptions, videoOptions);

        ArcEngine engine = new ArcEngine(window, game);
        engine.start();
    }

    private void start() {
        try {
            init();
            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void init() throws Exception {
        window.init();
        mouseInput.init();
        keyboardInput.init();
        game.init();
        if (Cameras.main() == null) {
            Cameras.setMainCamera(Cameras.defaultCamera());
        }
    }

    private void run() {
        gameLoop();
    }

    @Override
    public void cleanup() {
        globalContext.cleanup();
        mouseInput.cleanup();
        keyboardInput.cleanup();
        window.cleanup();
    }

    private void gameLoop() {
        var elapsedTime = 0d;
        var accumulator = 0d;
        var interval = 1d / TARGET_UPS;

        while (!window.shouldClose()) {

            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            // handle input
            updateInput();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            window.updateFps(timer);

            renderer.render(window, globalContext.getActiveContext(), Cameras.main());

            if (!window.isvSync()) {
                sync();
            }
        }
    }

    private void updateInput() {
        // handle input
        mouseInput.update();
        keyboardInput.update();
    }

    private void update(double interval) {
        var activeContext = globalContext.getActiveContext();

        if (activeContext != null) {
            var globalSystems = globalContext.getComponentSystemRegistery().getComponentSystems();
            activeContext.update(globalSystems);
        } else {
            globalContext.update(null);
        }

    }

    private void sync() {
        var loopSlot = 1d / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
