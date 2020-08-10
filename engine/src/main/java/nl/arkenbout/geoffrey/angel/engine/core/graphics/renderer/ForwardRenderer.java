package nl.arkenbout.geoffrey.angel.engine.core.graphics.renderer;

import nl.arkenbout.geoffrey.angel.ecs.Component;
import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.context.Context;
import nl.arkenbout.geoffrey.angel.ecs.match.EntityComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.EntityComponentMatcher;
import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Matrices;
import org.joml.Matrix4d;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.lwjgl.opengl.GL11.*;

public class ForwardRenderer implements Renderer {
    private final EntityComponentMatcher renderMatcher;

    public ForwardRenderer() {
        renderMatcher = new EntityComponentMatcher(TransformComponent.class, RenderComponent.class);
    }

    @Override
    public void render(Window window, Context activeContext, Camera camera) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getWidth(), window.getHeight());

        var viewMatrix = Matrices.getViewMatrix(camera);
        var projectionMatrix = Matrices.getProjectionMatrix(window, camera);

        var entities = activeContext.getEntities();
        var matches = renderMatcher.match(entities, ifComponentsAreActive());

        matches.forEach(this.render(viewMatrix, projectionMatrix));

        window.update();
    }

    private Predicate<Entity> ifComponentsAreActive() {
        return entity -> entity.getComponents().stream()
                .anyMatch(Component::isEnabled);
    }

    private Consumer<EntityComponentMatch> render(Matrix4d viewMatrix, Matrix4d projectionMatrix) {
        return match -> {
            var renderComponent = match.getComponent(RenderComponent.class);
            var transformComponent = match.getComponent(TransformComponent.class);

            var material = renderComponent.getMaterial();
            var mesh = renderComponent.getMesh();

            var modelViewMatrix = Matrices.getModelViewMatrix(transformComponent, viewMatrix);

            try {
                mesh.render(material, projectionMatrix, modelViewMatrix, viewMatrix);
            } catch (IllegalStateException e) {
                throw new RuntimeException("Error rendering entity '" + match.getEntity().getId() + "'", e);
            }
        };
    }
}
