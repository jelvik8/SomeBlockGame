package org.main;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private int width, height;
    private String title;
    private long window;
    public Input input;
    private float backgroundR, backgroundG, backgroundB;
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private boolean isFullscreen;
    private int[] windowPosX = new int[1], windowPosY = new int[1];

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            System.err.println("ERROR: GLFW wasn't initialised!");
            return;
        }

        input = new Input();
        window =
                GLFW.glfwCreateWindow(
                        width, height, title, isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);

        if (window == 0) {
            System.err.println("ERROR: window wasn't created!");
            return;
        }

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        windowPosX[0] = (vidMode.width() - width) / 2;
        windowPosY[0] = (vidMode.height() - height) / 2;
        GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        createCallbacks();

        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(1);
    }

    private void createCallbacks() {
        sizeCallback =
                new GLFWWindowSizeCallback() {
                    @Override
                    public void invoke(long window, int w, int h) {
                        width = w;
                        height = h;
                        isResized = true;
                    }
                };

        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(window, input.getScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }

    public void update() {
        if (isResized) {
            GL11.glViewport(0, 0, width, height);
            isResized = false;
        }

        GL11.glClearColor(backgroundR, backgroundG, backgroundB, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void free() {
        input.free();
        sizeCallback.free();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public void setBackgroundColour(float r, float g, float b) {
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
        isResized = true;

        if (isFullscreen) {
            GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        } else {
            GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
        }
    }
}
