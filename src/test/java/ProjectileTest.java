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
    void testCheckProjIsOnScreen_PositiveCase() {
        playerProjectile.checkProjIsOnScreen();
        assertTrue(playerProjectile.isInBounds());
    }

    @Test
    void testCheckProjIsOnScreen_FalseCase() {
        playerProjectile.setPosX(-10);
        playerProjectile.setPosY(-10);
        playerProjectile.checkProjIsOnScreen();
        assertFalse(playerProjectile.isInBounds());
    }

    @Test
    void testCheckProjIsOnScreen_True_to_False() {
//        checking that from a start point on screen that the bounds is true and when moves to a point off the screen
//        it becomes false
        playerProjectile.setPosX(10);
        playerProjectile.setPosY(10);
        playerProjectile.shootLine();
        for (int i = 0; i < gameTicks; i++) {
            playerProjectile.updateMove();
            playerProjectile.checkProjIsOnScreen();
            assertTrue(playerProjectile.isInBounds());
        }
        assertFalse(playerProjectile.isInBounds());
    }

}
