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
            int randEnemy = rand.nextInt(4) + 1;
//            int randEnemy = 4;
            int Xpos = rand.nextInt(1920);
            int Ypos = rand.nextInt(1080);
            int size = 25;
            if(randEnemy == 1){
                StationaryEnemy enemy = new StationaryEnemy(1, 0, Xpos, Ypos, 0, size, 5, 35, GREEN);
                add(enemy);
            }
            else if(randEnemy == 2){
                BrawlerEnemy enemy = new BrawlerEnemy(1, 6, Xpos, Ypos, 3, size, 50, BLUE);
                add(enemy);
            }
            else if(randEnemy == 3){
                FireBrawlerEnemy enemy = new FireBrawlerEnemy(1, 3, Xpos, Ypos,3, size, 75, ORANGE);
                add(enemy);
            }
            else if (randEnemy == 4){
                MagicEnemy enemy = new MagicEnemy(1, 4, Xpos, Ypos, 3, size, 800,15, PURPLE);
                add(enemy);
            }
        }
        return true;
    }

    public ArrayList<Enemy> getEnemyList(){
        return getList();
    }

    public void update(ProjectileHandler projList, Player player) {
        int counter = 0;
        int falseCounter = 0;
        for (int i = 0; i < size(); i++) {
            if (get(i) instanceof StationaryEnemy){
                StationaryEnemy enemy = (StationaryEnemy) get(i);
                enemy.shootPlayer(player, projList);
            }
            else if (get(i) instanceof BrawlerEnemy){
                BrawlerEnemy enemy = (BrawlerEnemy) get(i);
                if (enemy.getRange() < enemy.calculateDistanceToPlayer(player)){
                    enemy.followPlayer(player, "to");
                }
                enemy.attack(player);
            }
            if (get(i) instanceof  FireBrawlerEnemy) {
                counter++;
                FireBrawlerEnemy enemy = (FireBrawlerEnemy) get(i);
                enemy.attack(player);
                if (enemy.calculateDistanceToPlayer(player) >= enemy.getRange()){
                    falseCounter++;
                }
            }
            if (get(i) instanceof MagicEnemy){
                MagicEnemy enemy = (MagicEnemy) get(i);
                if (enemy.calculateDistanceToPlayer(player) > enemy.getRange() / 1.5){
                    enemy.followPlayer(player, "to");
                }
                else if (enemy.calculateDistanceToPlayer(player) < enemy.getRange() / 2){
                    enemy.followPlayer(player, "away");
                }
                enemy.castSpell(player, projList);
            }
            Enemy enemy = (Enemy) get(i);
            enemy.gotDamagedRanged(projList);
            if (!enemy.isAlive()){
                removeIndex(i);
            }
            else{
                DrawCircle(enemy.getPosX(), enemy.getPosY(), enemy.getSize(), enemy.getColor());
            }
            if (falseCounter == counter){
                player.setFireInRange(false);
            }
            else {
                player.setFireInRange(true);
            }
        }
    }
}