package nl.arkenbout.geoffrey.game;

import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.GameContext;
import nl.arkenbout.geoffrey.angel.engine.Game;
import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.component.*;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.PrimitiveMesh;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Shaders;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Vector3u;
import nl.arkenbout.geoffrey.angel.engine.util.MathUtils;
import nl.arkenbout.geoffrey.game.components.*;
import nl.arkenbout.geoffrey.game.systems.*;
import org.joml.Vector3f;

public class TestGame implements Game {
    @Override
    public void init() throws Exception {
        var gameContext = GameContext.getInstance();
        var systemRegistry = gameContext.getComponentSystemRegistery();
        systemRegistry.registerSystem(new ScalePingPongSystem());
        systemRegistry.registerSystem(new BounceSystem());
        systemRegistry.registerSystem(new RotatorSystem());

        var shader = Shaders.defaultShader();

        var mesh = PrimitiveMesh.createCube(1f);
        var cubeRenderer = new RenderComponent(mesh, shader);

        for (int i = 0; i < 5; i++) {
            var x = MathUtils.remap(i, 0, 4, -2.5f, 2.5f);
            var transform = new TransformComponent(new Vector3f(x, 0, -5f), Vector3u.zero(), 1f);
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
        var transform = new TransformComponent(new Vector3f(-0.1f, -0.5f, -6f), Vector3u.zero(), 1f);
        var planeRenderer = new RenderComponent(planeMesh, shader);
        var plane = gameContext.createEntity(transform, planeRenderer);
        System.out.println("planeId = " + plane.getId());
    }

    @Override
    public void input(Window window) {

    }

    @Override
    public void cleanup() {

    }
}
