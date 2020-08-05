package nl.arkenbout.geoffrey.angel.engine.system;

import nl.arkenbout.geoffrey.angel.ecs.Entity;
import nl.arkenbout.geoffrey.angel.ecs.match.EntityComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.system.DualComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Matrices;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting.ShadowMap;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.shader.DepthShader;
import org.joml.Matrix4f;

import java.util.Collection;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL11.*;

public class RenderComponentSystem extends DualComponentSystem<RenderComponent, TransformComponent> {
    private final Window window;
    private ShadowMap shadowMap;
    private DepthShader depthShader;

    private RenderComponentSystem(Window window) {
        super(RenderComponent.class, TransformComponent.class);
        this.window = window;
    }

    public static RenderComponentSystem forWindow(Window window) {
        return new RenderComponentSystem(window);
    }

    @Override
    protected void update(Entity entity, RenderComponent renderComponent, TransformComponent transformComponent) {
        // empty for other render class
    }

    public void render(Camera camera, Collection<Entity> entities) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glViewport(0, 0, window.getWidth(), window.getHeight());

        Matrix4f viewMatrix = Matrices.getViewMatrix(camera);
        Matrix4f projectionMatrix = Matrices.getProjectionMatrix(camera.getFov(), window.getWidth(), window.getHeight(), camera.getNear(), camera.getFar());

        matcher.match(entities).forEach(render(viewMatrix, projectionMatrix));

        // update the window render buffer
        window.update();
    }

    private Consumer<EntityComponentMatch> render(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        return match -> {
            var entity = match.getEntity();
            var renderComponent = match.getComponent(RenderComponent.class);
            var transformComponent = match.getComponent(TransformComponent.class);
            var material = renderComponent.getMaterial();
            var mesh = renderComponent.getMesh();

            Matrix4f modelViewMatrix = Matrices.getModelViewMatrix(transformComponent, viewMatrix);

            try {
                mesh.render(material, projectionMatrix, modelViewMatrix, viewMatrix);
            } catch (IllegalStateException e) {
                throw new RuntimeException("Error rendering entity \'" + entity.getId() + "\'", e);
            }
        };
    }

//    //TODO: Move to a rendering class
//    private void renderDepthMap(List<EntityComponentMatch> components) {
//        glBindFramebuffer(GL_FRAMEBUFFER, shadowMap.getBufferId());
//        glViewport(0, 0, ShadowMap.WIDTH, ShadowMap.HEIGHT);
//        glClear(GL_DEPTH_BUFFER_BIT);
//
//        depthShader.renderDepthMap(components);
//
//        glBindFramebuffer(GL_FRAMEBUFFER, 0);
//    }

//    public void init() throws Exception {
//        this.shadowMap = new ShadowMap();
//        this.depthShader = new DepthShader();
//    }
}