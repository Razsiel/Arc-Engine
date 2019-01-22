package nl.arkenbout.geoffrey.arc.engine.system;

import nl.arkenbout.geoffrey.arc.ecs.ComponentMatch;
import nl.arkenbout.geoffrey.arc.ecs.ComponentMatcher;
import nl.arkenbout.geoffrey.arc.ecs.ComponentSystem;
import nl.arkenbout.geoffrey.arc.engine.Window;
import nl.arkenbout.geoffrey.arc.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.arc.engine.component.TransformComponent;

import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class RenderComponentSystem extends ComponentSystem {

    private final Window window;

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

        for (var match : components) {
            var renderComponent = match.getComponent(RenderComponent.class);
            var transformComponent = match.getComponent(TransformComponent.class);

            var shader = renderComponent.getShader();
            var mesh = renderComponent.getMesh();
            shader.render(mesh);
        }

        // update the window render buffer
        window.update();
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }


}
