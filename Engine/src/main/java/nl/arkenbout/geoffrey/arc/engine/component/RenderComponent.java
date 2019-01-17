package nl.arkenbout.geoffrey.arc.engine.component;

import nl.arkenbout.geoffrey.arc.engine.core.graphics.Shader;

import java.awt.*;

public class RenderComponent extends Component implements nl.arkenbout.geoffrey.arc.ecs.Component {
    private Shader shader;

    public Shader getShader() {
        return shader;
    }
}
