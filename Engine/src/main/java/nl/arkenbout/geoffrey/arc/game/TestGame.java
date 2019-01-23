package nl.arkenbout.geoffrey.arc.game;

import nl.arkenbout.geoffrey.arc.ecs.GameContext;
import nl.arkenbout.geoffrey.arc.engine.Window;
import nl.arkenbout.geoffrey.arc.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.arc.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.Shader;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.util.PrimitiveMesh;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.util.Vector3u;
import nl.arkenbout.geoffrey.arc.engine.util.Utils;
import nl.arkenbout.geoffrey.arc.game.components.BounceComponent;
import nl.arkenbout.geoffrey.arc.game.components.ScalePingPongComponent;
import nl.arkenbout.geoffrey.arc.game.systems.BounceSystem;
import nl.arkenbout.geoffrey.arc.game.systems.ScalePingPongSystem;
import org.joml.Vector3f;

public class TestGame implements nl.arkenbout.geoffrey.arc.engine.Game {
    @Override
    public void init() throws Exception {
        var gameContext = GameContext.getInstance();
        gameContext.registerSystem(new ScalePingPongSystem());
        gameContext.registerSystem(new BounceSystem());

        var mesh = PrimitiveMesh.createCube(1f);
        var shader = new Shader(Utils.loadResource("/vertex.vs"), Utils.loadResource("/fragment.fs"));
        var renderer = new RenderComponent(mesh, shader);

        for (int i = 0; i < 3; i++) {
            var offset = (i + 1f) * 10f;
            var transform = new TransformComponent(new Vector3f((i - 1f) * 2f, 0, -4f), Vector3u.zero(), 1f);
            var scalePingPong = new ScalePingPongComponent(0.1f, 0.25f, 0.5f);
            var bouncer = new BounceComponent(-4f, 1.75f);
            var entity = gameContext.createEntity(transform, renderer, scalePingPong, bouncer);
        }
    }

    @Override
    public void input(Window window) {

    }

    @Override
    public void cleanup() {

    }
}
