package org.main;

import org.engine.graphics.*;
import org.engine.io.*;
import org.engine.maths.Vector2f;
import org.engine.maths.Vector3f;
import org.engine.objects.GameObject;
import org.lwjgl.glfw.GLFW;


import java.io.IOException;

public class Main implements Runnable {
    public Thread game;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public final int WIDTH = 1280, HEIGHT = 760;

    public Mesh mesh =
            new Mesh(
                    new Vertex[] {
                        new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
                        new Vertex(new Vector3f(0.5f, 0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(1.0f, 0.0f)),
                        new Vertex(new Vector3f(0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector2f(1.0f, 1.0f)),
                        new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(0.0f, 1.0f))
                    },
                    new int[] {
                        0, 1, 2,
                        0, 3, 2
                    }, new Material("/textures/beautiful.png"));

    public GameObject object = new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh);

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init(){
        window = new Window(WIDTH, HEIGHT, "Minecraft Clone");

        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");

        renderer = new Renderer(shader);
        window.setBackgroundColour(0, 0.5f, 1.0f);
        // window.setFullscreen(true);
        window.create();
        mesh.create();
        shader.create();
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
        close();
    }

    // Main loop
    private void update() {
        window.update();
        object.update();

        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
        }
    }

    private void render() {
        renderer.renderMesh(object);
        window.swapBuffers();
    }

    private void close() {
        window.free();
        mesh.free();
        shader.free();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
