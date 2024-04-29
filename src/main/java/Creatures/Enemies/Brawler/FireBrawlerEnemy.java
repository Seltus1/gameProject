package Creatures.Enemies.Brawler;

import Creatures.Players.Player;
import com.raylib.Raylib;
import Elements.*;

public class FireBrawlerEnemy extends BrawlerEnemy {
    private int burnCountDown;

    public FireBrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, int numCoins, Raylib.Color color, Raylib.Camera2D camera){
        super(hp, damage, posX, posY, moveSpeed, size, range, numCoins, color, camera);
    }

    public void attack(Player player, Fire fire, Raylib.Camera2D camera){
        super.attack(player, camera);
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