package nl.arkenbout.geoffrey.angel.engine;

import nl.arkenbout.geoffrey.angel.ecs.GameContext;
import nl.arkenbout.geoffrey.angel.ecs.SceneContext;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.core.GameTimer;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Cameras;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyboardInput;
import nl.arkenbout.geoffrey.angel.engine.core.input.mouse.MouseInput;
import nl.arkenbout.geoffrey.angel.engine.system.RenderComponentSystem;
import org.joml.Vector3f;
import org.lwjgl.Version;

import java.util.stream.Stream;

public class ArcEngine {

    private static final int TARGET_FPS = 60;
    private static final int TARGET_UPS = 30;

    private final Window window;
    private final GameTimer timer;
    private final GameContext context;
    private final MouseInput mouseInput;
    private final KeyboardInput keyboardInput;
    private final Game game;

    private final RenderComponentSystem renderSystem;

    public ArcEngine(String windowTitle, int width, int height, boolean vSync, Game game) {
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");
        this.game = game;
        this.mouseInput = new MouseInput();
        this.keyboardInput = new KeyboardInput();
        this.timer = GameTimer.getInstance();
        this.context = GameContext.getInstance();
        this.window = new Window(windowTitle, width, height, vSync);
        this.renderSystem = new RenderComponentSystem(window);
        context.getComponentSystemRegistery().registerSystem(renderSystem);
    }

    public void start() {
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
        renderSystem.init();
        mouseInput.init(window);
        keyboardInput.init(window);
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

            renderSystem.render(Cameras.main());

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
            } catch (InterruptedException e) {
            }
        }
    }

    private void input() {
        // handle input
        mouseInput.input();
        keyboardInput.input();
    }

    private void update(float interval) {
        var globalSystems = context.getComponentSystemRegistery()
                .getComponentSystems();
        SceneContext activeSceneContext = context.getActiveSceneContext();

        if (activeSceneContext != null) {
            this.renderSystem.updateActiveScene(activeSceneContext);
            var sceneSystems = activeSceneContext.getComponentSystemRegistery()
                    .getComponentSystems();

            Stream.concat(globalSystems.stream(), sceneSystems.stream())
                    .parallel()
                    .forEach(ComponentSystem::update);
        } else {
            globalSystems.stream()
                    .parallel()
                    .forEach(ComponentSystem::update);
        }

    }

    private void cleanup() {
        context.getComponentSystemRegistery()
                .getComponentSystems()
                .stream()
                .parallel()
                .forEach(ComponentSystem::cleanup);
        mouseInput.cleanup(window);
        keyboardInput.cleanup(window);
        window.cleanup();
    }
}
