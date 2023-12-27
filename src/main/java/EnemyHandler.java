import java.util.*;
import java.util.Random;
//UNFINISHED make it so handles all enemies
//TEST
public class EnemyHandler {
    private static ArrayList<Enemy> enemyList;
    private static Random rand = new Random();


    public EnemyHandler() {
        enemyList = new ArrayList<>();
    }

    public boolean addEnemy(Enemy e){
        enemyList.add(e);
        return true;
    }

    public boolean addMultipleEnemies(int amount){
//        Random rand = new Random();
        for (int i = 0; i < amount; i++) {
            int Xpos = rand.nextInt(0, 1920);
            int Ypos = rand.nextInt(0, 1080);
            int size = rand.nextInt(5, 50);
            Enemy enemy = new Enemy(200, Xpos, Ypos, size);
            addEnemy(enemy);
        }
        return true;
    }

    public void update(){
        for (Enemy enemy: enemyList){
            enemy.update();
        }
    }
}