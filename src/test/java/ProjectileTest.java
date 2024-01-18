import Attacks.Projectile;
import Handlers.Vector2D;
import org.junit.jupiter.api.Test;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectileTest {

    Projectile playerProjectile = new Projectile(10,550,250,10, new Vector2D(550,250, 10)
            ,"Player", 1000,true,BLACK);


    int gameTicks = 300;

    @Test
    void testCheckProjIsOnScreen_PositiveCase() {
        InitWindow(1000,1000,"TEST");
        playerProjectile.checkProjIsOnScreen();
        assertTrue(playerProjectile.isInBounds());
        CloseWindow();
    }

    @Test
    void testCheckProjIsOnScreen_FalseCase() {
        InitWindow(1000,1000,"TEST");
        playerProjectile.setPosX(-10);
        playerProjectile.setPosY(-10);
        playerProjectile.checkProjIsOnScreen();
        assertFalse(playerProjectile.isInBounds());
        CloseWindow();

    }

    @Test
    void testCheckProjIsOnScreen_True_to_False() {
//        checking that from a start point on screen that the bounds is true and when moves to a point off the screen
//        it becomes false
        InitWindow(1000,1000,"TEST");

        playerProjectile.setPosX(10);
        playerProjectile.setPosY(10);
        playerProjectile.createShotLine();
        for (int i = 0; i < gameTicks; i++) {
            playerProjectile.updateMove();
            playerProjectile.checkProjIsOnScreen();
            assertTrue(playerProjectile.isInBounds());
        }
        assertFalse(playerProjectile.isInBounds());
        CloseWindow();


    }

}
