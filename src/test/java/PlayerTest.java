import Creatures.Player;
import org.junit.jupiter.api.Test;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    Player getPlayer = new Player(10, 10, 10, 10, 10, 10, 10, 10, BLACK);
    Player setPlayer = new Player(0, 0, 0, 0, 0, 0, 0, 0, BLACK);
    Player player = new Player(100, 12, 15, 550, 250, 5, 20, 700, RED);
    int gameTicks = 300; // 5 seconds for 60 FPS
    @Test
    void get_hp() {
        assertEquals(getPlayer.getHp(), 10);
    }

    @Test
    void set_hp() {
        setPlayer.setHp(10);
        assertEquals(setPlayer.getHp(), getPlayer.getHp());
    }
}
