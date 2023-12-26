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
            int Xpos = rand.nextInt(0, 1920);
            int Ypos = rand.nextInt(0, 1080);
            int size = rand.nextInt(5, 50);
            Enemy enemy = new Enemy(200, Xpos, Ypos, size);
            add(enemy);
            System.out.println(1);
        }
        return true;
    }

    public void update(ProjectileHandler projList){
        for (Enemy enemy: list){
            enemy.gotDamagedRanged(projList);
            enemy.update();
        }
    }
}