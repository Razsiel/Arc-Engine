package nl.arkenbout.geoffrey.game;

import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.GameContext;
import nl.arkenbout.geoffrey.angel.engine.Game;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Material;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Texture;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.shader.FlatColouredShader;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.shader.TexturedShader;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.PrimitiveMesh;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
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
        var mesh = PrimitiveMesh.createCube(1);

        var cubeRenderer = new RenderComponent(mesh, texturedCubeMaterial);

        TransformComponent t = new TransformComponent(Vector3u.up().mul(2f), Vector3u.zero(), 1f);
        gameContext.createEntity(cubeRenderer);
        Entity e = gameContext.createEntity(t, cubeRenderer);
        System.out.println("eId = " + e.getId());

        for (int i = 0; i < 5; i++) {
            var x = MathUtils.remap(i, 0, 4, -2.5f, 2.5f);
            var transform = new TransformComponent(new Vector3f(x, 0f, 0f), Vector3u.zero(), 1f);
            var scalePingPong = new ScalePingPongComponent(1f, 0.3f, 0.7f);
            var bouncer = new BounceComponent(-3f, 2f);

            int rotateX = i == 0 ? 1 : 0;
            int rotateY = i == 1 ? 1 : 0;
            int rotateZ = i == 2 ? 1 : 0;
            Vector3f axis = i > 3 ? Vector3u.zero() : i < 3 ? new Vector3f(rotateX, rotateY, rotateZ) : Vector3u.one();
            RotatorComponent rotator = new RotatorComponent(100f, axis);
            var cube = gameContext.createEntity(transform, cubeRenderer, scalePingPong, bouncer, rotator);
            System.out.println("cubeId = " + cube.getId());
        }

        var planeMesh = PrimitiveMesh.createPlane(4, 4);
        var vertexColoredShader = new FlatColouredShader(Color.DKGREY);
        var planeMaterial = new Material(vertexColoredShader);

        var transform = new TransformComponent(new Vector3f(0f, -0.5f, 0f), Vector3u.zero(), 1f);
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
