package nl.arkenbout.geoffrey.arc.engine.system;

import nl.arkenbout.geoffrey.arc.ecs.System;
import nl.arkenbout.geoffrey.arc.engine.Window;
import nl.arkenbout.geoffrey.arc.engine.component.RenderComponent;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.Shader;
import nl.arkenbout.geoffrey.arc.engine.util.Utils;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class RenderSystem extends System<RenderComponent> {

    private final Window window;

    private Shader shader;
    private float[] vertices;

    public RenderSystem(Window window) {
        this.window = window;
    }

    public void init() {
        vertices = new float[]{
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        try {
            shader = new Shader(Utils.loadResource("/vertex.vs"), Utils.loadResource("/fragment.fs"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        //
    }

    public void render() {
        var components = getComponents(RenderComponent.class);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for (var component : components) {
            // draw
            //var shader = component.getShader();
            //shader.render(component.getVertices()); // getMesh(), getVertices()
        }

        shader.render(vertices);

        // update the window render buffer
        window.update();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        shader.cleanup();
    }


}
