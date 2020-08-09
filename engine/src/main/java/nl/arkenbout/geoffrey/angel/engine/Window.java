package nl.arkenbout.geoffrey.angel.engine;

import nl.arkenbout.geoffrey.angel.engine.core.GameTimer;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.Key;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyModifier;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyboardInput;
import nl.arkenbout.geoffrey.angel.engine.core.input.keyboard.KeyboardListener;
import nl.arkenbout.geoffrey.angel.engine.options.VideoOptions;
import nl.arkenbout.geoffrey.angel.engine.options.WindowOptions;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements KeyboardListener {
    private final boolean vSync;
    private String windowTitle;
    private int width;
    private int height;
    private long windowHandle;
    private boolean resized;
    private double lastUpdateFps;
    private int fps;
    private boolean isMaximized;
    private boolean isRenderingWireframe;

    public Window(String windowTitle, int width, int height, boolean vSync) {
        this.windowTitle = windowTitle;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
    }

    public static Window fromWindowOptions(WindowOptions windowOptions, VideoOptions videoOptions) {
        return new Window(windowOptions.getTitle(), windowOptions.getWidth(), windowOptions.getHeight(), videoOptions.isVSync());
    }

    public void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        if (width == 0 || height == 0) {
            glfwWindowHint(GLFW_MAXIMIZED, GL_TRUE); // Maximizes the window on creation if either the width or height is 0
        }

        // Create the window
        windowHandle = glfwCreateWindow(width, height, windowTitle, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup resize callback
        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.resized = true;
        });

        glfwSetWindowMaximizeCallback(windowHandle, (window, maximized) -> {
            this.isMaximized = maximized;
        });

        // Setup a key callback to close the window on key ESCAPE released
        KeyboardInput.registerKeyboardListener(this);

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vidmode == null) {
            throw new IllegalStateException("Could not get the GLFW Video of the primary monitor");
        }
        // Center our window
        glfwSetWindowPos(
                windowHandle,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);

        if (isvSync()) {
            // enable v-sync
            glfwSwapInterval(1);
        }

        // Make the window visible
        glfwShowWindow(windowHandle);

        GL.createCapabilities();

        // Set the clear color
        GL11.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);

        // Enable face-culling
        glEnable(GL_CULL_FACE);
        glCullFace(GL_FRONT);

        // Enable Z-buffer
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDepthFunc(GL_LESS);

        System.out.println(glfwGetVersionString());
    }

    public boolean isvSync() {
        return vSync;
    }

    public void updateFps(GameTimer timer) {
        if (timer.getLastLoopTime() - lastUpdateFps > 1) {
            lastUpdateFps = timer.getLastLoopTime();
            // set fps in window
            glfwSetWindowTitle(windowHandle, windowTitle + " - " + fps + " FPS");
            fps = 0;
        }
        fps++;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    public void update() {
        if (this.resized) {
            glViewport(0, 0, width, height);
            resized = false;
        }
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public void cleanup() {
        GL.setCapabilities(null);
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    @Override
    public void onKeyDown(Key key, List<KeyModifier> modifiers) {

    }

    @Override
    public void onKeyUp(Key key, List<KeyModifier> modifiers) {
        if (key == Key.ESCAPE) {
            glfwSetWindowShouldClose(windowHandle, true);
        }
        if (key == Key.F11) {
            if (isMaximized) {
                glfwRestoreWindow(windowHandle);
            } else {
                glfwMaximizeWindow(windowHandle);
            }
        }
        if (key == Key.F9) {
            if (isRenderingWireframe) {
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                isRenderingWireframe = false;
            } else {
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                isRenderingWireframe = true;
            }
        }
    }

    @Override
    public void onKeys(List<Key> keys, List<KeyModifier> modifiers) {

    }
}
