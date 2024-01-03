import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import java.util.*;

public class PlayerHandler {
    private Player player;
    private boolean isAlive;
    private Fire fire;
    private int cooldown;
    private int thingy;
    private double xDir;
    private double yDir;
    private double cursorX;
    private double cursorY;
    private Date date;

    public PlayerHandler(Player player){
        this.player = player;
        isAlive = true;
        fire = new Fire();
        date = new Date();

    }

    public void playerIframe(int iFrame){
        try {
            Thread.sleep(iFrame);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A primitive method that checks for all enemies for collision
     * Soon, implement a circle that is approx 2x the size of our players and only check for collisions when an enemy
     * is inside that circle.
     * @param
     */
    public void enemyCollision(EnemyHandler enemy){
//        ArrayList<Enemy> enemyList = enemy.getEnemyList();
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
        Raylib.Vector2 playerPos = player.getPos();
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

    private int horizontalCheck(int xValues){
        int left = 0;
        int right = 0;
        if (xValues < 0){
            left = 1;
        }
        if (xValues > 0){
            right = 1;
        }
        return right - left;
    }

    private int verticalCheck(int yValues){
        int up = 0;
        int down = 0;
        if (yValues < 0){
            up = 1;
        }
        if (yValues > 0){
            down = 1;
        }
        return down - up;
    }
    public void gotDamagedRanged(ProjectileHandler projList) {
        for (int i = 0; i < projList.size(); i++) {
            Projectile currProj = (Projectile) projList.get(i);
            Jaylib.Vector2 currPos = new Jaylib.Vector2(currProj.getPosX(), currProj.getPosY());
            //lol without this, the collision bounds never got moved
            Jaylib.Vector2 playerPos = new Jaylib.Vector2(player.getPosX(), player.getPosY());
            if (CheckCollisionCircles(playerPos, player.getSize(), currPos, currProj.getShotRad())) {
                player.setTimeSinceHit(System.currentTimeMillis());
                if (currProj.getShotTag().contains("Enemy")) {
                    if(currProj.getShotTag().contains("Pool")) {
                        currProj.setyMoveSpeed(0);
                        currProj.setxMoveSpeed(0);
                        updatePool(currProj,projList);
                    }
                    else {
                        player.setHp(player.getHp() - ((Projectile) projList.get(i)).getDamage());
                        ((Projectile) projList.get(i)).setHitPlayer(true);
                        if (currProj.getShotTag().contains("Fire")) {
                            fire.shootAttack(player, currProj);
                        }
                        projList.removeObject(currProj);
                    }
                }
                else {
                    ((Projectile) projList.get(i)).setHitPlayer(false);
                }

                }
            }
        }

    public void shoot(ProjectileHandler projList){
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT) && player.canShoot()) {
            int playerXPos = GetMouseX();
            int playerYPos = GetMouseY();
            Projectile shot = new Projectile(13, player.getPosX(), player.getPosY(), 7, playerXPos, playerYPos, "Player", player.getShotRange(), BLACK);
            shot.setShotTag("Player");
            shot.shootLine();
            projList.add(shot);
            player.setCanShoot(false);
            cooldown(player.getSHOT_COOLDOWN(), "shot");
        }
    }
    public void regen(){
        if(System.currentTimeMillis() - player.getTimeSinceHit() > 5000){
            if(player.getHp() < player.getInitalHp()) {
                player.setHp(player.getHp() + 1);
            }
        }
    }

    public void drawHp(){
        double thing = (double) player.getHp() /  player.getInitalHp();
        double width = thing * 150;
        DrawRectangle(50, 1000, (int) width, 40, DARKGREEN);
        DrawRectangleLines(50, 1000, 150, 40, BLACK);
        String s = String.format("HP: %d", player.getHp());
        DrawText(s, 50, 1000, 20, BLACK);
    }

    public void drawBurn(){
        double thing = (double) player.getBurnTicks() / player.getIntialBurn();
        double width = thing * 150;
        DrawRectangle(50, 900, (int) width, 40, ORANGE);
        DrawRectangleLines(50, 900, 150, 40, BLACK);
        String s = String.format("BURN: %d", player.getBurnTicks());
        DrawText(s, 50, 900, 20, BLACK);
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
