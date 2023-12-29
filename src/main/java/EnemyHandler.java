import java.util.*;
import java.util.Random;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

//UNFINISHED make it so handles all enemies
//TEST
public class EnemyHandler extends ListHandler {

    private Random rand = new Random();

    public EnemyHandler() {
        super();
    }

    public boolean addMultipleEnemies(int amount) {
        for (int i = 0; i < amount; i++) {
            int Xpos = rand.nextInt(1920);
            int Ypos = rand.nextInt(1080);
            int size = rand.nextInt(26) + 20;
            Enemy enemy = new Enemy(1, 5, 12, Xpos, Ypos, 5, size, PURPLE);
            add(enemy);
        }
        return true;
    }


    public boolean addMultipleStationaryEnemy(int amount){
        for (int i = 0; i < amount; i++) {
            int Xpos = rand.nextInt(1920);
            int Ypos = rand.nextInt(1080);
            int size = rand.nextInt(46) + 5;
            StationaryEnemy enemy = new StationaryEnemy(1, 0, 10000, Xpos, Ypos,0, size, GREEN);
            add(enemy);
        }
        return true;
    }

    public ArrayList<Enemy> getEnemyList(){
        return getList();
    }

    public void update(ProjectileHandler projList, Player player) {
        for (int i = 0; i < size(); i++) {
            if (get(i) instanceof StationaryEnemy){
                StationaryEnemy enemy = (StationaryEnemy) get(i);
                enemy.shootPlayer(player, projList);
            }
            Enemy enemy = (Enemy) get(i);
            enemy.gotDamagedRanged(projList);
            if (!enemy.isAlive()){
                removeIndex(i);
            }
            else{
                DrawCircle(enemy.getPosX(), enemy.getPosY(), enemy.getSize(), enemy.getColor());
            }
        }

    }


}