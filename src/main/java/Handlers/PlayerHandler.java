package Handlers;
import Creatures.Players.Warriors.Knight;
import Elements.*;
import Creatures.Players.Player;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.GetScreenWidth;

public class PlayerHandler {
    private Player player;
    private boolean isAlive;
    private Fire fire;
    private int thingy;
    private double xDir;
    private double yDir;
    private double cursorX;
    private double cursorY;
    private CooldownHandler cooldown;

    public PlayerHandler(Player player){
        this.player = player;
        isAlive = true;
        fire = new Fire();
        cooldown = new CooldownHandler();
    }

    public void update(EnemyHandler enemy, ProjectileHandler projList, Camera2D camera, Raylib.Vector2 mousePos, GameHandler game) {
//        shoot(projList);

        if (player.getHp() <= 0){
            isAlive = false;
        }
        drawHp();
        if(player.isOnFire()){
            drawBurn();
        }
        drawFireFex();
        drawRange(mousePos);
        drawPoison();
        drawPlayer();
        drawOxygen();
        drawPos();
        drawCooldowns();
        drawNumCoins();
        if(player instanceof Knight){
            drawShieldHP();
        }
        player.checkIfOutsideArea(game);
//        player.playerOutsideBorderArea();

    }

    public void drawRange(Raylib.Vector2 mousePos) {
//        player.getVector().rangeLine(player, mousePos);
    }

    public void drawPlayer(){
        DrawCircle((int)player.getPosition().x(),(int)player.getPosition().y(),player.getSize(),player.getColor());
    }



    public void drawHp(){
        double percentage = (double) player.getHp() /  player.getInitalHp();
        double width = percentage * 300;
        Raylib.Rectangle HP = new Raylib.Rectangle(new Jaylib.Rectangle(player.getPosX() - (GetScreenWidth() / 2) + 50,  player.getPosY() + (GetScreenHeight() / 2) - 100, (int) width, 40));
        Raylib.Rectangle HPContainer = new Raylib.Rectangle(new Jaylib.Rectangle(player.getPosX() - (GetScreenWidth() / 2) + 50, player.getPosY() + (GetScreenHeight() / 2) - 100, 300, 40));
        DrawRectangleRounded(HP,5,10,DARKGREEN);
        DrawRectangleRoundedLines(HPContainer, 5,10,3, BLACK);
        String s = String.format("%d", player.getHp());
        DrawText(s, player.getPosX() - (GetScreenWidth() / 2) + 180, player.getPosY() + (GetScreenHeight() / 2) - 90, 20, BLACK);
    }
    public void drawOxygen(){
        float percentage = (float) player.getSpaceSuitOxygen() /  player.getInitialSpaceSuitOxygen();
        float height = percentage * 100;
        float remainingHeight = 100 - height;
        float barTop = player.getPosY() + (GetScreenHeight() / 2) - 150 + remainingHeight;
        Raylib.Rectangle oxygen = new Raylib.Rectangle(new Jaylib.Rectangle(player.getPosX() - (GetScreenWidth() / 2) + 380, barTop, 40, height));
        Raylib.Rectangle OxyContainer = new Raylib.Rectangle(new Jaylib.Rectangle(player.getPosX() - (GetScreenWidth() / 2) + 380, player.getPosY() + (GetScreenHeight() / 2) - 150, 40, 100));
        DrawRectangleRounded(oxygen,10,5,SKYBLUE);
        DrawRectangleRoundedLines(OxyContainer, 10,5,3, BLACK);
        String s = String.format("%d", player.getSpaceSuitOxygen());
        DrawText(s, player.getPosX() - (GetScreenWidth() / 2) + 390, player.getPosY() + (GetScreenHeight() / 2) - 120, 20, BLACK);
    }

    public void drawBurn(){
        double thing = (double) player.getBurnTicks() / player.getIntialBurn();
        double width = thing * 150;
        DrawRectangle(player.getPosX() - (GetScreenWidth() / 2) + 50, player.getPosY() + (GetScreenHeight() / 2) - 200, (int) width, 40, ORANGE);
        DrawRectangleLines(player.getPosX() - (GetScreenWidth() / 2) + 50, player.getPosY() + (GetScreenHeight() / 2) - 200, 150, 40, BLACK);
        String s = String.format("BURN: %d", player.getBurnTicks());
        DrawText(s, player.getPosX() - (GetScreenWidth() / 2) + 50, player.getPosY() + (GetScreenHeight() / 2) - 200, 20, BLACK);
    }

