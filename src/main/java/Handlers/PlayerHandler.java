package Handlers;
import Creatures.*;
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
    private int cooldown;
    private int cooldown2;
    private int thingy;
    private double xDir;
    private double yDir;
    private double cursorX;
    private double cursorY;

    public PlayerHandler(Player player){
        this.player = player;
        isAlive = true;
        fire = new Fire();

    }


    /**
     * A primitive method that checks for all enemies for collision
     * Soon, implement a circle that is approx 2x the size of our players and only check for collisions when an enemy
     * is inside that circle.
     * @param
     */
    public void enemyCollision(EnemyHandler enemy){
//        ArrayList<Creatures.Enemies.Enemy> enemyList = enemy.getEnemyList();
//        for (int i = 0; i < enemyList.size(); i++) {
//            Jaylib.Vector2 playerPos = new Jaylib.Vector2(player.getPosX(),player.getPosY());
//            Jaylib.Vector2 enemyPos = new Jaylib.Vector2(enemyList.get(i).getPosX(),enemyList.get(i).getPosY());
//            if (CheckCollisionCircles(playerPos,player.getSize(),enemyPos,enemyList.get(i).getSize())){
//                player.setHp((player.getHp()-enemyList.get(i).getDamage()));
//            }
//        }
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
                player.setTimeSinceHit(System.currentTimeMillis());
                if (currProj.getShotTag().contains("Creatures.Enemies.Enemy")) {
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
            }
            else {
                player.setHp(player.getHp() - currProj.getDamage());
                currProj.setHitPlayer(true);
                if (currProj.getShotTag().contains("Elements.Fire")) {
                    fire.shootAttack(player);
                }
                if(currProj.getShotTag().contains("Inferno")){
                    fire.magicLongShoot(player);
                }
                projList.removeObject(currProj);
            }
        }

    public void shoot(ProjectileHandler projList){
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
            player.setShooting(true);
            if (player.canShoot()) {
                int mouseX = GetMouseX();
                int mouseY = GetMouseY();
                Projectile shot = new Projectile(13, player.getPosX(), player.getPosY(), 7, mouseX, mouseY, "Creatures.Player", player.getShotRange(), true, BLACK);
                shot.setShotTag("Creatures.Player");
                shot.shootLine();
                projList.add(shot);
                player.setCanShoot(false);
                cooldown(player.getSHOT_COOLDOWN(), "shot");
            }
            else {
                player.setShooting(false);
            }
        }
    }
    public void regen(){
        if(System.currentTimeMillis() - player.getTimeSinceHit() > player.getRegenCooldown()){
            cooldown2++;
            if((cooldown2 + 1) % 15 == 0){
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

    public void drawInferno(){
        if(player.isInferno()) {
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


    private void cooldown(int cooldown, String type){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(cooldown);
            } catch (InterruptedException e) {
                System.out.println("Woah, something went wrong! (check cooldown method)");
            }
            if(type.equals("shot")){
                player.setCanShoot(true);
            }
            executor.shutdown();
        });
    }

    public void update(EnemyHandler enemy, ProjectileHandler projList) {
        enemyCollision(enemy);
        shoot(projList);
        gotDamagedRanged(projList);
        if (player.getHp() <= 0){
            isAlive = false;
        }
        drawHp();
        if(player.isOnFire()){
            drawBurn();
        }
        drawInferno();
        drawRange();
        regen();
    }
    public void updatePool(Projectile currProj, ProjectileHandler projList){
        if(currProj.isDraw()){
            currProj.setShotRad(50);
            cooldown++;
            if((cooldown + 1) % 15 == 0) {
                player.setHp(player.getHp() - currProj.getDamage());
                player.setTimeSinceHit(System.currentTimeMillis());
                cooldown = 0;
            }
        }
        else {
            projList.removeObject(currProj);
        }
    }

}
