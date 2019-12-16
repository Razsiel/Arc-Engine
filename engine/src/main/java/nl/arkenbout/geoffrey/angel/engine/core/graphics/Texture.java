package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.joml.Vector2i;

import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    private final int id;
    private final int height;
    private final int width;

    public Texture(String textureName) throws Exception {
        this.id = glGenTextures();
        Vector2i size = loadTexture(textureName, id);
        width = size.x();
        height = size.y();
    }

    public Texture(int width, int height, int pixelFormat) {
        this.id = glGenTextures();
        this.width = width;
        this.height = height;
        glBindTexture(GL_TEXTURE_2D, this.id);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, this.width, this.height, 0, pixelFormat, GL_FLOAT, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    private Vector2i loadTexture(String fileName, int textureId) throws Exception {
        InputStream textureStream = Class.forName(Texture.class.getName()).getResourceAsStream(fileName);
        PNGDecoder decoder = new PNGDecoder(textureStream);
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        buffer.flip();

        int width = decoder.getWidth();
        int height = decoder.getHeight();

        glBindTexture(GL_TEXTURE_2D, textureId);

        // how to unpack, each RGBA component is 1 byte
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        // set texture filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // upload texture data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        // speaks for itself
        glGenerateMipmap(GL_TEXTURE_2D);

        return new Vector2i(width, height);
    }

    public int getId() {
        return id;
    }

    public void cleanup() {
        glDeleteTextures(id);
    }
}
