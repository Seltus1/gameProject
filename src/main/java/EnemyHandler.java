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
            int randEnemy = rand.nextInt(5) + 1;
//            int randEnemy = 4;
            int Xpos = rand.nextInt(1920);
            int Ypos = rand.nextInt(1080);
            int size = 25;
            if(randEnemy == 1){
                SniperEnemy enemy = new SniperEnemy(1, 0, Xpos, Ypos, 0, size, 1300, 35, GREEN);
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
                MagicEnemy enemy = new MagicEnemy(1, 4, Xpos, Ypos, 2, size, 800, 550, 15, PURPLE);
                add(enemy);
            }
            else if(randEnemy == 5){
                FireSniperEnemy enemy = new FireSniperEnemy(1, 0, Xpos, Ypos, 0, size, 1300, 35, ColorFromHSV(29,1,1));
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
            if (get(i) instanceof SniperEnemy){
                if(get(i) instanceof  FireSniperEnemy){
                    FireSniperEnemy enemy = (FireSniperEnemy) get(i);
                    enemy.shoot(player,projList);
                }
                else {
                    SniperEnemy enemy = (SniperEnemy) get(i);
                    enemy.shootPlayer(player, projList, "Enemy", BLACK);
                }

            }
            else if (get(i) instanceof BrawlerEnemy){
                if (get(i) instanceof  FireBrawlerEnemy) {
                    counter++;
                    FireBrawlerEnemy enemy = (FireBrawlerEnemy) get(i);
                    enemy.attack(player);
                    if (enemy.calculateDistanceToPlayer(player) >= enemy.getRange()){
                        falseCounter++;
                    }
                }
                BrawlerEnemy enemy = (BrawlerEnemy) get(i);
                if (enemy.getRange() < enemy.calculateDistanceToPlayer(player)){
                    if (!enemy.collisionWIthOtherEnemy(getEnemyList(),player,"to")){
                        enemy.followPlayer(player,"to");
                        enemy.attack(player);
                    }
                    else {
                        DrawCircle((int)enemy.getActualXPos(),(int)enemy.getActualYPos(),enemy.getSize(),enemy.getColor());
                    }
                }
                enemy.attack(player);
            }
            if (get(i) instanceof MagicEnemy){
                MagicEnemy enemy = (MagicEnemy) get(i);
                enemy.update(player, projList, DARKPURPLE);
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