    public void drawFireFex(){
        if(player.isFireHex()) {
            player.setColor(ORANGE);
            if(!player.isOnFire()){
                DrawCircle(player.getPosX() - (GetScreenWidth() / 2) + 50,player.getPosY() + (GetScreenHeight() / 2) - 180,25,BLACK);
                DrawCircle(player.getPosX() - (GetScreenWidth() / 2) + 50,player.getPosY() + (GetScreenHeight() / 2) - 180,10,ORANGE);
            }
            else{
                DrawCircle(player.getPosX() - (GetScreenWidth() / 2) + 230,player.getPosY() + (GetScreenHeight() / 2) - 180,25,BLACK);
                DrawCircle(player.getPosX() - (GetScreenWidth() / 2) + 230,player.getPosY() + (GetScreenHeight() / 2) - 180,10,ORANGE);
            }
        }
        else{
            player.setColor(RED);
        }
    }

    public void drawPos(){
        DrawText("X: " + player.getPosX() + " Y: " + player.getPosY(), player.getPosX() - (GetScreenWidth()/ 2) + 100, player.getPosY() + (GetScreenHeight() / 2) - 300,30,BLACK);
    }
    public void drawNumCoins(){
        DrawText(player.getNumCoins() + "", player.getPosX() - (GetScreenWidth() / 2) + 100, player.getPosY() - (GetScreenHeight() / 2) + 200, 30,BLACK);
    }

    public void drawPoison(){
        if(player.isPoisoned()){
            DrawCircle(player.getPosX(), player.getPosY() - 50, 8,GREEN);
        }
    }

    public void drawShieldHP(){
        float percentage =  (float) player.getShieldHp() / player.getShieldMaxHp();
        double width = percentage * 300;
        Raylib.Rectangle shieldHP = new Raylib.Rectangle(new Jaylib.Rectangle(player.getPosX() - (GetScreenWidth() / 2) + 50,  player.getPosY() + (GetScreenHeight() / 2) - 150, (int) width, 40));
        Raylib.Rectangle shieldHPContainer = new Raylib.Rectangle(new Jaylib.Rectangle(player.getPosX() - (GetScreenWidth() / 2) + 50, player.getPosY() + (GetScreenHeight() / 2) - 150, 300, 40));
        DrawRectangleRounded(shieldHP,5,10,BLUE);
        DrawRectangleRoundedLines(shieldHPContainer, 5,10,3, BLACK);
        String s = String.format("%d", player.getShieldHp());
        DrawText(s, player.getPosX() - (GetScreenWidth() / 2) + 180, player.getPosY() + (GetScreenHeight() / 2) - 140, 20, BLACK);
//        DrawTriangle(shieldLeft,shieldRight,shieldBot,BLUE);



//        DrawRectangleRoundedLines(HPContainer, 5,10,3, BLACK);
//        String s = String.format("%d", player.getHp());
//        DrawText(s, player.getPosX() - (GetScreenWidth() / 2) + 180, player.getPosY() + (GetScreenHeight() / 2) - 90, 20, BLACK);
    }

