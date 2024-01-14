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

    public void gotDamagedRanged(ProjectileHandler projList) {
        for (int i = 0; i < projList.size(); i++) {
            Projectile currProj = (Projectile) projList.get(i);
            Jaylib.Vector2 currPos = new Jaylib.Vector2(currProj.getPosX(), currProj.getPosY());
            if (CheckCollisionCircles(player.getPosition(), player.getSize(), currPos, currProj.getShotRad())) {
                if (currProj.getShotTag().toLowerCase().contains("enemy")) {
                    player.setTimeSinceHit(System.currentTimeMillis());
                    enemyShots(currProj, projList);
                }
                else{
                    currProj.setHitPlayer(false);
                }
            }
        }
    }

    public void enemyShots(Projectile currProj, ProjectileHandler projList){

        if(currProj.getShotTag().contains("Pool")) {
            currProj.setyMoveSpeed(0);
            currProj.setxMoveSpeed(0);
            updatePool(currProj,projList);
            if(cooldown.cooldown(150)){
                player.setHp(player.getHp() - currProj.getDamage());
                player.setTimeSinceHit(System.currentTimeMillis());
            }
        }
        else {
            player.setHp(player.getHp() - currProj.getDamage());
            player.setTimeSinceHit(System.currentTimeMillis());
            currProj.setHitPlayer(true);
            if (currProj.getShotTag().contains("Fire")) {
                fire.shootAttack(player);
            }
            if(currProj.getShotTag().contains("Inferno")){
                fire.magicLongShoot(player);
            }
            projList.removeObject(currProj);
        }
    }

//    public void shoot(ProjectileHandler projList){
//        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
//            player.setShooting(true);
//            if (player.canShoot()) {
//                int mouseX = GetMouseX();
//                int mouseY = GetMouseY();
//                Projectile shot = new Projectile(13, player.getPosX(), player.getPosY(), 7, mouseX, mouseY, "Creatures.Player", player.getShotRange(), true, BLACK);
//                shot.setShotTag("Creatures.Player");
//                shot.shootLine();
//                projList.add(shot);
//                player.setCanShoot(false);
//                cooldown(player.getSHOT_COOLDOWN(), "shot");
//            }
//            else {
//                player.setShooting(false);
//            }
//        }
//    }

    public void regen(){
        if(System.currentTimeMillis() - player.getTimeSinceHit() > player.getRegenCooldown()){
            if(cooldown.cooldown(150)){
                if(player.getHp() < player.getInitalHp()) {
                    if(player.getHp() + 10 < player.getInitalHp()) {
                        player.setHp(player.getHp() + 10);
                    }
                    else{
                        player.setHp(player.getInitalHp());
                    }
                }
            }
        }
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
        DrawRectangle(50, 900, (int) width, 40, ORANGE);
        DrawRectangleLines(50, 900, 150, 40, BLACK);
        String s = String.format("BURN: %d", player.getBurnTicks());
        DrawText(s, 50, 900, 20, BLACK);
    }

    public void drawFireFex(){
        if(player.isFireHex()) {
            player.setColor(ORANGE);
            if(!player.isOnFire()){
                DrawCircle(50,920,25,BLACK);
                DrawCircle(50,920,10,ORANGE);
            }
            else{
                DrawCircle(230,920,25,BLACK);
                DrawCircle(230,920,10,ORANGE);
            }
        }
        else{
            player.setColor(RED);
        }
    }

    public void update(EnemyHandler enemy, ProjectileHandler projList) {
//        shoot(projList);
        gotDamagedRanged(projList);
        if (player.getHp() <= 0){
            isAlive = false;
        }
        drawHp();
        if(player.isOnFire()){
            drawBurn();
        }
        drawFireFex();
        drawRange();
        regen();
    }
    public void updatePool(Projectile currProj, ProjectileHandler projList){
        if(currProj.isDraw()) {
            currProj.explodePoolSpell();
        }
        else{
            projList.removeObject(currProj);
        }
    }
}