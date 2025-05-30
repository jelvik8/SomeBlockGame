package org.engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

public class Material {
    private String path;
    private Texture texture;
    private float width, height;
    private int textureID;

    public Material(String path) {
        this.path = path;
    }

    public void create() {
        try {
            texture = TextureLoader.getTexture(path.split("[.]")[1], Material.class.getResourceAsStream(path), GL11.GL_NEAREST);
            width = texture.getWidth();
            height = texture.getHeight();
            textureID = texture.getTextureID();
        } catch (IOException e) {
            System.err.println("Failed to load texture at " + path);
        }
    }

    public void free() {
        GL13.glDeleteTextures(textureID);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }
}
