package nl.arkenbout.geoffrey.arc.engine.component;

import nl.arkenbout.geoffrey.arc.ecs.Component;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.Mesh;
import nl.arkenbout.geoffrey.arc.engine.core.graphics.Shader;

public class RenderComponent implements Component {
    private Mesh mesh;
    private Shader shader;

    public RenderComponent(Mesh mesh, Shader shader) {
        this.mesh = mesh;
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }

    public Mesh getMesh() {
        return mesh;
    }

    @Override
    public void cleanup() {
        mesh.cleanup();
        shader.cleanup();
    }
}
