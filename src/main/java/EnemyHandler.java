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

    public boolean addMultipleEnemies(int amount){
        for (int i = 0; i < amount; i++) {
//            int randEnemy = rand.nextInt(3) + 1;
            int randEnemy = 3;
            int Xpos = rand.nextInt(1920);
            int Ypos = rand.nextInt(1080);
            int size = 25;
            if(randEnemy == 1){
                StationaryEnemy enemy = new StationaryEnemy(1, 0, Xpos, Ypos, 0, size, GREEN);
                add(enemy);
            }
            else if(randEnemy == 2){
                BrawlerEnemy enemy = new BrawlerEnemy(1, 6, Xpos, Ypos, 3, size, BLUE);
                add(enemy);
            }
            else if(randEnemy == 3){
                FireBrawlerEnemy enemy = new FireBrawlerEnemy(1, 6, Xpos, Ypos,2, size, ORANGE);
                add(enemy);
            }
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
            else if (get(i) instanceof BrawlerEnemy){
                BrawlerEnemy enemy = (BrawlerEnemy) get(i);
                enemy.followPlayer(player);
            }
            if (get(i) instanceof  FireBrawlerEnemy){
                FireBrawlerEnemy enemy = (FireBrawlerEnemy) get(i);
                enemy.followPlayer(player);
                enemy.attack(player);
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