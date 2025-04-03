public class Main implements Runnable
{
    public Thread game;
    public static Window window;
    public static final int WIDTH = 1280, HEIGHT = 760;

    public void start()
    {
        game = new Thread(this, "game");
        game.start();
    }

    public static void init()
    {
        //System.out.println("Initializing GAME!");
        window = new Window(WIDTH, HEIGHT, "Minecraft Clone");
        window.create();
    }

    public void run()
    {
        init();

        while (!window.shouldClose())
        {
            update();
            render();
        }
    }

    // Main loop
    private void update()
    {
        window.update();
    }

    private void render()
    {
        window.swapBuffers();
    }

    public static void main(String[] args)
    {
        new Main().start();
    }
}