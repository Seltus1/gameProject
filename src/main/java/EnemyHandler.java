import java.util.*;
import java.util.Random;

import static com.raylib.Jaylib.PURPLE;
import static com.raylib.Raylib.DrawCircle;

//UNFINISHED make it so handles all enemies
//TEST
public class EnemyHandler {
    private ArrayList<Enemy> list;

    private Random rand = new Random();

    public EnemyHandler() {
        list = new ArrayList<>();
    }

    public boolean addMultipleEnemies(int amount) {
        for (int i = 0; i < amount; i++) {
            int Xpos = rand.nextInt(1920); // Random X position between 0 and 1919
            int Ypos = rand.nextInt(1080); // Random Y position between 0 and 1079
            int size = rand.nextInt(46) + 5; // Random size between 5 and 50 (inclusive)
            Enemy enemy = new Enemy(1, Xpos, Ypos, size);
            list.add(enemy);
        }
        return true;
    }

    public void update(ProjectileHandler projList) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).gotDamagedRanged(projList);
            if (!list.get(i).getIsAlive()){
                list.remove(i);
            }
            else{
                DrawCircle(list.get(i).getPosX(), list.get(i).getPosY(), list.get(i).getSize(), PURPLE);
            }
        }
    }
}