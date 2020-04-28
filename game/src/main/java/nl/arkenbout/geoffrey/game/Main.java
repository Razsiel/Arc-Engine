package nl.arkenbout.geoffrey.game;

import nl.arkenbout.geoffrey.angel.engine.ArcEngine;
import nl.arkenbout.geoffrey.angel.engine.Game;

public class Main {

    public static void main(String[] args) throws Exception {
        ArcEngine.start(OrbitalGame.class);
    }
}