    public void drawCooldowns(){
        Raylib.Vector2 cd1Top = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX()), player.getPosY() + (GetScreenHeight() / 2) - 235));
        Raylib.Vector2 cd1Left = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX() - 60), player.getPosY() + (GetScreenHeight() / 2) - 175));
        Raylib.Vector2 cd1Right = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX() + 60), player.getPosY() + (GetScreenHeight() / 2) - 175));
        Raylib.Vector2 cd1Bot = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX()), player.getPosY() + (GetScreenHeight() / 2) - 115));

        DrawLineV(cd1Top,cd1Left,BLACK);
        DrawLineV(cd1Left,cd1Bot,BLACK);
        DrawLineV(cd1Bot,cd1Right,BLACK);
        DrawLineV(cd1Right,cd1Top,BLACK);

        Raylib.Vector2 cd2Top = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX() - 50), player.getPosY() + (GetScreenHeight() / 2) - 150));
        Raylib.Vector2 cd2Left = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX() - 95), player.getPosY() + (GetScreenHeight() / 2) - 105));
        Raylib.Vector2 cd2Right = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX() - 5), player.getPosY() + (GetScreenHeight() / 2) - 105));
        Raylib.Vector2 cd2Bot = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX() - 50), player.getPosY() + (GetScreenHeight() / 2) - 60) );

        DrawLineV(cd2Top,cd2Left,BLACK);
        DrawLineV(cd2Left,cd2Bot,BLACK);
        DrawLineV(cd2Bot,cd2Right,BLACK);
        DrawLineV(cd2Right,cd2Top,BLACK);



        Raylib.Vector2 cd3Top = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX() + 50), player.getPosY() + (GetScreenHeight() / 2) - 150));
        Raylib.Vector2 cd3Left = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX()  + 5), player.getPosY() + (GetScreenHeight() / 2) - 105));
        Raylib.Vector2 cd3Right = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX() + 95), player.getPosY() + (GetScreenHeight() / 2) - 105));
        Raylib.Vector2 cd3Bot = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX() + 50), player.getPosY() + (GetScreenHeight() / 2) - 60) );

        DrawLineV(cd3Top,cd3Left,BLACK);
        DrawLineV(cd3Left,cd3Bot,BLACK);
        DrawLineV(cd3Bot,cd3Right,BLACK);
        DrawLineV(cd3Right,cd3Top,BLACK);


        Raylib.Vector2 cd4Top = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX()), player.getPosY() + (GetScreenHeight() / 2) - 100));
        Raylib.Vector2 cd4Left = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX()  + 30), player.getPosY() + (GetScreenHeight() / 2) - 55));
        Raylib.Vector2 cd4Right = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX() - 30), player.getPosY() + (GetScreenHeight() / 2) - 55));
        Raylib.Vector2 cd4Bot = new Raylib.Vector2(new Jaylib.Vector2((player.getPosX()), player.getPosY() + (GetScreenHeight() / 2) - 10) );

        DrawLineV(cd4Top,cd4Left,BLACK);
        DrawLineV(cd4Left,cd4Bot,BLACK);
        DrawLineV(cd4Bot,cd4Right,BLACK);
        DrawLineV(cd4Right,cd4Top,BLACK);
        drawCooldownNumbers();
    }

    private void drawCooldownNumbers(){
        if(player instanceof Knight){
//            when we add the ult, add the same system.


//          charge texts
            chargeCDNums();
//            shield texts
            shieldCDNums();
//            sword texts
            swordCDNums();
//            overdrive texts
            overDriveCDNums();
        }
    }
    private void chargeCDNums(){
        if(player.getCanUseUtility()) {
            DrawText("CHARGE", player.getPosX() - 95, player.getPosY() + (GetScreenHeight() / 2) - 115, 20, BLACK);
            return;
        }
//        calculating the timer for the cooldown
        int cdNumSecs = (int) player.getChargeCD().getCurrentFrameInMilliSeconds() / 1000;
        cdNumSecs = (player.getTotalChargeCD() / 1000) - cdNumSecs;
//        drawing the calculated number
        DrawText("" + cdNumSecs, player.getPosX() - 65, player.getPosY() + (GetScreenHeight() / 2) - 115, 40, BLACK);
    }


    private void shieldCDNums(){
        if(player.isCanUseSecondary()) {
            DrawText("SHIELD", player.getPosX() + 10, player.getPosY() + (GetScreenHeight() / 2) - 115, 20, BLACK);
            return;
        }
        int cdNumSecs = (int) player.getShieldCD().getCurrentFrameInMilliSeconds() / 1000;
        cdNumSecs = (player.getTotalShieldCD() / 1000) - cdNumSecs;
        DrawText("" + cdNumSecs, player.getPosX() + 40, player.getPosY() + (GetScreenHeight() / 2) - 115, 40, BLACK);
    }


    private void swordCDNums(){
        if(player.canMelee()){
            DrawText("SWORD", player.getPosX() - 25,player.getPosY() + (GetScreenHeight() / 2) - 65, 15, BLACK);
            return;
        }
        int cdNumSecs = (int) player.getShieldCD().getCurrentFrameInMilliSeconds() / 1000;
        cdNumSecs = (player.getShotcooldown() / 1000) - cdNumSecs;
        DrawText("" + cdNumSecs, player.getPosX() - 10, player.getPosY() + (GetScreenHeight() / 2) - 65, 40, BLACK);
    }
    private void overDriveCDNums(){
        if(player.isCanUseUltimate()){
            DrawText("ULT", player.getPosX() - 30,player.getPosY() + (GetScreenHeight() / 2) - 190, 30, BLACK);
            return;
        }
        int cdNumSecs = (int) player.getUltimateTimer().getCurrentFrameInMilliSeconds() / 1000;
        cdNumSecs = (player.getUltimateCD() / 1000) - cdNumSecs;
        DrawText("" + cdNumSecs, player.getPosX() - 30, player.getPosY() + (GetScreenHeight() / 2) - 190, 40, BLACK);
    }
}