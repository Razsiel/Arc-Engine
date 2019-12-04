package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Shader {

    private final int programId;
    private final int vertexShaderId;
    private final int fragmentShaderId;

    private final Map<String, Integer> uniforms;

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

    private int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0)
            throw new Exception("Error creating shader. Type: " + shaderType);

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0)
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));

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

    private void bind() {
        glUseProgram(programId);
    }

    private void unbind() {
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

    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void render(Mesh mesh, Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        if (mesh.hasTexture()) {
            try {
                createUniform("texture_sampler");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.bind();

        setUniform("projectionMatrix", projectionMatrix);
        setUniform("modelViewMatrix", modelViewMatrix);

        mesh.render(this);

        this.unbind();
    }

    public void cleanup() {
        this.unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}
