package nl.arkenbout.geoffrey.angel.engine;

import nl.arkenbout.geoffrey.angel.ecs.GameContext;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.core.GameTimer;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Cameras;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import nl.arkenbout.geoffrey.angel.engine.core.input.KeyboardInput;
import nl.arkenbout.geoffrey.angel.engine.core.input.MouseInput;
import nl.arkenbout.geoffrey.angel.engine.system.RenderComponentSystem;
import org.joml.Vector3f;
import org.lwjgl.Version;

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
        this.renderSystem = context.getComponentSystemRegistery().registerSystem(new RenderComponentSystem(window));
    }

    public void start() throws Exception {
        init();
        run();
    }

    private void run() {
        try {
            gameLoop();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void init() throws Exception {
        window.init();
        mouseInput.init(window);
        keyboardInput.init(window);
        game.init();
        if (Cameras.main() == null) {
            Camera mainCamera = new Camera(new Vector3f(0f, 1.5f, -5f), Vector3u.up().mul(180));
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
        var systems = context.getComponentSystemRegistery()
                .getComponentSystems();
        systems.stream()
                .parallel()
                .forEach(ComponentSystem::update);
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
