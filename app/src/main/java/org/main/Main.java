package org.main;

import org.engine.graphics.Mesh;
import org.engine.graphics.Renderer;
import org.engine.graphics.Vertex;
import org.engine.io.*;
import org.engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable {
    public Thread game;
    public Window window;
    public Renderer renderer;
    public final int WIDTH = 1280, HEIGHT = 760;

    public Mesh mesh =
            new Mesh(
                    new Vertex[] {
                        new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f)),
                        new Vertex(new Vector3f(0.5f, 0.5f, 0.0f)),
                        new Vertex(new Vector3f(0.5f, -0.5f, 0.0f)),
                        new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f))
                    },
                    new int[] {
                        0, 1, 2,
                        0, 3, 2
                    });

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        window = new Window(WIDTH, HEIGHT, "Minecraft Clone");
        renderer = new Renderer();
        window.setBackgroundColour(0, 0.5f, 1.0f);
        // window.setFullscreen(true);
        window.create();
        mesh.create();
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
        renderer.renderMesh(mesh);
        window.swapBuffers();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
