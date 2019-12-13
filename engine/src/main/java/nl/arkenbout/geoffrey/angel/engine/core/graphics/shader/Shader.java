package nl.arkenbout.geoffrey.angel.engine.core.graphics.shader;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.gl.VboType;
import nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting.DirectionalLight;
import nl.arkenbout.geoffrey.angel.engine.util.Utils;
import org.joml.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.Color;

import java.util.*;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

    private final int programId;
    private final int vertexShaderId;
    private final int fragmentShaderId;

    private final Map<String, Integer> uniforms;
    protected Map<String, Object> properties = new HashMap<>();

    public Shader(String vertexShaderCode, String fragmentShaderCode) throws Exception {
        if (vertexShaderCode == null || vertexShaderCode.isEmpty())
            throw new IllegalArgumentException("Vertex shader code must be provided");

        if (fragmentShaderCode == null || fragmentShaderCode.isEmpty())
            throw new IllegalArgumentException("Fragment shader code must be provided");

        programId = glCreateProgram();

        uniforms = new HashMap<>();

        if (programId == 0) {
            throw new RuntimeException("Could not create shader");
        }

        vertexShaderId = createShader(vertexShaderCode, GL_VERTEX_SHADER);
        fragmentShaderId = createShader(fragmentShaderCode, GL_FRAGMENT_SHADER);

        this.link();

        createUniform("projectionMatrix");
        createUniform("modelViewMatrix");
    }

    public Shader(String name) throws Exception {
        this(
                Utils.loadResource(String.format("/shaders/%s/vertex.vs", name)),
                Utils.loadResource(String.format("/shaders/%s/fragment.fs", name))
        );
    }

    private int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0)
            throw new Exception("Error creating shader. Type: " + shaderType);

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            String shaderTypeName = shaderType == GL_VERTEX_SHADER ? "VERTEX" : shaderType == GL_FRAGMENT_SHADER ? "FRAGMENT" : "UNKNOWN";
            throw new Exception("Error compiling " + shaderTypeName + " Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void createDirectionalLightUniform(String uniformName) throws Exception {
        createUniform(uniformName + ".color");
        createUniform(uniformName + ".direction");
        createUniform(uniformName + ".intensity");
    }

    private void link() throws Exception {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    protected void bind() {
        glUseProgram(programId);
    }

    protected void unbind() {
        glUseProgram(0);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (var stack = MemoryStack.stackPush()) {
            var fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.x(), value.y(), value.z());
    }

    public void setUniform(String uniformName, Vector2f value) {
        glUniform2f(uniforms.get(uniformName), value.x(), value.y());
    }

    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Color color) {
        Integer colorLocation = uniforms.get(uniformName);
        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        float alpha = color.getAlpha() / 255f;
        glUniform4f(colorLocation, red, green, blue, alpha);
    }

    public void setUniform(String uniformName, DirectionalLight directionalLight) {
        setUniform(uniformName + ".color", directionalLight.getColor());
        setUniform(uniformName + ".direction", directionalLight.getDirection());
        setUniform(uniformName + ".intensity", directionalLight.getIntensity());
    }

    protected void render(Matrix4f projectionMatrix, Matrix4f modelViewMatrix, Runnable shaderRenderer) {
        // set uniform data
        setUniform("projectionMatrix", projectionMatrix);
        setUniform("modelViewMatrix", modelViewMatrix);

        shaderRenderer.run();
    }

    public void cleanup() {
        this.unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }

    public <T> void setProperty(String propertyName, T value) {
        this.properties.put(propertyName, value);
    }

    <T> T getProperty(String propertyName) {
        return (T) properties.get(propertyName);
    }

    public abstract Map<VboType, Integer> prepareVertexBufferObjects(int vboIdIndex);

    public abstract void preRender(int vboLastIndex);
    public abstract void render(Matrix4f projectionMatrix, Matrix4f modelViewMatrix, Matrix4f viewMatrix);
    public abstract void postRender(int vboLastIndex);
}

