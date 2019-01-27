package nl.arkenbout.geoffrey.game;

import nl.arkenbout.geoffrey.arc.ecs.GameContext;
import nl.arkenbout.geoffrey.arc.engine.Game;
import nl.arkenbout.geoffrey.arc.engine.Window;
import nl.arkenbout.geoffrey.arc.engine.component.*;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.util.PrimitiveMesh;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.util.Shaders;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.util.Vector3u;
import nl.arkenbout.geoffrey.arc.engine.util.MathUtils;
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

        var mesh = PrimitiveMesh.createCube(1f);
        var shader = Shaders.defaultShader();
        var renderer = new RenderComponent(mesh, shader);

        for (int i = 0; i < 4; i++) {
            var offset = (i + 1f) * 10f;
            var x = MathUtils.remap(i, 0, 3, -2, 2);
            var transform = new TransformComponent(new Vector3f(x, 0, -4f), Vector3u.zero(), 1f);
            var scalePingPong = new ScalePingPongComponent(0.5f, 0.4f, 0.6f);
            var bouncer = new BounceComponent(-4f, 1.75f);

            RotatorComponent rotator;
            var rotateSpeed = 100f;
            switch (i) {
                case 0:
                    rotator = new RotatorComponent(rotateSpeed, new Vector3f(1, 0, 0));
                    break;
                case 1:
                    rotator = new RotatorComponent(rotateSpeed, new Vector3f(0, 1, 0));
                    break;
                case 2:
                    rotator = new RotatorComponent(rotateSpeed, new Vector3f(0, 0, 1));
                    break;
                case 3:
                    rotator = new RotatorComponent(rotateSpeed, Vector3u.one());
                    break;
                    default:
                        rotator = new RotatorComponent(rotateSpeed);
            }
            var entity = gameContext.createEntity(transform, renderer, scalePingPong, bouncer, rotator);
        }
    }

    @Override
    public void input(Window window) {

    }

    @Override
    public void cleanup() {

    }
}
