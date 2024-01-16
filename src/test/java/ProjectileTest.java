import Attacks.Projectile;
import Creatures.Enemies.Enemy;
import Creatures.Player;
import Handlers.ProjectileHandler;
import org.junit.jupiter.api.Test;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectileTest {

    Player player = new Player(100, 12, 15, 550, 250, 5, 20, 700, RED);
    Enemy enemy = new Enemy(1, 6, 800, 800, 3, 5, 50, BLUE);
    Projectile playerProjectile = new Projectile(10,player.getPosX(),player.getPosY(),10,enemy.getPosX(),
            enemy.getPosY(),"Player", player.getShotRange(),true,BLACK);

    int gameTicks = 300;

    @Test
    void testShootLine() {
    }
}
