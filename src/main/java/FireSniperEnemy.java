import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class FireSniperEnemy extends SniperEnemy {
    private Fire fire;
    private  int burnCountDown;

    public FireSniperEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int shotRange, int shotSpeed, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, shotRange, shotSpeed, color);

        fire = new Fire();
    }

    public void shoot(Player player, ProjectileHandler projList) {
        super.shootPlayer(player, projList, "Fire_Enemy", RED);
    }
}
