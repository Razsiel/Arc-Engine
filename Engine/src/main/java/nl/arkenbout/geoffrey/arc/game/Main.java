package nl.arkenbout.geoffrey.arc.game;

import nl.arkenbout.geoffrey.arc.engine.ArcEngine;
import nl.arkenbout.geoffrey.arc.engine.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new TestGame();
        ArcEngine engine = new ArcEngine("TestGame", 800, 600, true, game);
        engine.start();
    }
}
