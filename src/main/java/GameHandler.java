import java.util.ArrayList;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class GameHandler {
    private boolean isinMenu;
    private boolean isPlaying;
    private int middleX;
    private int middleY;

    public GameHandler(){
        isinMenu = false;
        isPlaying = true;
        middleX = GetScreenWidth() / 2;
        middleY = GetScreenHeight() / 2;
    }

    public void startGame(){
        isPlaying = true;
        isinMenu = false;
    }

    public void drawTexts(){
        DrawText("Play", middleX, middleY, 50, BLACK);
    }

    public void update(Player player, EnemyHandler enemies){
//        drawTexts();
//        if(IsMouseButtonPressed(MOUSE_BUTTON_LEFT)){
//            if(GetMouseX() > (middleX - 100) && GetMouseX() < (middleX + 100)) {
//                if (GetMouseY() > (middleY - 100) && GetMouseY() < (middleY + 100)) {
//                    startGame();
//                }
//            }
//        }
            isPlaying = false;
            String unaliveText = "you suck!";
            String restart = "press space bar to restart";
            DrawText(unaliveText, GetScreenWidth() / 2 - MeasureText(unaliveText, 50) / 2, GetScreenHeight() / 2 - 10, 50, RED);
            DrawText(restart, GetScreenWidth() / 2 - MeasureText(unaliveText, 50) / 2, GetScreenHeight() / 2 + 100, 30, GRAY);
            if(IsKeyPressed(KEY_SPACE)){
                player.setHp(player.getInitalHp());
                enemies.getList().clear();
            }

    }

    public boolean isIsinMenu() {
        return isinMenu;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
