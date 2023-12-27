import java.util.*;
import java.util.Random;

import static com.raylib.Jaylib.PURPLE;
import static com.raylib.Raylib.DrawCircle;

//UNFINISHED make it so handles all enemies
//TEST
public class EnemyHandler extends ListHandler {

    private Random rand = new Random();

    public EnemyHandler() {
        super();
    }

    public boolean addMultipleEnemies(int amount) {
        for (int i = 0; i < amount; i++) {
            int Xpos = rand.nextInt(1920); // Random X position between 0 and 1919
            int Ypos = rand.nextInt(1080); // Random Y position between 0 and 1079
            int size = rand.nextInt(46) + 5; // Random size between 5 and 50 (inclusive)
            Enemy enemy = new Enemy(1, Xpos, Ypos, size);
            add(enemy);
        }
        return true;
    }

    public ArrayList<Enemy> getEnemyList(){
        return this.getList();
    }

    public void update(ProjectileHandler projList) {
        for (int i = 0; i < size(); i++) {
            Enemy enemy = (Enemy) get(i);
            enemy.gotDamagedRanged(projList);
            if (!enemy.getIsAlive()){
                removeIndex(i);
            }
            else{
                DrawCircle(enemy.getPosX(), enemy.getPosY(), enemy.getSize(), PURPLE);
            }
        }
    }
}