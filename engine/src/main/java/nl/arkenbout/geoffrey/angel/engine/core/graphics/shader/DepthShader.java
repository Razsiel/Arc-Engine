package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Matrices;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting.DirectionalLight;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Lights;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public class DepthShader extends Shader {

    public DepthShader() throws Exception {
        super("depth");
        createUniform("modelLightViewMatrix");
        createUniform("orthoProjectionMatrix");
    }

    public void renderDepthMap(List<ComponentMatch> components) {
        bind();
        DirectionalLight directionalLight = Lights.getDirectionalLight();
        Vector3f lightDirection = directionalLight.getDirection();

        float lightAngleX = (float) Math.toDegrees(Math.acos(lightDirection.z()));
        float lightAngleY = (float) Math.toDegrees(Math.cos(lightDirection.x()));
        float lightAngleZ = 0;
        var lightViewMatrix = Matrices.getLightViewMatrix(new Vector3f(lightDirection), new Vector3f(lightAngleX, lightAngleY, lightAngleZ));
        var orthoProjectionMatrix = Matrices.getOrthoProjectionMatrix(-10f, 10f, -10f, 10f, 0.01f, 2000f);

        setUniform("orthoProjectionMatrix", orthoProjectionMatrix);
        for (var componentMatch : components) {
            var transform = componentMatch.getComponent(TransformComponent.class);

            var modelLightViewMatrix = Matrices.getModelViewMatrix(transform, lightViewMatrix);
            setUniform("modelLightViewMatrix", modelLightViewMatrix);
        }

        unbind();
    }

    // IGNORE
    @Override
    public Map<VboType, Integer> prepareVertexBufferObjects(int vboIdIndex) {
        return null;
    }

    @Override
    public void preRender(int vboLastIndex) {

    }

    @Override
    public void render(Matrix4f projectionMatrix, Matrix4f modelViewMatrix, Matrix4f viewMatrix) {

    }

    @Override
    public void postRender(int vboLastIndex) {

    }
}
