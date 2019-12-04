package nl.arkenbout.geoffrey.angel.engine.core.graphics;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.system.MemoryUtil;

import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    private final int id;

    public Texture(String textureName) throws Exception {
        this(loadTexture(textureName));
    }

    private Texture(int id) {
        this.id = id;
    }

    private static int loadTexture(String fileName) throws Exception {
        InputStream textureStream = Class.forName(Texture.class.getName()).getResourceAsStream(fileName);
        PNGDecoder decoder = new PNGDecoder(textureStream);
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        buffer.flip();

        int width = decoder.getWidth();
        int height = decoder.getHeight();

        int textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);

        // how to unpack, each RGBA component is 1 byte
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        // set texture filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // upload texture data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glGenerateMipmap(GL_TEXTURE_2D);

        // release the buffer
        MemoryUtil.memFree(buffer);

        return textureId;
    }

    public int getId() {
        return id;
    }

    public void cleanup() {
        glDeleteTextures(id);
    }
}
