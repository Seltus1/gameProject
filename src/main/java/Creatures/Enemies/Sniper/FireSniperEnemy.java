package Creatures.Enemies.Sniper;

import Creatures.Players.Player;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

import Handlers.*;
import Elements.*;

public class FireSniperEnemy extends SniperEnemy {
    private Fire fire;
    private  int burnCountDown;

    public FireSniperEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int shotRange, int shotSpeed, int numCoins, Raylib.Color color, Camera2D camera){
        super(hp, damage, posX, posY, moveSpeed, size, shotRange, shotSpeed, numCoins, color, camera);
        fire = new Fire();
    }

    public void update(Player player, ProjectileHandler projList, Camera2D camera){
        shoot(player, projList, camera);
    }

    public void shoot(Player player, ProjectileHandler projList, Camera2D camera) {
        super.shootPlayer(player, projList, "Fire_Enemy", RED, camera);
    }

}
