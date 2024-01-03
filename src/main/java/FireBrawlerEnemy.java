import com.raylib.Raylib;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.ColorTint;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class FireBrawlerEnemy extends BrawlerEnemy{
    private Fire fire;
    private int burnCountDown;

    public FireBrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, range, color);
        fire = new Fire();
    }

    public void attack(Player player){
//        fire.setBurnTime(10);
        player.setBurnDamage(fire.getBurnDamage());
        super.attack(player);
        if (calculateDistanceToPlayer(player) < fire.getRange()){
            fire.meleeAttack(player);
            player.setFireInRange(true);
            player.setOnFire(true);
        }
        else{
            player.setFireInRange(false);
        }
    }
}