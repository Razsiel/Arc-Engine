package nl.arkenbout.geoffrey.angel.engine;

import lombok.extern.java.Log;
import nl.arkenbout.geoffrey.angel.ecs.context.GlobalContext;
import nl.arkenbout.geoffrey.angel.engine.core.GameTimer;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.renderer.ForwardRenderer;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.renderer.Renderer;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Cameras;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyboardInput;
import nl.arkenbout.geoffrey.angel.engine.core.input.mouse.MouseInput;
import nl.arkenbout.geoffrey.angel.engine.options.VideoOptions;
import nl.arkenbout.geoffrey.angel.engine.options.WindowOptions;
import org.joml.Vector3f;
import org.lwjgl.Version;

import java.lang.reflect.InvocationTargetException;

@Log
public class ArcEngine {

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

    private void run() {
        gameLoop();
    }

    private void init() throws Exception {
        window.init();
//        renderer.init();
        mouseInput.init();
        keyboardInput.init();
        game.init();
        if (Cameras.main() == null) {
            Camera mainCamera = new Camera(new Vector3f(0f, 1.5f, -5f), Vector3u.up().mul(180), 0.01f, 1000f, (float) Math.toRadians(60.0f));
            Cameras.addCamera(mainCamera);
            Cameras.setMainCamera(mainCamera);
        }
    }

    private void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        while (!window.shouldClose()) {

            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            // handle input
            input();

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

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void input() {
        // handle input
        mouseInput.input();
        keyboardInput.input();
    }

    private void update(float interval) {
        var activeContext = globalContext.getActiveContext();

        if (activeContext != null) {
            var globalSystems = globalContext.getComponentSystemRegistery().getComponentSystems();
            activeContext.update(globalSystems);
        } else {
            globalContext.update(null);
        }

    }

    private void cleanup() {
        globalContext.cleanup();
        mouseInput.cleanup(window);
        keyboardInput.cleanup(window);
        window.cleanup();
    }
}
