import Creatures.*;
import Handlers.*;
import Creatures.Player;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Define screen width and height constants
        final int SCREENWIDTH = GetScreenWidth();
        final int SCREENHEIGHT = GetRenderHeight();

        // Create a window with specified dimensions and title
        InitWindow(SCREENWIDTH, SCREENHEIGHT, "The_Game");
//        ToggleFullscreen();

        // Set the target frames per second (fps)
        SetTargetFPS(60);

        // Create instances of necessary game objects
        Random rand = new Random();
        ProjectileHandler projectiles = new ProjectileHandler();
        Creatures.Player player1 = new Creatures.Player(100, 12, 15, 550, 250, 5, 20, 700, RED);
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
//            if(game.isIsinMenu()){
//                game.update();
//            }
//           if(player1.getHp() >= 0) {
               // Update the player and enemies

            player.update(enemies, projectiles);
            enemies.update(projectiles, player1);
            projectiles.update();
            if (enemies.size() == 0) {
                enemies.addMultipleEnemies(1);
            }
            player1.update(projectiles);

               // Display the current frames per second (FPS)

//           }
//           else{
//               game.update(player1, enemies);
//           }
               DrawFPS(100, 100);

               // End drawing
               EndDrawing();
        }

        // Close the window when the loop exits (window closed or Esc pressed)
        CloseWindow();
    }

}