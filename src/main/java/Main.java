import Creatures.*;
import Handlers.*;
import Creatures.Player;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Define screen width and height constants
        final int frameRate = 60;
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
        Creatures.Player player1 = new Creatures.Player(100, 12, 15, 0, 0, 5, 20, 700, RED);
        EnemyHandler enemies = new EnemyHandler();
        PlayerHandler player = new PlayerHandler(player1);
        GameHandler game = new GameHandler();
        Camera2D camera = new Camera2D();
        VectorHandler vectorHandler = new VectorHandler(0,0,0);
        camera.offset(new Jaylib.Vector2(GetScreenWidth()/ 2f, GetScreenHeight()/ 2f));
        camera.rotation(0f);
        camera.zoom(1f);
        // Generate a random number of enemies
        SetMouseCursor(3);
        int amountOfEnemy = rand.nextInt(15) + 3;
        enemies.addMultipleEnemies(1);

        // Main game loop
        while (!WindowShouldClose()) {
            // Begin drawing on the window
            BeginDrawing();
            BeginMode2D(camera);

            int frameTime = (int) GetFrameTime();

            // Clear the window background with a color (RAYWHITE)
            ClearBackground(RAYWHITE);
            DrawText("posX " + player1.getPosX() + "posY " + player1.getPosY(), 200, 200, 30, BLACK);
//            if(game.isIsinMenu()){
//                game.update();
//            }
//           if(player1.getHp() >= 0) {
            // Update the player and enemies
//            vectorHandler.updateCamera(cameraTarget, camera);

            player.update(enemies, projectiles, camera);
            enemies.update(projectiles, player1);
            projectiles.update(enemies,player1);
            if (enemies.size() == 0) {
                enemies.addMultipleEnemies(1);
            }
            player1.update(projectiles, camera);
//            camera.target(player1.getPosition());

            // Display the current frames per second (FPS)

//           }
//           else{
//               game.update(player1, enemies);
//           }

            DrawFPS(player1.getPosX() - (GetScreenWidth() / 2) + 100, player1.getPosY() - (GetScreenHeight() / 2) + 100);

            // End drawing
            EndDrawing();
            EndMode2D();
        }

        // Close the window when the loop exits (window closed or Esc pressed)
        CloseWindow();
    }
}