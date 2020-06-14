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
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyboardInput;
import nl.arkenbout.geoffrey.angel.engine.core.input.mouse.MouseInput;
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

        final var speed = 10;

        final var sunShader = new FlatColouredShader(Color.YELLOW);
        var sunMaterial = new Material(sunShader);
        var sunRenderer = new RenderComponent(PrimitiveMesh.createCube(2f), sunMaterial);
        var sunRotator = new RotatorComponent((360f / 24.4f) * speed, Vector3u.up());
        var sunTransform = TransformComponent.origin();

        var earthOrbitTransform = TransformComponent.origin();
        final var earthShader = new FlatColouredShader(Color.CYAN);
        var earthMaterial = new Material(earthShader);
        var earthRenderer = new RenderComponent(PrimitiveMesh.createCube(0.5f), earthMaterial);
        var earthRotator = new RotatorComponent(360f * speed, Vector3u.up());
        var earthTransform = TransformComponent.parented(earthOrbitTransform);
        earthTransform.setPosition(Vector3u.right().mul(3));

        scene.createEntity(sunRenderer, sunTransform, sunRotator);
        scene.createEntity(earthRenderer, earthTransform, earthRotator);

        CameraControl cameraControl = new CameraControl();
        MouseInput.registerMouseListener(cameraControl);
        KeyboardInput.registerKeyboardListener(cameraControl);

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
