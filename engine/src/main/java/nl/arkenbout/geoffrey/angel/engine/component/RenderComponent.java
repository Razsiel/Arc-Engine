package nl.arkenbout.geoffrey.angel.engine.component;

import nl.arkenbout.geoffrey.angel.ecs.BaseComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Material;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Mesh;

public class RenderComponent extends BaseComponent {
    private Mesh mesh;
    private Material material;

    public RenderComponent(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
        mesh.prepare(material);
    }

    public Mesh getMesh() {
        return mesh;
    }

    @Override
    public void cleanup() {
        mesh.cleanup();
        material.cleanup();
    }

    public Material getMaterial() {
        return material;
    }
}
