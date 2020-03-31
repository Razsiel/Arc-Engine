package nl.arkenbout.geoffrey.game;

import nl.arkenbout.geoffrey.angel.ecs.*;
import nl.arkenbout.geoffrey.angel.engine.Game;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Material;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Texture;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.shader.FlatColouredShader;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.shader.TexturedShader;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.*;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyboardInput;
import nl.arkenbout.geoffrey.angel.engine.core.input.mouse.MouseInput;
import nl.arkenbout.geoffrey.angel.engine.util.MathUtils;
import nl.arkenbout.geoffrey.game.components.*;
import nl.arkenbout.geoffrey.game.systems.*;
import org.joml.Vector3f;
import org.lwjgl.util.Color;

public class TestGame implements Game {

    @Override
    public void init() throws Exception {
        var gameContext = GameContext.getInstance();
        var scene = new SceneContext("", gameContext);

        var systemRegistry = scene.getComponentSystemRegistery();
        systemRegistry.registerSystem(new ScalePingPongSystem());
        systemRegistry.registerSystem(new BounceSystem());
        systemRegistry.registerSystem(new RotatorSystem());

        var gridTexture = new Texture("/textures/grid.png");
        var texturedShader = new TexturedShader(gridTexture, Color.WHITE);
        var texturedCubeMaterial = new Material(texturedShader);
        var cubeMesh = PrimitiveMesh.createCube(1);
        var cubeRenderer = new RenderComponent(cubeMesh, texturedCubeMaterial);

        TransformComponent cubeTransform = new TransformComponent(Vector3u.up().mul(1.5f), Vector3u.up().mul(45), 1f);
        RotatorComponent rotatorComponent = new RotatorComponent(50f, Vector3u.up());
        ScalePingPongComponent scalePingPongComponent = new ScalePingPongComponent(1f, 0.3f, 1f);
        Entity cube = scene.createEntity(cubeTransform, cubeRenderer, rotatorComponent, scalePingPongComponent);
        System.out.println("eId = " + cube.getId());

        for (int i = 0; i < 5; i++) {
            var x = MathUtils.remap(i, 0, 4, -2.5f, 2.5f);
            var transform = new TransformComponent(new Vector3f(x, 0f, 0f), Vector3u.zero(), 1f, cubeTransform);
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

            var cubeEntity = scene.createEntity(transform, renderer, scalePingPong, bouncer, rotator);
            System.out.println("cubeId = " + cubeEntity.getId());
            System.out.println(color);
        }

        var planeMesh = PrimitiveMesh.createPlane(4, 4);
        var planeShader = new TexturedShader(gridTexture, Vector2u.one().mul(16), Color.WHITE);
        var planeMaterial = new Material(planeShader);
        var transform = new TransformComponent(Vector3u.zero(), Vector3u.zero(), 1f);
        var planeRenderer = new RenderComponent(planeMesh, planeMaterial);
        var plane = scene.createEntity(transform, planeRenderer);
        System.out.println("planeId = " + plane.getId());

        CameraControl cameraControl = new CameraControl();
        MouseInput.registerMouseListener(cameraControl);
        KeyboardInput.registerKeyboardListener(cameraControl);

//        var icoMesh = Icosahedron.create(0);
//        var icoRenderer = new RenderComponent(icoMesh, texturedCubeMaterial);
//        Entity icosahedron = gameContext.createEntity(TransformComponent.identity(), icoRenderer);
//        System.out.println("icosahedronId = " + icosahedron.getId());

        gameContext.setActiveSceneContext(scene);
    }

    @Override
    public void cleanup() {

    }
}
