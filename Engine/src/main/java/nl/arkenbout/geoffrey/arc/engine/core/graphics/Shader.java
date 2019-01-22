package nl.arkenbout.geoffrey.arc.engine.core.graphics;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Shader {

    private final int programId;
    private final int vertexShaderId;
    private final int fragmentShaderId;


    public Shader(String vertexShaderCode, String fragmentShaderCode) throws Exception {
        if (vertexShaderCode == null || vertexShaderCode.isEmpty())
            throw new IllegalArgumentException("Vertex shader code must be provided");

        if (fragmentShaderCode == null || fragmentShaderCode.isEmpty())
            throw new IllegalArgumentException("Fragment shader code must be provided");

        programId = glCreateProgram();

        if (programId == 0) {
            throw new RuntimeException("Could not create shader");
        }

        vertexShaderId = createShader(vertexShaderCode, GL_VERTEX_SHADER);
        fragmentShaderId = createShader(fragmentShaderCode, GL_FRAGMENT_SHADER);

        this.link();
    }

    protected int createShader(String shaderCode, int shaderType) throws Exception {
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

    public void render(Mesh mesh) {
        this.bind();

        // Bind to the VAO
        glBindVertexArray(mesh.getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Draw the vertices
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        this.unbind();
    }

    public void cleanup() {
        this.unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}
