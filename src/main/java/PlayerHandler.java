import com.raylib.Jaylib;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class PlayerHandler {
    private Player player;
    private boolean isAlive;

    public PlayerHandler(Player player){
        this.player = player;
        isAlive = true;
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

    public void gotDamagedRanged(ProjectileHandler projList) {
        for (int i = 0; i < projList.size(); i++) {
            Projectile currProj = (Projectile) projList.get(i);
            Jaylib.Vector2 currPos = new Jaylib.Vector2(currProj.getPosX(), currProj.getPosY());
            //lol without this, the collision bounds never got moved
            Jaylib.Vector2 playerPos = new Jaylib.Vector2(player.getPosX(),player.getPosY());
            if (CheckCollisionCircles(playerPos, player.getSize(), currPos, currProj.getShotRad()) && currProj.getShotTag().equals("Enemy")) {
                player.setHp(player.getHp()-((Projectile) projList.get(i)).getDamage());
                projList.removeIndex(i);
            }
        }
    }
    public void shoot(ProjectileHandler projList){
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT) && player.canShoot()) {
            int playerXPos = GetMouseX();
            int playerYPos = GetMouseY();
            Projectile shot = new Projectile(13, player.getPosX(), player.getPosY(), 7, playerXPos, playerYPos);
            shot.setShotTag("Player");
            shot.vectorCalculations();
            projList.add(shot);
            player.setCanShoot(false);
            cooldown(player.getSHOT_COOLDOWN(), "shot");
        }
        projList.checkProjectilesBounds();
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
        double thing = (double) player.getBurnDmgCount() / player.getIntialBurn();
        double width = thing * 150;
        DrawRectangle(50, 900, (int) width, 40, ORANGE);
        DrawRectangleLines(50, 900, 150, 40, BLACK);
        String s = String.format("BURN: %d", player.getBurnDmgCount());
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
        if (!isAlive){
            DrawText("you suck",300,600,20,RED);
        }
        drawHp();
        if(player.isOnFire()){
            drawBurn();
        }
    }

}
