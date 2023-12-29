import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class GameHandler {
    private boolean isinMenu;
    private boolean isPlaying;
    private int middleX;
    private int middleY;

    public GameHandler(){
        isinMenu = true;
        isPlaying = false;
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

    public void update(){
        drawTexts();
        if(IsMouseButtonPressed(MOUSE_BUTTON_LEFT)){
            if(GetMouseX() > (middleX - 100) && GetMouseX() < (middleX + 100)) {
                if (GetMouseY() > (middleY - 100) && GetMouseY() < (middleY + 100)) {
                    startGame();
                }
            }
        }
    }

    public boolean isIsinMenu() {
        return isinMenu;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
