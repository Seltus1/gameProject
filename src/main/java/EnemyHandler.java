import java.util.*;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
//UNFINISHED make it so handles all enemies
//TEST
public class EnemyHandler {
    private static ArrayList<Enemy> enemyList = new ArrayList<>();


    public EnemyHandler() {

    }
    public static void spawnEnemies(){
        Random ran = new Random();
        int numEnemiesToSpawn = 10;
        for(int i = 0; i < numEnemiesToSpawn; i++){
            //Why doesnt ran.nextInt()*1080 or 1920 not work? Fix later
            enemyList.add(new Enemy(50,600,700,40));
            enemyList.get(i).update();
        }
    }

    public static ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }
}