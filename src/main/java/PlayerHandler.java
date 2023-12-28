import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class PlayerHandler {
    private Player player;
    private boolean isAlive;

    public PlayerHandler(Player player){
        this.player = player;
        isAlive = true;
    }

    /**
     * A primitive method that checks for all enemies for collision
     * Soon, implement a circle that is approx 2x the size of our players and only check for collisions when an enemy
     * is inside that circle.
     * @param
     */
    public void enemyCollision(EnemyHandler enemy){
        ArrayList<Enemy> enemyList = enemy.getEnemyList();
        for (int i = 0; i < enemyList.size(); i++) {
            if (CheckCollisionCircles(player.getPos(),player.getSize(),enemyList.get(i).getPos(),enemyList.get(i).getSize())){
                player.setHp((player.getHp()-enemyList.get(i).getDamage()));
            }
        }
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



    public void update(EnemyHandler enemy, ProjectileHandler projList) {
        //enemyCollision(enemy);
        gotDamagedRanged(projList);
        if (player.getHp() <= 0){
            isAlive = false;
        }
        if (!isAlive){
            DrawText("you suck",300,600,20,RED);
        }
        System.out.println(player.getHp());
    }

}
