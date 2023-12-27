import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int SCREENWIDTH = 1920;
        final int SCREENHEIGHT = 1080;
//        create new window
        InitWindow(SCREENWIDTH, SCREENHEIGHT, "testing game mechanics");

//      target fps TEST
        SetTargetFPS(60);

//        making a random instance
        Random rand = new Random();
        Player player1 = new Player();
        EnemyHandler enemies = new EnemyHandler();

        int amountOfEnemies = rand.nextInt(5, 13);
        enemies.addMultipleEnemies(amountOfEnemies);

//        Enemy enemy1 = new Enemy(200,500, 350,30);
//        this checks if they close the window or press escape
//        if not, run the loop and update stuff
        while (!WindowShouldClose()){
            BeginDrawing();
            ClearBackground(RAYWHITE);
            player1.update();
            enemies.update();
            DrawFPS(100,100);
            EndDrawing();
        }
//        close the window
        CloseWindow();
    }
}
