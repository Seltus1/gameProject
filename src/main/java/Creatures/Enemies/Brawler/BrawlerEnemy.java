package Creatures.Enemies.Brawler;

import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import static com.raylib.Jaylib.*;
import Handlers.*;
import Attacks.*;
import Creatures.Players.Player;

public class BrawlerEnemy extends Enemy {
    private MeleeAttack melee;
    private CooldownHandler attackCD;
    private int attackCooldown;
    private CooldownHandler attackingTime;
    private int speedWhileAttacking;
    private int attackTimeInMilli;
    private boolean shouldAttack;
    private boolean isAttacking;
    private HealthHandler playerHp;
    private CooldownHandler dealDamageCD;
    private int intervalBetweenAttacks;
    private boolean directionLocked;

    public BrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, Raylib.Color color, Raylib.Camera2D camera){
        super(hp, damage, posX, posY, moveSpeed, size, range, color, camera);
        attackCooldown = 1000;
        attackCD = new CooldownHandler();
        attackingTime = new CooldownHandler();
        speedWhileAttacking = getMoveSpeed() * 2;
        attackTimeInMilli = 1000;
        playerHp = new HealthHandler();
        intervalBetweenAttacks = 500;
        dealDamageCD = new CooldownHandler();
    }

    public void attack(Player player, Raylib.Camera2D camera){
        int distance = getVector().distanceToOtherObject(player.getPosX(),player.getPosY());
        if(distance <= getRange()){
            if(attackCD.cooldown(attackCooldown)) {
//                player.setHp(player.getHp() - getDamage());
//                player.getRegenCooldown().setCurrentFrame(0);
                shouldAttack = true;

            }
            lungeAttack(player,camera);
            dealDamageIfCollided(player);
        }
        else {
            directionLocked = false;
        }
    }
    public void move(Player player, Raylib.Camera2D camera){
        if(!directionLocked) {
            if (50 < getVector().distanceToOtherObject(player.getPosX(), player.getPosY()) && !isRandMoving()) {
                getVector().moveObject(player.getPosition(), "to", camera);
                getVector().setHasAlrdyBooleanMoved(false);
            } else {
                getVector().randEnemyMove(player, this, 50, camera);
            }
        }
    }
    public void update(Player player, Raylib.Camera2D camera){
        move(player,camera);
        attack(player, camera);
    }
    private void lungeAttack(Player player, Raylib.Camera2D camera){
        if(shouldAttack) {
            getVector().setShotPosition(player.getPosition());
            getVector().setStraightLine(camera);
            shouldAttack = false;
            isAttacking = true;
        }
        if(isAttacking){
            directionLocked = true;
            setMoveSpeed(speedWhileAttacking);
            getVector().updateShootLinePosition(camera);
            if(attackingTime.cooldown(attackTimeInMilli)){
                isAttacking = false;
                directionLocked = false;
            }
        }
    }
    private void dealDamageIfCollided(Player player){
        if(CheckCollisionCircles(getPos(),getSize(),player.getPosition(),player.getSize())){
            if(dealDamageCD.cooldown(intervalBetweenAttacks)) {
                playerHp.damagePlayer(player, getDamage());
            }
        }
    }
}
