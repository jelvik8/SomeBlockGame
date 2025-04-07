import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
    private int width, height;
    private String title;
    private long window;
    public Input input;

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
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) {
            System.err.println("ERROR: window wasn't created!");
            return;
        }

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());

        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(1);
    }

    public void update() {
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
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}