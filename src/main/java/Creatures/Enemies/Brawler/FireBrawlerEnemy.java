package Creatures.Enemies.Brawler;

import Creatures.Enemies.Brawler.BrawlerEnemy;
import com.raylib.Raylib;
import Creatures.*;
import Handlers.*;
import Attacks.*;
import Elements.*;

public class FireBrawlerEnemy extends BrawlerEnemy {
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