package nl.arkenbout.geoffrey.angel.engine.core.graphics.renderer;

import nl.arkenbout.geoffrey.angel.ecs.Scene;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;
import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Matrices;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class ForwardRenderer implements Renderer {
    private static final ComponentMatcher RENDER_MATCHER = new ComponentMatcher(TransformComponent.class, RenderComponent.class);

    @Override
    public void render(Window window, Scene scene, Camera camera) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getWidth(), window.getHeight());

        Matrix4f viewMatrix = Matrices.getViewMatrix(camera);
        Matrix4f projectionMatrix = Matrices.getProjectionMatrix(window, camera);

        var sceneRenderingComponents = scene.getRenderComponentMatches(RENDER_MATCHER);

        for (var match : sceneRenderingComponents) {
            var renderComponent = match.getComponent(RenderComponent.class);
            var transformComponent = match.getComponent(TransformComponent.class);

            var material = renderComponent.getMaterial();
            var mesh = renderComponent.getMesh();

            Matrix4f modelViewMatrix = Matrices.getModelViewMatrix(transformComponent, viewMatrix);

            try {
                mesh.render(material, projectionMatrix, modelViewMatrix, viewMatrix);
            } catch (IllegalStateException e) {
                throw new RuntimeException("Error rendering entity '" + match.getEntityId() + "'", e);
            }
        }

        window.update();
    }
}
