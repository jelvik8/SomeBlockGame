package org.main;

import org.lwjgl.glfw.GLFW;

public class Main implements Runnable {
    public Thread game;
    public Window window;
    public final int WIDTH = 1280, HEIGHT = 760;

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        window = new Window(WIDTH, HEIGHT, "Minecraft Clone");
        window.setBackgroundColour(0, 0.5f, 1.0f);
        // window.setFullscreen(true);
        window.create();
    }

    public void run() {
        init();

        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
                window.setFullscreen(!window.isFullscreen());
            }
        }
        window.free();
    }

    // Main loop
    private void update() {
        window.update();

        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
        }
    }

    private void render() {
        window.swapBuffers();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
