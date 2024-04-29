package Creatures.Enemies.Magic;

import Creatures.Players.Player;
import com.raylib.Raylib;
import Handlers.*;
import Attacks.*;
import Elements.*;

public class FireMagicEnemy extends MagicEnemy {
    private Fire fire;
    private int longCooldown;
    private CooldownHandler cooldown;

    public FireMagicEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int moveRange, int spellRange, int shotSpeed, int numCoins, Raylib.Color color, Raylib.Camera2D camera) {
        super(hp, damage, posX, posY, moveSpeed, size, moveRange, spellRange, shotSpeed, numCoins, color, camera);
        fire = new Fire();
        cooldown = new CooldownHandler();
        longCooldown = 1500;
    }

    public void castInferno(Player player, ProjectileHandler projList, Raylib.Color color, Raylib.Camera2D camera) {
        super.castLongSpell(player,projList,color, "Inferno_Enemy", camera);
    }



    public void castCloseSpell(Player player, ProjectileHandler projList, Raylib.Color color) {

    }

    public void castFireWall(Player player, ProjectileHandler projList, Raylib.Color color, Raylib.Camera2D camera) {
        Projectile wall = new Projectile(4, getPosX(), getPosY(), 5, player.getPosX(), player.getPosY(), "Enemy_Fire_Wall", getSpellRange(), true, camera, color);
        projList.add(wall);
    }
    public void attack(Player player, ProjectileHandler projList, Raylib.Color color, Raylib.Camera2D camera){
        //        int rand = getRand().nextInt(2) + 1;
        int rand = 1;
        if (cooldown.cooldown(longCooldown)) {
            if (rand == 1){
                castInferno(player, projList, color, camera);
            }
            else{
                castFireWall(player, projList, color, camera);
            }
        }
    }
    public void update(Player player, ProjectileHandler projList, Raylib.Color color, Raylib.Camera2D camera) {
        attack(player,projList,color,camera);
        move(player,camera);
        drawHat(camera);
    }
}
