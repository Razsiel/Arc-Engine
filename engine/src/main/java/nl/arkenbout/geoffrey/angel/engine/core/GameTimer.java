package nl.arkenbout.geoffrey.angel.engine.core;

public class GameTimer {

    private static GameTimer instance;
    private double lastLoopTime;
    private double startTime;

    private GameTimer() {
        lastLoopTime = getTime();
        startTime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    public static GameTimer getInstance() {
        if (instance == null) {
            instance = new GameTimer();
        }
        return instance;
    }

    public double getElapsedTime() {
        var time = getTime();
        var elapsedTime = time - lastLoopTime;
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
