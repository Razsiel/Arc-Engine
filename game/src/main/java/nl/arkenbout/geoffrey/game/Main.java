package nl.arkenbout.geoffrey.game;

import nl.arkenbout.geoffrey.angel.engine.ArcEngine;
import nl.arkenbout.geoffrey.angel.engine.Game;

public class Main {

    public static void main(String[] args) throws Exception {
        Game game = new TestGame();
        ArcEngine engine = new ArcEngine("TestGame", 800, 600, true, game);
        engine.start();
    }
}
