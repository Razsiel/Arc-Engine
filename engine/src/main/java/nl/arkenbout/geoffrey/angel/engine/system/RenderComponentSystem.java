package nl.arkenbout.geoffrey.angel.engine.system;

import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;
import nl.arkenbout.geoffrey.angel.ecs.system.DualComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Transformation;
import org.joml.Matrix4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class RenderComponentSystem extends DualComponentSystem<RenderComponent, TransformComponent> {

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    private final Window window;

    public RenderComponentSystem(Window window) {
        super(RenderComponent.class, TransformComponent.class);
        this.window = window;
    }

    @Override
    protected void doEachComponent(ComponentMatch match) {
        // ignore because of render
    }

    public void render() {
        var matcher = new ComponentMatcher(RenderComponent.class, TransformComponent.class);
        List<ComponentMatch> components = getComponents(matcher);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Matrix4f projectionMatrix = Transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);

        for (var match : components) {
            var renderComponent = match.getComponent(RenderComponent.class);
            var transform = match.getComponent(TransformComponent.class);

            var shader = renderComponent.getShader();
            var mesh = renderComponent.getMesh();

            var worldMatrix = Transformation.getWorldMatrix(transform.getPosition(), transform.getRotation(), transform.getScale());

            shader.render(mesh, projectionMatrix, worldMatrix);
        }

        // update the window render buffer
        window.update();
    }

}
