package nl.arkenbout.geoffrey.arc.engine.core;

public class GameTimer {

    private double lastLoopTime;
    private double startTime;

    private static GameTimer instance;

    public static GameTimer getInstance() {
        if (instance == null) {
            instance = new GameTimer();
        }
        return instance;
    }

    private GameTimer() {
        lastLoopTime = getTime();
        startTime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return elapsedTime;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }

    public double getTimeSinceStart() {
        return getTime() - startTime;
    }
}
