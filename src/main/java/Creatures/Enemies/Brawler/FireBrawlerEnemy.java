package Creatures.Enemies.Brawler;

import Creatures.Enemies.Brawler.BrawlerEnemy;
import com.raylib.Raylib;
import Creatures.*;
import Handlers.*;
import Attacks.*;
import Elements.*;

public class FireBrawlerEnemy extends BrawlerEnemy {
    private int burnCountDown;

    public FireBrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, Raylib.Color color, Raylib.Camera2D camera){
        super(hp, damage, posX, posY, moveSpeed, size, range, color, camera);
    }

    public void attack(Player player, Fire fire){
        super.attack(player);
        if (getVector().distanceToOtherObject(player.getPosX(),player.getPosY()) < fire.getRange()){
            fire.meleeAttack(player);
            player.setFireInRange(true);
            player.setOnFire(true);
            fire.setGettingMeleed(true);
        }

        else{
            player.setFireInRange(false);
            fire.setGettingMeleed(false);
        }
        if(getHp() <= 0){
            fire.setGettingMeleed(false);
        }
    }
}