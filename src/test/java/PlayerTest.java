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

    @Test
    void burn_does_not_set_player_on_fire() {
        for (int i = 0; i < gameTicks; i++) {
            player.burn();
        }
        assertFalse(player.isOnFire());
    }

    @Test
    void burn_does_increases_burn_ticks_on_fire() {
        player.setOnFire(true);
        player.setBurnTicks(10);
        for (int i = 0; i < gameTicks; i++) {
            player.burn();
        }
        assertTrue(player.getBurnTicks() < 10);
    }

    @Test
    void brun_deals_damage_every_15_seconds() {
        player.setBurnTicks(10);
        player.setOnFire(true);
        for (int i = 0; i < 150; i++) {
            player.burn();
        }
        assertEquals(0, player.getBurnTicks());
    }

    @Test
    void name() {
    }
}
