package nl.arkenbout.geoffrey.angel.engine.system;

import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;
import nl.arkenbout.geoffrey.angel.ecs.system.DualComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
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

    public void render(Camera mainCamera) {
        var matcher = new ComponentMatcher(RenderComponent.class, TransformComponent.class);
        List<ComponentMatch> components = getComponents(matcher);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Matrix4f projectionMatrix = Transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        Matrix4f viewMatrix = Transformation.getViewMatrix(mainCamera);

        for (var match : components) {
            var renderComponent = match.getComponent(RenderComponent.class);
            var transformComponent = match.getComponent(TransformComponent.class);

            var material = renderComponent.getMaterial();
            var mesh = renderComponent.getMesh();

            Matrix4f modelViewMatrix = Transformation.getModelViewMatrix(transformComponent, viewMatrix);

            mesh.render(material, projectionMatrix, modelViewMatrix);
        }

        // update the window render buffer
        window.update();
    }

}
