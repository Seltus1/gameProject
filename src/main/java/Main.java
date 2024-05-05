import Creatures.Players.Player;
import Creatures.Players.Warriors.Knight;
import Creatures.Players.Warriors.Paladin;
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
    static Player player1;
    static GameHandler game;
    static PlayerHandler player;
    static int amountOfEnemy;
    static Raylib.Vector2 mousePos;
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
            DrawFPS(player1.getPosX() - (GetScreenWidth() / 2) + 100, player1.getPosY() - (GetScreenHeight() / 2) + 100);


            mousePos = GetScreenToWorld2D(new Jaylib.Vector2(GetMouseX(), GetMouseY()), camera);
            enemies.update(projectiles, player1, camera, player1.getFire(), player1.getPoison());
            projectiles.update(enemies, player1, camera);
            game.update(player1,enemies,camera);
            player.update(enemies, projectiles, camera, mousePos, game);
            player1.update(projectiles, camera, mousePos, enemies, game);

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
            player1 = new Knight(200, 12, 100, 0, 0, 5, 20, camera, RED);
            enemies = new EnemyHandler();
            player = new PlayerHandler(player1);
            game = new GameHandler(player1);
        }
        public static void cameraSet () {
            camera.offset(new Jaylib.Vector2(GetScreenWidth() / 2f, GetScreenHeight() / 2f));
            camera.rotation(0f);
            camera.zoom(1f);
        }
        public static void drawMouse(){
            DrawCircle((int) mousePos.x(), (int) mousePos.y(), 5, PURPLE);
        }
}