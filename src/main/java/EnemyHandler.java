import java.util.*;
import java.util.Random;

import static com.raylib.Jaylib.PURPLE;
import static com.raylib.Raylib.DrawCircle;

//UNFINISHED make it so handles all enemies
//TEST
public class EnemyHandler extends ListHandler {
    private ArrayList<Enemy> list = getList();
    private Random rand = new Random();

    public EnemyHandler() {
        super();
    }
    public boolean addMultipleEnemies(int amount){
        for (int i = 0; i < amount; i++) {
            int Xpos = rand.nextInt(1920); // Random X position between 0 and 1919
            int Ypos = rand.nextInt(1080); // Random Y position between 0 and 1079
            int size = rand.nextInt(46) + 5; // Random size between 5 and 50 (inclusive)
            Enemy enemy = new Enemy(200, Xpos, Ypos, size);
            add(enemy);
        }
        return true;
    }

    public void update(ProjectileHandler projList){
        // Loop through the list of enemies
        for (Enemy enemy : list) {
            // Call the 'gotDamagedRanged' method on each enemy, passing the ProjectileHandler
            enemy.gotDamagedRanged(projList);

            // Update the current enemy
            enemy.update();
        }
    }
}