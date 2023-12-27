import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Define screen width and height constants
        final int SCREENWIDTH = 1920;
        final int SCREENHEIGHT = 1080;

        // Create a window with specified dimensions and title
        InitWindow(SCREENWIDTH, SCREENHEIGHT, "testing game mechanics");

        // Set the target frames per second (fps)
        SetTargetFPS(60);

        // Create instances of necessary game objects
        Random rand = new Random();
        ProjectileHandler projectiles = new ProjectileHandler();
        Player player1 = new Player();
        EnemyHandler enemies = new EnemyHandler();

        // Generate a random number of enemies
        int amountOfEnemies = rand.nextInt(8) + 5;
        enemies.addMultipleEnemies(amountOfEnemies);

        // Main game loop
        while (!WindowShouldClose()) {
            // Begin drawing on the window
            BeginDrawing();

            // Clear the window background with a color (RAYWHITE)
            ClearBackground(RAYWHITE);

            // Update the player and enemies
            player1.update(projectiles);
            enemies.update(projectiles);

            // Display the current frames per second (FPS)
            DrawFPS(100, 100);

            // End drawing
            EndDrawing();
        }

        // Close the window when the loop exits (window closed or Esc pressed)
        CloseWindow();
    }
}
