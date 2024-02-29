package Creatures.Enemies.Brawler;

import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import com.raylib.Raylib;
import static com.raylib.Jaylib.*;
import Handlers.*;
import Attacks.*;

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
    private Raylib.Vector2 initialPositionBeforeAttack;
    private boolean collidedWithShield;
    private CooldownHandler enemyKnockbackAfterShieldCollision;
    private boolean knockBackEnemy;
    private CooldownHandler resetMoveSpeed;


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
        enemyKnockbackAfterShieldCollision = new CooldownHandler();
        resetMoveSpeed = new CooldownHandler();
    }

    public void attack(Player player, Raylib.Camera2D camera){
        int distance = getVector().distanceToOtherObject(player.getPosX(),player.getPosY());
        if(distance <= getRange()){
            if(attackCD.cooldown(attackCooldown) &&!isAttacking){
//                player.setHp(player.getHp() - getDamage());
//                player.getRegenCooldown().setCurrentFrame(0);
                shouldAttack = true;

            }
            lungeAttack(player,camera);
            if(knockBackEnemy){
                knockBackEnemy(player,camera);
                return;
            }
            dealDamageIfCollided(player, camera);
        }
        else {
            directionLocked = false;
        }
    }
    public void move(Player player, Raylib.Camera2D camera){
        if(!directionLocked){
            if (50 < getVector().distanceToOtherObject(player.getPosX(), player.getPosY()) && !isRandMoving()) {
                getVector().moveObject(player.getPosition(), "to", camera);
                getVector().setHasAlrdyBooleanMoved(false);
            }
            else {
                getVector().randEnemyMove(player, this, 50, camera);
            }
        }
    }
    public void update(Player player, Raylib.Camera2D camera){
        move(player,camera);
        attack(player, camera);
//        resetSpeedAfterCollisionWithShield();
        collisionWithShield(player,camera);
    }
    private void lungeAttack(Player player, Raylib.Camera2D camera){
        if(shouldAttack){
            setInitialPositionBeforeAttack(getPos());
            getVector().setShotPosition(player.getPosition());
            getVector().setStraightLine(camera);
            shouldAttack = false;
            isAttacking = true;
        }
        if(isAttacking && !knockBackEnemy){
            directionLocked = true;
            setMoveSpeed(speedWhileAttacking);
            getVector().updateShootLinePosition(camera);
            if(attackingTime.cooldown(attackTimeInMilli)){
                isAttacking = false;
                directionLocked = false;
                setMoveSpeed(getInitialMoveSpeed());
            }
        }
    }
    private void dealDamageIfCollided(Player player, Camera2D camera){
        if(CheckCollisionCircles(getPos(),getSize(),player.getPosition(),player.getSize())){
            if(dealDamageCD.cooldown(intervalBetweenAttacks)) {
                playerHp.damagePlayer(player, getDamage());
                knockBackEnemy = true;
            }
            setAttacking(false);
        }
    }
//    private void resetSpeedAfterCollisionWithShield(){
//        if(collidedWithShield){

//    }
    private void collisionWithShield(Player player, Camera2D camera){
        if(collidedWithShield){
            collidedWithShield = false;
                knockBackEnemy(player, camera);
                knockBackEnemy = true;

        }
    }
    private void knockBackEnemy(Player player, Camera2D camera){
        if(knockBackEnemy) {
            getVector().moveObject(player.getPosition(), "away", camera);
            if (enemyKnockbackAfterShieldCollision.cooldown(1000)) {
                knockBackEnemy = false;
            }
        }
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public boolean isDirectionLocked() {
        return directionLocked;
    }

    public void setDirectionLocked(boolean directionLocked) {
        this.directionLocked = directionLocked;
    }

    public Raylib.Vector2 getInitialPositionBeforeAttack() {
        return initialPositionBeforeAttack;
    }
    public void setInitialPositionBeforeAttack(Raylib.Vector2 initialPositionBeforeAttack){
        this.initialPositionBeforeAttack = initialPositionBeforeAttack;
    }
    public void setSpeedWhileAttacking(int speedWhileAttacking){
        this.speedWhileAttacking = speedWhileAttacking;
    }
    public int getSpeedWhileAttacking(){
        return speedWhileAttacking;
    }

    public boolean isCollidedWithShield() {
        return collidedWithShield;
    }

    public void setCollidedWithShield(boolean collidedWithShield) {
        this.collidedWithShield = collidedWithShield;
    }
}
