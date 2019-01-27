package nl.arkenbout.geoffrey.arc.engine.system;

import nl.arkenbout.geoffrey.arc.ecs.ComponentMatch;
import nl.arkenbout.geoffrey.arc.ecs.ComponentMatcher;
import nl.arkenbout.geoffrey.arc.ecs.ComponentSystem;
import nl.arkenbout.geoffrey.arc.engine.Window;
import nl.arkenbout.geoffrey.arc.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.arc.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.Transformation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class RenderComponentSystem extends ComponentSystem {

    private final Window window;

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;

    public RenderComponentSystem(Window window) {
        this.window = window;
    }

    @Override
    public void update() {
        //
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

    @Override
    public void cleanup() {
        super.cleanup();
    }


}
