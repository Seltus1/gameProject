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
        Camera2D camera = new Camera2D();
        camera.offset(new Jaylib.Vector2(GetScreenWidth()/ 2f, GetScreenHeight()/ 2f));
        camera.rotation(0f);
        camera.zoom(1f);
        Random rand = new Random();
        ProjectileHandler projectiles = new ProjectileHandler();
        Creatures.Player player1 = new Creatures.Player(100, 12, 15, 0, 0, 5, 20, 700, camera, RED);
        EnemyHandler enemies = new EnemyHandler();
        PlayerHandler player = new PlayerHandler(player1);
        GameHandler game = new GameHandler();
        // Generate a random number of enemies
        HideCursor();
        int amountOfEnemy = rand.nextInt(15) + 3;
        enemies.addMultipleEnemies(amountOfEnemy,camera);

        // Main game loop
        while (!WindowShouldClose()) {
            // Begin drawing on the window
            BeginDrawing();
            BeginMode2D(camera);
            Raylib.Vector2 mousePos = GetScreenToWorld2D(new Jaylib.Vector2(GetMouseX(), GetMouseY()),camera);
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


            player.update(enemies, projectiles, camera, mousePos);
            enemies.update(projectiles, player1, camera);
            projectiles.update(enemies,player1, camera);
            if (enemies.size() == 0) {
                enemies.addMultipleEnemies(amountOfEnemy, camera);
            }
            player1.update(projectiles, camera, mousePos);
//            camera.target(player1.getPosition());

            // Display the current frames per second (FPS)

//           }
//           else{
//               game.update(player1, enemies);
//           }
//            DrawRectangle(player.getPosX() - (GetScreenWidth() / 2) + 50,  player.getPosY() + (GetScreenHeight() / 2) - 100, (int) width, 40, DARKGREEN);
            DrawFPS(player1.getPosX() - (GetScreenWidth() / 2) + 100, player1.getPosY() - (GetScreenHeight() / 2) + 100);

            // End drawing
            DrawCircle((int) mousePos.x(),(int) mousePos.y(), 5, PURPLE);
            EndDrawing();
            EndMode2D();
        }

        // Close the window when the loop exits (window closed or Esc pressed)
        CloseWindow();
    }
}