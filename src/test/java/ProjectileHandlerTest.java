import Attacks.Projectile;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import Handlers.EnemyHandler;
import Handlers.ProjectileHandler;
import Handlers.VectorHandler;
import org.junit.jupiter.api.Test;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectileHandlerTest {


    Projectile playerProjectile = new Projectile(10,550,250,10, new VectorHandler(550,250, 10)
            , "Creatures/Players", 1000,true,BLACK);
    ProjectileHandler projList = new ProjectileHandler();
    EnemyHandler enemyHandler = new EnemyHandler();
    Player player = new Player(100, 12, 15, 550, 250, 5, 20, 700, RED);



    int gameTicks = 300; // 5 seconds for 60 FPS

    @Test
    void checking_if_poolshot_creates_pool() {
        projList.add(playerProjectile);
        playerProjectile.setShotTag("Enemy_Pool_Shot");
        playerProjectile.setDistanceTravelled(playerProjectile.getMaxRange());
        for (int i = 0; i < 1; i++) {
            projList.update(enemyHandler, player);
        }
        Projectile firstP = (Projectile) projList.get(0);
        assertEquals("Enemy_Pool", firstP.getShotTag());
    }

    @Test
    void pool_gets_removed_after_drawTime(){
        InitWindow(1000,1000,"TEST");

        projList.add(playerProjectile);
        playerProjectile.setShotTag("Enemy_Pool_Shot");
        playerProjectile.setDistanceTravelled(playerProjectile.getMaxRange());
//        iterate for the cooldown (150)
        for (int i = 0; i < 151; i++) {
            projList.update(enemyHandler, player);
        }
        assertEquals(0, projList.size());
        CloseWindow();
    }

    @Test
    void Player_fire_proj_sets_Enemy_on_fire(){
        InitWindow(1000,1000,"TEST");
        Enemy enemy = new Enemy(100, 12, 700, 250, 5, 20, 700, RED);
        enemyHandler.add(enemy);
        enemy.setOnFire(false);
        projList.add(playerProjectile);
        playerProjectile.setShotTag("Enemy_Fire");
        playerProjectile.setFinalX(enemy.getPosX());
        playerProjectile.setFinalY(enemy.getPosY());
        playerProjectile.createShotLine();
        for(int i = 0; i < gameTicks; i++) {
            projList.update(enemyHandler, player);
        }
        assertTrue(enemy.isOnFire());
        CloseWindow();
    }
}
