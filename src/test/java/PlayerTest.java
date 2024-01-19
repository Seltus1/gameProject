import Attacks.Projectile;
import Creatures.Enemies.Enemy;
import Creatures.Player;
import Handlers.ProjectileHandler;
import org.junit.jupiter.api.Test;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    Player getPlayer = new Player(10, 10, 10, 10, 10, 10, 10, 10, BLACK);
    Player setPlayer = new Player(0, 0, 0, 0, 0, 0, 0, 0, BLACK);
    Player player = new Player(100, 12, 15, 550, 250, 5, 20, 700, RED);
    int gameTicks = 300; // 5 seconds for 60 FPS
    ProjectileHandler projectileList = new ProjectileHandler();
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
    void brun_Increases_Brun_ticks() {
        player.setOnFire(true);
        player.setBurnTicks(10);
        for (int i = 0; i < gameTicks; i++) {
            player.burn();
        }
        assertTrue(player.getBurnTicks() < 10);
    }

    @Test
    void Brun_Deals_Damage_Every_Brun_Cooldown() {
        player.setBurnTicks(10);
        player.setOnFire(true);
        for (int i = 0; i < 150; i++) {
            player.burn();
        }
        assertEquals(0, player.getBurnTicks());
    }

    @Test
    void brun_deals_correct_amount_of_damage() {
        player.setBurnTicks(10);
        player.setOnFire(true);
        for (int i = 0; i < gameTicks; i++) {
            player.burn();
        }
        assertEquals(90, player.getHp());
    }

    @Test
    void fireHex_does_not_set_fireHex(){
        player.setFireHex(true);
        player.setFireHexCount(10);
        for (int i = 0; i < gameTicks * 2; i++) {
            player.fireHex();
        }
        assertFalse(player.isFireHex());
    }

    @Test
    void fireHex_does_not_increase_fireHexCount() {
        player.setFireHex(true);
        player.setFireHexCount(10);
        for (int i = 0; i < gameTicks; i++) {
            player.fireHex();
        }
        assertTrue(player.getFireHexCount() < 10);
    }

    @Test
    void fireHex_increase_burnTicket_when_shooting_and_never_goes_over_max_burn_ticks() {
        player.setFireHex(true);
        player.setFireHexCount(10);
        for (int i = 0; i < 6; i++) {
            if (player.getBurnTicks() == 10) {
                assertEquals(10, player.getBurnTicks());
                break;
            }
            int originalBrunTicks = player.getBurnTicks();
            player.setShooting(true);
            player.fireHex();
            assertTrue(player.getBurnTicks() > originalBrunTicks);
        }
    }

    @Test
    void checking_to_see_if_shoot_add_projectile_to_projList() {
//        player.shoot(projectileList);
        assertEquals(1, projectileList.size());
    }

    @Test
    void checking_fireHex_applies_when_fireHexed_and_shooting() {
        player.setFireHex(true);
        player.setFireHexCount(10);
        for (int i = 0; i < 1; i++) {
//            player.shoot(projectileList);
            player.fireHex();
        }
        assertEquals(3, player.getBurnTicks());
        assertTrue(player.isOnFire());
    }

    @Test
    void checking_fireHex_applies_when_fireHexed_and_shooting_multiple_times() {
        player.setFireHexCount(10);
        player.setFireHex(true);
        for (int i = 0; i < 45; i++) {
//            player.shoot(projectileList);
            player.shotCooldown();
            player.fireHex();
            player.setShooting(false);
        }
        assertEquals(9, player.getBurnTicks());
    }

    @Test
    void check_fixHex_applies_while_shooting_and_also_deals_brun_damage() {
        player.setFireHexCount(10);
        player.setFireHex(true);
        for (int i = 0; i < gameTicks; i++) {
//            player.shoot(projectileList);
            player.shotCooldown();
            player.fireHex();
            player.burn();
            player.setShooting(false);
        }
        assertEquals(80, player.getHp());
    }

    @Test
    void check_shotCooldown_works_properly() {
        for (int i = 0; i < gameTicks; i++) {
//            player.shoot(projectileList);
            player.shotCooldown();
        }
        assertEquals(20, projectileList.size());
    }
    @Test
    void check_regenCooldown_works_properly(){
        player.setHp(7);
        for(int i = 0; i < gameTicks + 28; i++){
            player.regen();
        }
        assertEquals(37, player.getHp());
    }

    @Test
    void check_regen_cooldown_works_properly(){

    }
}
