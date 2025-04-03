import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window
{
    private int width, height;
    private String title;
    private long window;

    public Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create()
    {
        if (!GLFW.glfwInit())
        {
            System.err.println("ERROR: GLFW wasn't initialised!");
            return;
        }

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0)
        {
            System.err.println("ERROR: window wasn't created!");
            return;
        }

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);

        GLFW.glfwShowWindow(window);
    }

    public void update()
    {
        GLFW.glfwPollEvents();
    }

    public void swapBuffers()
    {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose()
    {
        return GLFW.glfwWindowShouldClose(window);
    }
}