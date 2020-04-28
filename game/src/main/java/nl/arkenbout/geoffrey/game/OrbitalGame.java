package nl.arkenbout.geoffrey.game;

import nl.arkenbout.geoffrey.angel.ecs.GameContext;
import nl.arkenbout.geoffrey.angel.ecs.Scene;
import nl.arkenbout.geoffrey.angel.engine.Game;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Material;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.shader.FlatColouredShader;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.PrimitiveMesh;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import nl.arkenbout.geoffrey.angel.engine.options.VideoOptions;
import nl.arkenbout.geoffrey.angel.engine.options.WindowOptions;
import nl.arkenbout.geoffrey.game.components.RotatorComponent;
import nl.arkenbout.geoffrey.game.systems.RotatorSystem;
import org.lwjgl.util.Color;

public class OrbitalGame implements Game {
    @Override
    public void init() throws Exception {
        var gameContext = GameContext.getInstance();
        var scene = new Scene("");
        var systemRegistry = scene.getComponentSystemRegistery();

        systemRegistry.registerSystem(RotatorSystem.class);

        final var sunShader = new FlatColouredShader(Color.YELLOW);
        var sunMaterial = new Material(sunShader);

        var sunRenderer = new RenderComponent(PrimitiveMesh.createCube(2f), sunMaterial);
        var sunRotator = new RotatorComponent(90f, Vector3u.up());
        var sunTransform = new TransformComponent();

        scene.createEntity(sunRenderer, sunTransform, sunRotator);

        gameContext.setActiveScene(scene);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public WindowOptions getWindowOptions() {
        return WindowOptions.of()
                .title("Orbital")
                .width(640)
                .height(480)
                .create();
    }

    @Override
    public VideoOptions getVideoOptions() {
        return VideoOptions.of()
                .vSync(true)
                .create();
    }
}
