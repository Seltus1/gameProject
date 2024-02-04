package Creatures.Enemies.Brawler;

import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import com.raylib.Raylib;
import Handlers.*;
import Attacks.*;

public class BrawlerEnemy extends Enemy {
    private MeleeAttack melee;
    private CooldownHandler attackCD;
    private int attackCooldown;


    public BrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, Raylib.Color color, Raylib.Camera2D camera){
        super(hp, damage, posX, posY, moveSpeed, size, range, color, camera);
        attackCooldown = 1000;
        attackCD = new CooldownHandler();
    }

    public void attack(Player player){
        int distance = getVector().distanceToOtherObject(player.getPosX(),player.getPosY());
        if(distance <= getRange()){
            if(attackCD.cooldown(attackCooldown)) {
                player.setHp(player.getHp() - getDamage());
                player.getRegenCooldown().setCurrentFrame(0);
            }
        }
    }
    public void move(Player player, Raylib.Camera2D camera){
        if (50 < getVector().distanceToOtherObject(player.getPosX(),player.getPosY()) &&!isRandMoving()) {
            getVector().moveObject(player.getPosition(), "to", camera);
            getVector().setHasAlrdyBooleanMoved(false);
        }
        else{
            getVector().randEnemyMove(player,this,50, camera);
        }
    }
    public void update(Player player, Raylib.Camera2D camera){
        move(player,camera);
        attack(player);
    }
}
