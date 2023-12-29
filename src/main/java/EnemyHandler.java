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
//            int randEnemy = rand.nextInt(2) + 1;
            int randEnemy = 3;
            int Xpos = rand.nextInt(1920);
            int Ypos = rand.nextInt(1080);
            int size = rand.nextInt(46) + 5;
            if(randEnemy == 1){
                StationaryEnemy enemy = new StationaryEnemy(1, 0, Xpos, Ypos, 0, size, GREEN);
                add(enemy);
            }
            else if(randEnemy == 2){
                MeleeEnemy enemy = new MeleeEnemy(1, 0, Xpos, Ypos, 5, size, BLUE);
                add(enemy);
            }
            else if(randEnemy == 3){
                FireMeleeEnemy enemy = new FireMeleeEnemy(1, 5, Xpos, Ypos,2, size, ORANGE);
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
            else if (get(i) instanceof MeleeEnemy){
                MeleeEnemy enemy = (MeleeEnemy) get(i);
                enemy.followPlayer(player);
            }
            if (get(i) instanceof  FireMeleeEnemy){
                FireMeleeEnemy enemy = (FireMeleeEnemy) get(i);
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