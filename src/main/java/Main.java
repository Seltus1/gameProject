import Creatures.Players.Player;
import Creatures.Players.Warriors.Knight;
import Handlers.*;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import java.util.Random;

public class Main {
    final int frameRate = 60;
    final static int SCREENWIDTH = GetScreenWidth();
    final static int SCREENHEIGHT = GetRenderHeight();
    static ProjectileHandler projectiles;
    static EnemyHandler enemies;
    static Random rand;
    static Camera2D camera;
    static Player player;
    static GameHandler game;
    static PlayerHandler playerHandler;
    static InteractablesHandler interactablesHandler;
    static int amountOfEnemy;
    static boolean isInit = false;
    public static void main(String[] args) {
        init();
        gameLoop();
    }
    public static void init(){
        // Set the target frames per second (fps)
        SetTargetFPS(60);
        InitAudioDevice();
        // Create instances of necessary game objects
        createInstances();

        InitWindow(SCREENWIDTH, SCREENHEIGHT, "The_Game");
        enemies.addMultipleEnemies(1,camera,game);
        isInit = true;
    }

    public static void gameLoop() {
        // Begin drawing on the window
        while (!WindowShouldClose() && isInit) {
            HideCursor();
            cameraSet();
            BeginDrawing();
            BeginMode2D(camera);
            ClearBackground(RAYWHITE);
            DrawFPS(player.getPosX() - (GetScreenWidth() / 2) + 100, player.getPosY() - (GetScreenHeight() / 2) + 100);



            enemies.update(projectiles, player, camera, player.getFire(), player.getPoison());
            projectiles.update(enemies, player, camera);
            game.update(player,enemies,camera);
            interactablesHandler.update(player, game, camera);
            playerHandler.update(enemies, projectiles, camera, game.getMousePos(), game);
            player.update(projectiles, camera, game.getMousePos(), enemies, game, interactablesHandler);


            drawMouse();
            EndDrawing();
            EndMode2D();
        }
        CloseWindow();
    }

        public static void createInstances () {
            camera = new Camera2D();
            rand = new Random();
            projectiles = new ProjectileHandler();
            player = new Knight(200, 12, 100, 0, 0, 5, 20, camera, RED);
            enemies = new EnemyHandler();
            playerHandler = new PlayerHandler(player);
            game = new GameHandler(player, camera);
            interactablesHandler = new InteractablesHandler(camera);

        }
        public static void cameraSet () {
            camera.offset(new Jaylib.Vector2(GetScreenWidth() / 2f, GetScreenHeight() / 2f));
            camera.rotation(0f);
            camera.zoom(1f);
        }
        public static void drawMouse(){
            DrawCircle((int) game.getMousePos().x(), (int) game.getMousePos().y(), 5, PURPLE);
        }
}