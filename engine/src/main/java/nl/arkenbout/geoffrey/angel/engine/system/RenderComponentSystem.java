package nl.arkenbout.geoffrey.angel.engine.system;

import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;
import nl.arkenbout.geoffrey.angel.ecs.system.DualComponentSystem;
import nl.arkenbout.geoffrey.angel.engine.Window;
import nl.arkenbout.geoffrey.angel.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Camera;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Transformation;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting.ShadowMap;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.shader.*;
import org.joml.Matrix4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class RenderComponentSystem extends DualComponentSystem<RenderComponent, TransformComponent> {
    private final Window window;
    private ShadowMap shadowMap;
    private DepthShader depthShader;

    public RenderComponentSystem(Window window) {
        super(RenderComponent.class, TransformComponent.class);
        this.window = window;
    }

    @Override
    protected void doEachComponent(ComponentMatch match) {
        // ignore because of render method
    }

    public void render(Camera mainCamera) {
        var matcher = new ComponentMatcher(RenderComponent.class, TransformComponent.class);
        List<ComponentMatch> components = getComponents(matcher);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // render depth map
//        renderDepthMap(components);

        glViewport(0, 0, window.getWidth(), window.getHeight());

        Matrix4f viewMatrix = Transformation.getViewMatrix(mainCamera);
        Matrix4f projectionMatrix = Transformation.getProjectionMatrix(mainCamera.getFov(), window.getWidth(), window.getHeight(), mainCamera.getNear(), mainCamera.getFar());

        for (var match : components) {
            var renderComponent = match.getComponent(RenderComponent.class);
            var transformComponent = match.getComponent(TransformComponent.class);

            var material = renderComponent.getMaterial();
            var mesh = renderComponent.getMesh();

            Matrix4f modelViewMatrix = Transformation.getModelViewMatrix(transformComponent, viewMatrix);

            try {
                mesh.render(material, projectionMatrix, modelViewMatrix, viewMatrix);
            } catch (IllegalStateException e) {
                throw new RuntimeException("Error rendering entity \'" + match.getEntityId() + "\'", e);
            }
        }

        // update the window render buffer
        window.update();
    }

    //TODO: Move to a rendering class
    private void renderDepthMap(List<ComponentMatch> components) {
        glBindFramebuffer(GL_FRAMEBUFFER, shadowMap.getBufferId());
        glViewport(0, 0, ShadowMap.WIDTH, ShadowMap.HEIGHT);
        glClear(GL_DEPTH_BUFFER_BIT);

        depthShader.renderDepthMap(components);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void init() throws Exception {
        this.shadowMap = new ShadowMap();
        this.depthShader = new DepthShader();
    }
}