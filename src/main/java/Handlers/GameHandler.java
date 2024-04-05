package Handlers;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

import Creatures.Players.Player;
import Items.Damage.FireRateUp;
import Shops.Shop;

import java.util.Random;

public class GameHandler {
    private boolean isinMenu;
    private Random rand;
    private boolean isPlaying;
    private int middleX;
    private int middleY;
    private int waveCount;
    private String romanNumeralWave;
    private CooldownHandler waveInCenter;
    private boolean drawWaveInCenter;
    private int areaSize;
    private Shop shop;

    public GameHandler(){
        isinMenu = false;
        isPlaying = true;
        middleX = GetScreenWidth() / 2;
        middleY = GetScreenHeight() / 2;
        rand = new Random();
        waveCount = 1;
        waveInCenter = new CooldownHandler();
        drawWaveInCenter = true;
        areaSize = 2000;
        shop = new Shop();
    }

    public void startGame(){
        isPlaying = true;
        isinMenu = false;
    }

    public void drawTexts(){
        DrawText("Play", middleX, middleY, 50, BLACK);
    }

    public void update(Player player, EnemyHandler enemies, Camera2D camera){
        spawnNewWave(enemies,camera,player);
        drawWave(player);
        drawArea();


//        drawTexts();
//        if(IsMouseButtonPressed(MOUSE_BUTTON_LEFT)){
//            if(GetMouseX() > (middleX - 100) && GetMouseX() < (middleX + 100)) {
//                if (GetMouseY() > (middleY - 100) && GetMouseY() < (middleY + 100)) {
//                    startGame();
//                }
//            }
//        }
//            isPlaying = false;
//            String unaliveText = "you suck!";
//            String restart = "press space bar to restart";
//            DrawText(unaliveText, GetScreenWidth() / 2 - MeasureText(unaliveText, 50) / 2, GetScreenHeight() / 2 - 10, 50, RED);
//            DrawText(restart, GetScreenWidth() / 2 - MeasureText(unaliveText, 50) / 2, GetScreenHeight() / 2 + 100, 30, GRAY);
//            if(IsKeyPressed(KEY_SPACE)){
//                player.setHp(player.getInitalHp());
//                enemies.getList().clear();
//            }

    }
    public void spawnNewWave(EnemyHandler enemies, Camera2D camera, Player player){
        if (enemies.size() == 0){
                shop.update();
                if(IsKeyPressed(KEY_E)){
                    FireRateUp newItem = new FireRateUp(player);
                    player.getItems().add(newItem);
                    player.newItemAdded(newItem);
                }
                if(IsKeyPressed(KEY_SPACE)) {
                    drawWaveInCenter = true;
                    waveCount++;
                    enemies.addMultipleEnemies(waveCount, camera, player);
                }

        }
    }
    public void drawWave(Player player){
        romanNumeralWave = waveNumToRomanNumeral();
        if(drawWaveInCenter){
            if (waveInCenter.cooldown(1000)) {
                drawWaveInCenter = false;
            }
        }
        if(drawWaveInCenter){
            DrawText(romanNumeralWave, player.getPosX(), player.getPosY(), 70, BLACK);
        }
        else{
            DrawText(romanNumeralWave, player.getPosX() - (GetScreenWidth() / 2) + 1800, player.getPosY() + (GetScreenHeight() / 2) - 100, 70, RED);
        }
    }

    public String waveNumToRomanNumeral(){
        if (waveCount < 1 || waveCount > 3999) {
            return "Invalid number";
        }
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[waveCount / 1000] +
                hundreds[(waveCount % 1000) / 100] +
                tens[(waveCount % 100) / 10] +
                ones[waveCount % 10];
    }
    public void drawArea(){
        DrawRectangleLines(-areaSize / 2, -areaSize / 2,areaSize,areaSize,BLACK);
    }

    public boolean isIsinMenu() {
        return isinMenu;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public int getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(int areaSize) {
        this.areaSize = areaSize;
    }
}
