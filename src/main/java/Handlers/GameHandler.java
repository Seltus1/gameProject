package Handlers;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

import Creatures.Players.Player;
import Shops.Shop;
import com.raylib.Raylib;

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
    private boolean rerolledShop;
    private CooldownHandler borderShrinker;
    private int borderShrinkCD;
    private int currentArenaElement;
    private Raylib.Color arenaColor;
    private boolean drawFailedElementChange;
    private boolean drawSuccessElementChange;
    private boolean startElementChangeCD;
    private CooldownHandler elementChangeCH;
    private String currentArenaElementString;
    private Sound sound;

    public GameHandler(Player player){
        isinMenu = false;
        isPlaying = true;
        middleX = GetScreenWidth() / 2;
        middleY = GetScreenHeight() / 2;
        rand = new Random();
        waveCount = 1;
        waveInCenter = new CooldownHandler();
        borderShrinker = new CooldownHandler();
        elementChangeCH = new CooldownHandler();
        sound = LoadSound("..\\gameProject\\sounds\\war-drum-loop-103870.mp3");
        drawWaveInCenter = true;
        areaSize = 2000;
        shop = new Shop(player);
        rerolledShop = false;
        borderShrinkCD = 75;
        currentArenaElement = 6;
        arenaColor = GRAY;

    }

    public void startGame(){
        isPlaying = true;
        isinMenu = false;
    }

    public void drawTexts(){
        DrawText("Play", middleX, middleY, 50, BLACK);
    }

    public void update(Player player, EnemyHandler enemies, Camera2D camera){
        betweenRoundStuff(enemies,camera,player);
        drawWave(player);
        drawArea();
        shrinkBorder(enemies);
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
    public void betweenRoundStuff(EnemyHandler enemies, Camera2D camera, Player player){
        if (enemies.size() == 0){
            if(!rerolledShop){
                shop.reroll();
                rerolledShop = true;
            }
            shop.update(player);
            if(IsKeyPressed(KEY_SPACE)) {
                drawWaveInCenter = true;
                waveCount++;
                enemies.addMultipleEnemies(waveCount, camera, this);
                rerolledShop = false;
//                PlaySound(sound);
            }
            if(IsKeyPressed(KEY_I)){
                rerollArenaElement();
                elementToColor();
            }
            drawElementSwitch(player);
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

    public void shrinkBorder(EnemyHandler enemies){
        if(enemies.size() != 0){
            if(borderShrinker.cooldown(borderShrinkCD)){
                areaSize--;
            }
        }
    }

    public String waveNumToRomanNumeral(){
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
        DrawRectangleLines(-areaSize / 2, -areaSize / 2,areaSize,areaSize,arenaColor);
    }
    private void rerollArenaElement(){
//        elements: fire, water, earth, wind, Electric, void
//        fire = 1
//        water = 2
//        earth = 3
//        electric = 4
//        void = 5
        int randElement = rand.nextInt(100);
        if(randElement  <= 50){
            drawFailedElementChange = true;
            areaSize -= 50;
        }
        else if(randElement <= 60){
            drawSuccessElementChange = true;
            currentArenaElement = 1;
            currentArenaElementString = "FIRE";
        }
        else if(randElement <= 70){
            drawSuccessElementChange = true;
            currentArenaElement = 2;
            currentArenaElementString = "WATER";
        }
        else if(randElement <= 80){
            drawSuccessElementChange = true;
            currentArenaElement = 3;
            currentArenaElementString = "EARTH";
        }
        else if(randElement <= 90){
            drawSuccessElementChange = true;
            currentArenaElement = 4;
            currentArenaElementString = "ELECTRIC";
        }
        else{
            drawSuccessElementChange = true;
            currentArenaElement = 5;
            currentArenaElementString = "VOID";
        }
        startElementChangeCD = true;
        elementChangeCH.resetCooldown();
    }
    private void elementToColor(){
        switch(currentArenaElement){
            case 1:
                arenaColor = RED;
                break;
            case 2:
                arenaColor = BLUE;
                break;
            case 3:
                arenaColor = BROWN;
                break;
            case 4:
                arenaColor = YELLOW;
                break;
            case 5:
                arenaColor = PURPLE;
                break;
            case 6:
                arenaColor = GRAY;
                break;
        }
    }
    private void drawElementSwitch(Player player){
        if(drawSuccessElementChange){
            DrawText("The Gods favor you and have changed the arena element to " + currentArenaElementString,player.getPosX() - 300, player.getPosY() + (GetScreenHeight() / 2) - 300, 30, BLACK);;
        }
        else if(drawFailedElementChange){
            DrawText("The Gods spite you and shrink the arena", player.getPosX() - 300, player.getPosY() + (GetScreenHeight() / 2) - 300, 30, BLACK);
        }

        if (startElementChangeCD){
            if(elementChangeCH.cooldown(4000)){
                startElementChangeCD = false;
                drawSuccessElementChange = false;
                drawFailedElementChange = false;
            }
        }

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
