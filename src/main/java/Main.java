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
//        ToggleFullscreen();

        // Set the target frames per second (fps)
        SetTargetFPS(60);

        // Create instances of necessary game objects
        Random rand = new Random();
        ProjectileHandler projectiles = new ProjectileHandler();
        Player player1 = new Player(100, 12, 15, 850, 550, 5, 20, RED);
        EnemyHandler enemies = new EnemyHandler();
        PlayerHandler player = new PlayerHandler(player1);
        GameHandler game = new GameHandler();


        // Generate a random number of enemies
        SetMouseCursor(3);
        int amountOfEnemy = rand.nextInt(15) + 3;
        enemies.addMultipleEnemies(amountOfEnemy);

        // Main game loop
        while (!WindowShouldClose()) {
            // Begin drawing on the window
            BeginDrawing();
            // Clear the window background with a color (RAYWHITE)
            ClearBackground(RAYWHITE);
//            if (game.isIsinMenu()) {
//                game.update();
//            }
//            if (game.isPlaying()) {
//                 Update the player and enemies
                player1.update(projectiles, player1);
                player.update(enemies, projectiles);
                enemies.update(projectiles, player1);
                if (enemies.size() == 0) {
                    enemies.addMultipleEnemies(amountOfEnemy);
                }
                // Display the current frames per second (FPS)
                DrawFPS(100, 100);
                // End drawing
                EndDrawing();
            }
//        }
        // Close the window when the loop exits (window closed or Esc pressed)
        CloseWindow();
    }

}