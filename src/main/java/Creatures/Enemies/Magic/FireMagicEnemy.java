package Creatures.Enemies.Magic;

import com.raylib.Raylib;
import Creatures.*;
import Handlers.*;
import Attacks.*;
import Elements.*;

public class FireMagicEnemy extends MagicEnemy {
    private Fire fire;
    private int longCooldown;

    public FireMagicEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int moveRange, int spellRange, int shotSpeed, Raylib.Color color, Raylib.Camera2D camera) {
        super(hp, damage, posX, posY, moveSpeed, size, moveRange, spellRange, shotSpeed, color, camera);
        fire = new Fire();
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
    public void update(Player player, ProjectileHandler projList, Raylib.Color color, Raylib.Camera2D camera) {
        longCooldown++;
//        int rand = getRand().nextInt(2) + 1;
        int rand = 1;
        if ((longCooldown + 1) % 91 == 0) {
            if (rand == 1){
                castInferno(player, projList, color, camera);
            }
            else{
                castFireWall(player, projList, color, camera);
            }
            longCooldown = 0;
        }
    }
}
