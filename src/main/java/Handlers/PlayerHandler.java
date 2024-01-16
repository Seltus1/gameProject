package Handlers;
import Attacks.*;
import Elements.*;
import Creatures.Player;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    public void drawRange() {
        Raylib.Vector2 mousePos = GetMousePosition();
        Raylib.Vector2 playerPos = player.getPosition();
        float shotRange = player.getShotRange();

        // Calculate the direction vector from player to mouse
        Raylib.Vector2 direction = new Raylib.Vector2();
        direction.x(mousePos.x() - playerPos.x());
        direction.y(mousePos.y() - playerPos.y());


        // Normalize the direction vector
        float length = (float) Math.sqrt(direction.x() * direction.x() + direction.y() * direction.y());
        direction.x(direction.x() / length);
        direction.y(direction.y() / length);

        // Calculate the endpoint of the line based on player's position and direction
        Raylib.Vector2 endPoint = new Raylib.Vector2();
        endPoint.x(playerPos.x() + direction.x() * shotRange);
        endPoint.y(playerPos.y() + direction.y() * shotRange);

        // Draw the line from playerPos to endPoint
        DrawLineV(playerPos, endPoint, BLACK);
    }

    public void drawHp(){
        double thing = (double) player.getHp() /  player.getInitalHp();
        double width = thing * 150;
        DrawRectangle(50, GetScreenHeight() - 100, (int) width, 40, DARKGREEN);
        DrawRectangleLines(50, GetScreenHeight() - 100, 150, 40, BLACK);
        String s = String.format("HP: %d", player.getHp());
        DrawText(s, 50, GetScreenHeight() - 100, 20, BLACK);
    }

    public void drawBurn(){
        double thing = (double) player.getBurnTicks() / player.getIntialBurn();
        double width = thing * 150;
        DrawRectangle(50, GetScreenHeight() - 200, (int) width, 40, ORANGE);
        DrawRectangleLines(50, GetScreenHeight() - 200, 150, 40, BLACK);
        String s = String.format("BURN: %d", player.getBurnTicks());
        DrawText(s, 50, GetScreenHeight() - 200, 20, BLACK);
    }

    public void drawFireFex(){
        if(player.isFireHex()) {
            player.setColor(ORANGE);
            if(!player.isOnFire()){
                DrawCircle(50,GetScreenHeight() - 180,25,BLACK);
                DrawCircle(50,GetScreenHeight() - 180,10,ORANGE);
            }
            else{
                DrawCircle(230,GetScreenHeight() - 180,25,BLACK);
                DrawCircle(230,GetScreenHeight() - 180,10,ORANGE);
            }
        }
        else{
            player.setColor(RED);
        }
    }

    public void update(EnemyHandler enemy, ProjectileHandler projList) {
//        shoot(projList);
        if (player.getHp() <= 0){
            isAlive = false;
        }
        drawHp();
        if(player.isOnFire()){
            drawBurn();
        }
        drawFireFex();
        drawRange();
    }
}