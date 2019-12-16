package nl.arkenbout.geoffrey.angel.engine.core.graphics.lighting;

import nl.arkenbout.geoffrey.angel.engine.core.graphics.Texture;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL30.*;

public class ShadowMap {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 1024;
    private final Texture depthMap;
    private final int frameBufferObjectId;

    public ShadowMap() {
        frameBufferObjectId = glGenFramebuffers();

        this.depthMap = new Texture(WIDTH, HEIGHT, GL_DEPTH_COMPONENT);

        // attach texture to framebuffer object
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferObjectId);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMap.getId(), 0);
        // set only depth
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new IllegalStateException("Could not create framebuffer for shadowmapping");
        }

        // unbind
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public Texture getDepthMap() {
        return depthMap;
    }

    public int getBufferId() {
        return frameBufferObjectId;
    }

    public void cleanup() {
        glDeleteFramebuffers(frameBufferObjectId);
        depthMap.cleanup();
    }
}
