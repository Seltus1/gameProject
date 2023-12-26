import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
public class Main {
    public static void main(String[] args) {
        final int SCREENWIDTH = 1920;
        final int SCREENHEIGHT = 1080;
//        create new window
        InitWindow(SCREENWIDTH, SCREENHEIGHT, "testing game mechanics");
//      target fps TEST

        SetTargetFPS(60);

        Player player1 = new Player();
        Enemy enemy1 = new Enemy(50,200,500,30);
        EnemyHandler.spawnEnemies();
//        this checks if they close the window or press escape
//        if not, run the loop and update stuff
        while (!WindowShouldClose()){

            BeginDrawing();
            ClearBackground(RAYWHITE);
            player1.update();
            enemy1.update();
            for (int i = 0; i<EnemyHandler.getEnemyList().size();i++){
                EnemyHandler.getEnemyList().get(i).update();
            }

            DrawFPS(100,100);
            EndDrawing();
        }
//        close the window
        CloseWindow();
    }
}
