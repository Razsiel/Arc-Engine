package nl.arkenbout.geoffrey.arc.engine;

import nl.arkenbout.geoffrey.arc.ecs.GameContext;
import nl.arkenbout.geoffrey.arc.ecs.System;
import nl.arkenbout.geoffrey.arc.engine.core.GameTimer;
import nl.arkenbout.geoffrey.arc.engine.system.RenderSystem;

public class ArcEngine implements Runnable {

    public static final int TARGET_FPS = 60;
    public static final int TARGET_UPS = 30;

    private final Window window;
    private final Thread gameLoopThread;
    private final GameTimer timer;
    private final GameContext context;
    private Game game;

    private RenderSystem renderSystem;

    public ArcEngine(String windowTitle, int width, int height, boolean vSync, Game game) {
        this.game = game;
        //System.out.println("Hello LWJGL" + Version.getVersion() + "!");
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        timer = new GameTimer();
        context = new GameContext();
        window = new Window(windowTitle, width, height, vSync);
        renderSystem = context.registerSystem(new RenderSystem(window));
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
        timer.init();
        game.init();
        renderSystem.init();
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
        // game logic update
        var systems = context.getSystems();
        for (System system : systems) {
            system.update();
        }
    }

    private void cleanup() {
        renderSystem.cleanup();
    }
}
