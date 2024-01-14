import Attacks.Projectile;
import Creatures.Player;
import Handlers.ProjectileHandler;
import org.junit.jupiter.api.Test;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectileTest {
    Projectile projectile1 = new Projectile(10,10,10,10,100,100,"Enemy",
            100,true,BLACK);
    Projectile projectile2 = new Projectile(10,10,10,10,100,100,"Enemy",
            100,true,BLACK);
    Projectile projectile3 = new Projectile(10,10,10,10,100,100,"Enemy",
            100,true,BLACK);
    int gameTicks = 300;

    @Test
    void checking_triangleShot_moves_projectiles(){
        for(int i = 0; i < gameTicks;i++){
            projectile1.triangleShot("above");
            projectile2.triangleShot("below");
        }
//        assertEquals()
    }
}
