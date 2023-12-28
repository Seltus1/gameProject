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



    public void update(EnemyHandler enemy) {
        enemyCollision(enemy);
        if (player.getHp() <= 0){
            isAlive = false;
        }
        if (!isAlive){
            DrawText("you suck",300,600,20,RED);
        }
    }

}
