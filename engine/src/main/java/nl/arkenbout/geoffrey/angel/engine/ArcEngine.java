package nl.arkenbout.geoffrey.angel.engine;

import nl.arkenbout.geoffrey.angel.ecs.GameContext;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.core.GameTimer;
import nl.arkenbout.geoffrey.angel.engine.system.RenderComponentSystem;
import org.lwjgl.Version;

public class ArcEngine implements Runnable {

    public static final int TARGET_FPS = 60;
    public static final int TARGET_UPS = 30;

    private final Window window;
    private final Thread gameLoopThread;
    private final GameTimer timer;
    private final GameContext context;
    private Game game;

    private RenderComponentSystem renderSystem;

    public ArcEngine(String windowTitle, int width, int height, boolean vSync, Game game) {
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");
        this.game = game;
        this.gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        this.timer = GameTimer.getInstance();
        this.context = GameContext.getInstance();
        this.window = new Window(windowTitle, width, height, vSync);
        this.renderSystem = context.getComponentSystemRegistery().registerSystem(new RenderComponentSystem(window));
    }

    public void start() {
        gameLoopThread.start();
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void init() throws Exception {
        window.init();
        game.init();
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

            renderSystem.render();
        }
    }

    private void input() {
        // handle input
        game.input(window);
    }

    private void update(float interval) {
        // nl.arkenbout.geoffrey.arc.game logic update
        var systems = context.getComponentSystemRegistery().getComponentSystems();
        for (ComponentSystem componentSystem : systems) {
            componentSystem.update();
        }
    }

    private void cleanup() {
        renderSystem.cleanup();
    }
}
