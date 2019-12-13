package nl.arkenbout.geoffrey.game;

import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.GameContext;
import nl.arkenbout.geoffrey.angel.engine.Game;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Material;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Texture;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.shader.*;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.*;
import nl.arkenbout.geoffrey.angel.engine.core.input.KeyboardInput;
import nl.arkenbout.geoffrey.angel.engine.core.input.MouseInput;
import nl.arkenbout.geoffrey.angel.engine.util.MathUtils;
import nl.arkenbout.geoffrey.game.components.*;
import nl.arkenbout.geoffrey.game.systems.*;
import org.joml.Vector3f;
import org.lwjgl.util.Color;

public class TestGame implements Game {

    @Override
    public void init() throws Exception {
        var gameContext = GameContext.getInstance();
        var systemRegistry = gameContext.getComponentSystemRegistery();
        systemRegistry.registerSystem(new ScalePingPongSystem());
        systemRegistry.registerSystem(new BounceSystem());
        systemRegistry.registerSystem(new RotatorSystem());

        var gridTexture = new Texture("/textures/grid.png");
        var texturedShader = new TexturedShader(gridTexture);
        var texturedCubeMaterial = new Material(texturedShader);
        var cubeMesh = PrimitiveMesh.createCube(1);

        var cubeRenderer = new RenderComponent(cubeMesh, texturedCubeMaterial);

        TransformComponent t = new TransformComponent(Vector3u.up().mul(1.5f), Vector3u.zero(), 1f);
        gameContext.createEntity(cubeRenderer);
        Entity e = gameContext.createEntity(t, cubeRenderer);
        System.out.println("eId = " + e.getId());

        for (int i = 0; i < 5; i++) {
            var x = MathUtils.remap(i, 0, 4, -2.5f, 2.5f);
            var transform = new TransformComponent(new Vector3f(x, 0f, 0f), Vector3u.zero(), 1f);
            var scalePingPong = new ScalePingPongComponent(1f, 0.3f, 0.7f);
            var bouncer = new BounceComponent(0f, 2f);

            int rotateX = i == 0 ? 1 : 0;
            int rotateY = i == 1 ? 1 : 0;
            int rotateZ = i == 2 ? 1 : 0;
            if (i == 3) {
                rotateX = 1;
                rotateY = 1;
                rotateZ = 1;
            }
            var axis = i > 3 ? Vector3u.zero() : i < 3 ? new Vector3f(rotateX, rotateY, rotateZ) : Vector3u.one();
            var rotator = new RotatorComponent(100f, axis);

            var color = new Color(255 * rotateX, 255 * rotateY, 255 * rotateZ);
            var shader = new FlatColouredShader(color);
            var material = new Material(shader);
            var renderer = new RenderComponent(cubeMesh, material);

            var cubeEntity = gameContext.createEntity(transform, renderer, scalePingPong, bouncer, rotator);
            System.out.println("cubeId = " + cubeEntity.getId());
        }

        var planeMesh = PrimitiveMesh.createPlane(4, 4);
        var planeShader = new TexturedShader(gridTexture, Vector2u.one().mul(16));
        var planeMaterial = new Material(planeShader);
        var transform = new TransformComponent(Vector3u.zero(), Vector3u.zero(), 1f);
        var planeRenderer = new RenderComponent(planeMesh, planeMaterial);
        var plane = gameContext.createEntity(transform, planeRenderer);
        System.out.println("planeId = " + plane.getId());

        CameraControl cameraControl = new CameraControl();
        MouseInput.registerMouseListener(cameraControl);
        KeyboardInput.registerKeyboardListener(cameraControl);
    }

    @Override
    public void cleanup() {

    }
}
