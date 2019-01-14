package nl.arkenbout.geoffrey.arc.engine;

import nl.arkenbout.geoffrey.arc.engine.core.GameTimer;
import org.lwjgl.Version;

public class ArcEngine implements Runnable {

    public static final int TARGET_FPS = 60;
    public static final int TARGET_UPS = 30;

    private final Window window;
    private final Thread gameLoopThread;
    private final GameTimer timer;
    private Game game;

    public ArcEngine(String windowTitle, int width, int height, boolean vSync, Game game) {
        this.game = game;
        //System.out.println("Hello LWJGL" + Version.getVersion() + "!");
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync);
        timer = new GameTimer();
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
        }
    }

    private void init() throws Exception {
        window.init();
        timer.init();
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

            render();
        }
    }

    private void input() {
        // handle input
    }

    private void update(float interval) {
        // game logic update
        game.update(interval);
    }

    private void render() {
        game.render(window);
        window.update();
    }
}
