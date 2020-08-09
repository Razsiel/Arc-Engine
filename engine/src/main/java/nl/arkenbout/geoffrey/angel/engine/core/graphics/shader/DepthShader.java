package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.ecs.match.EntityComponentMatch;
import nl.arkenbout.geoffrey.angel.engine.component.TransformComponent;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.Matrices;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.util.Lights;
import org.joml.Matrix4d;
import org.joml.Vector3d;

import java.util.List;
import java.util.Map;

public class DepthShader extends Shader {

    public DepthShader() throws Exception {
        super("depth");
        createUniform("modelLightViewMatrix");
        createUniform("orthoProjectionMatrix");
    }

    public void renderDepthMap(List<EntityComponentMatch> components) {
        bind();
        var directionalLight = Lights.getDirectionalLight();
        var lightDirection = directionalLight.getDirection();

        var lightAngleX = Math.toDegrees(Math.acos(lightDirection.z()));
        var lightAngleY = Math.toDegrees(Math.cos(lightDirection.x()));
        var lightAngleZ = 0d;
        var lightViewMatrix = Matrices.getLightViewMatrix(new Vector3d(lightDirection), new Vector3d(lightAngleX, lightAngleY, lightAngleZ));
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
    public void render(Matrix4d projectionMatrix, Matrix4d modelViewMatrix, Matrix4d viewMatrix) {

    }

    @Override
    public void postRender(int vboLastIndex) {

    }
}
