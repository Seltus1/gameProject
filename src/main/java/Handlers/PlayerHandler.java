package Handlers;
import Elements.*;
import Creatures.Players.Player;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

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

    public void drawRange(Raylib.Vector2 mousePos) {
//        player.getVector().rangeLine(player, mousePos);
    }

    public void drawPlayer(){
        DrawCircle((int)player.getPosition().x(),(int)player.getPosition().y(),player.getSize(),player.getColor());
    }



    public void drawHp(Camera2D camera){
        double thing = (double) player.getHp() /  player.getInitalHp();
        double width = thing * 150;
        DrawRectangle(player.getPosX() - (GetScreenWidth() / 2) + 50,  player.getPosY() + (GetScreenHeight() / 2) - 100, (int) width, 40, DARKGREEN);
        DrawRectangleLines(player.getPosX() - (GetScreenWidth() / 2) + 50, player.getPosY() + (GetScreenHeight() / 2) - 100, 150, 40, BLACK);
        String s = String.format("HP: %d", player.getHp());
        DrawText(s, player.getPosX() - (GetScreenWidth() / 2) + 50, player.getPosY() + (GetScreenHeight() / 2) - 100, 20, BLACK);
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

    public void drawPoison(){
        if(player.isPoisoned()){
            DrawCircle(player.getPosX(), player.getPosY() - 50, 8,GREEN);
        }
    }

    public void update(EnemyHandler enemy, ProjectileHandler projList, Camera2D camera, Raylib.Vector2 mousePos) {
//        shoot(projList);
        if (player.getHp() <= 0){
            isAlive = false;
        }
        drawHp(camera);
        if(player.isOnFire()){
            drawBurn();
        }
        drawFireFex();
        drawRange(mousePos);
        drawPoison();
        drawPlayer();
    }
}