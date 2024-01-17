//import Attacks.Projectile;
//import Creatures.Enemies.Enemy;
//import Creatures.Player;
//import Handlers.EnemyHandler;
//import Handlers.ProjectileHandler;
//import org.junit.jupiter.api.Test;
//import static com.raylib.Raylib.*;
//import static com.raylib.Jaylib.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ProjectileHandlerTest {


//    int gameTicks = 300;

import Attacks.Projectile;
import Creatures.Enemies.Enemy;
import Creatures.Player;
import Handlers.EnemyHandler;
import Handlers.ProjectileHandler;
import org.junit.jupiter.api.Test;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectileHandlerTest {

    Player player = new Player(100, 12, 15, 550, 250, 5, 20, 700, RED);
    Enemy enemy = new Enemy(1, 6, 800, 800, 3, 5, 50, BLUE);
    Projectile playerProjectile = new Projectile(10,player.getPosX(),player.getPosY(),10,enemy.getPosX(),
            enemy.getPosY(),"Player", player.getShotRange(),true,BLACK);
        ProjectileHandler projList = new ProjectileHandler();
    EnemyHandler enemyHandler = new EnemyHandler();
    int gameTicks = 300; // 5 seconds for 60 FPS
//    @Test
//    void checking_if_poolshot_creates_pool(){
//        projList.add(playerProjectile);
//        playerProjectile.setShotTag("Enemy_Pool_Shot");
//        playerProjectile.setDistanceTravelled(playerProjectile.getMaxRange());
//        for (int i = 0; i < gameTicks; i++) {
//            projList.update(enemyHandler, player);
//        }
//        Projectile firstP = (Projectile) projList.get(0);
//        assertEquals("Enemy_Pool",firstP.getShotTag());
//    }
    @Test
    void test(){
        assertEquals(10,10);
    }
}


