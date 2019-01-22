package nl.arkenbout.geoffrey.arc.game;

import nl.arkenbout.geoffrey.arc.ecs.GameContext;
import nl.arkenbout.geoffrey.arc.engine.Window;
import nl.arkenbout.geoffrey.arc.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.arc.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.Mesh;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.Shader;
import nl.arkenbout.geoffrey.arc.engine.util.Utils;
import org.joml.Vector3f;

public class TestGame implements nl.arkenbout.geoffrey.arc.engine.Game {
    @Override
    public void init() throws Exception {
        var gameContext = GameContext.getInstance();
        var entity = gameContext.createEntity();
        var vertices = new float[]{
                -0.5f,  0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f,  0.5f, 0.5f,
        };
        var indices = new int[]{
                0, 1, 3,
                3, 1, 2
        };
        var colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f
        };
        var mesh = new Mesh(vertices, indices, colours);
        var shader = new Shader(Utils.loadResource("/vertex.vs"), Utils.loadResource("/fragment.fs"));
        var renderComponent = new RenderComponent(mesh, shader);
        var transform = new TransformComponent();
        transform.setPosition(new Vector3f(0, 0, -2));
        entity.addComponent(renderComponent);
        entity.addComponent(transform);
    }

    @Override
    public void input(Window window) {

    }

    @Override
    public void cleanup() {

    }
}
