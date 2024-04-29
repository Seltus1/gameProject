package Creatures.Enemies.Brawler;

import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import com.raylib.Raylib;
import static com.raylib.Jaylib.*;
import Handlers.*;
import Attacks.*;

import java.sql.SQLOutput;

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
    private boolean didDealDamageToPlayer;
    private boolean startAttackCD;
    private CooldownHandler betweenAttacksCD;
    private boolean canAttack;
    private boolean attackLocked;
    private int count;
    private int attackLockcount;
    private boolean didStartRandMoving;
    private Raylib.Vector2 initialPositionBeforeRandMove;


    public BrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, int numCoins, Raylib.Color color, Raylib.Camera2D camera){
        super(hp, damage, posX, posY, moveSpeed, size, range, numCoins, color, camera);
        attackCooldown = 1000;
        attackCD = new CooldownHandler();
        attackingTime = new CooldownHandler();
        speedWhileAttacking = getMoveSpeed() * 2;
        attackTimeInMilli = 500;
        playerHp = new HealthHandler();
        intervalBetweenAttacks = 1000;
        dealDamageCD = new CooldownHandler();
        enemyKnockbackAfterShieldCollision = new CooldownHandler();
        resetMoveSpeed = new CooldownHandler();
        betweenAttacksCD = new CooldownHandler();
        canAttack = true;
    }

    public void update(Player player, Raylib.Camera2D camera) {
        move(player, camera);
//        resetSpeedAfterCollisionWithShield();
        dealDamageIfCollided(player, camera);
        collisionWithShield(player, camera);
        if (knockBackEnemy) {
            knockBackEnemy(player, camera);
        }
        else{
            attack(player, camera);
        }
//        DrawText("" + isAttacking, player.getPosX() + 10, player.getPosY() + 10, 30, BLACK);
    }

    public void attack(Player player, Raylib.Camera2D camera){

        if(startAttackCD){
            if(betweenAttacksCD.cooldown(intervalBetweenAttacks)) {
                canAttack = true;
                startAttackCD = false;
            }
        }
        if (canAttack) {
            int distance = getVector().distanceToOtherObject(player.getPosX(),player.getPosY());
            if(distance <= getRange()){
                if(attackCD.cooldown(attackCooldown)){
                    shouldAttack = true;
                }
                lungeAttack(player,camera);
            }
        }

    }

    public void move(Player player, Raylib.Camera2D camera){
        if(!directionLocked){
            if (startAttackCD) {
                if(!didStartRandMoving){
                    initialPositionBeforeRandMove = getPos();
                    didStartRandMoving = true;
                }
//                else {
//                    getVector().randEnemyMoveInArea(initialPositionBeforeRandMove, this, 10, camera);
//                }
            }
            if (getRange() <= getVector().distanceToOtherObject(player.getPosX(), player.getPosY()) && !isRandMoving()) {
                getVector().moveObject(player.getPosition(), "to", camera);
                getVector().setHasAlrdyBooleanMoved(false);
                didStartRandMoving = false;
            }
//            else {
//                getVector().randEnemyMove(player, this, 10, camera);
//            }
        }
    }
    private void lungeAttack(Player player, Raylib.Camera2D camera){
        if(shouldAttack) {
            setInitialPositionBeforeAttack(getPos());
            getVector().setShotPosition(player.getPosition());
            getVector().setStraightLine(camera);
            shouldAttack = false;
            isAttacking = true;
        }
        if(isAttacking){
            directionLocked = true;
            setMoveSpeed(speedWhileAttacking);
            getVector().updateShootLinePosition(camera);
//            after X ammt of time, stop attacking, no matter if we hit the player or not.
            if(attackingTime.cooldown(attackTimeInMilli)){
                directionLocked = false;
                isAttacking = false;
                setMoveSpeed(getInitialMoveSpeed());
                shouldAttack = false;
            }
        }
    }
    private void dealDamageIfCollided(Player player, Camera2D camera){
        if(CheckCollisionCircles(getPos(),getSize(),player.getPosition(),player.getSize())){
            attackingTime.resetCooldown();
            setMoveSpeed(getInitialMoveSpeed());
            setAttacking(false);
            knockBackEnemy = true;
            if(!didDealDamageToPlayer){
                playerHp.damagePlayer(player, getDamage());
                didDealDamageToPlayer = true;
            }
        }
        else{
            didDealDamageToPlayer = false;
        }
    }
    private void collisionWithShield(Player player, Camera2D camera){
        if(collidedWithShield){
            knockBackEnemy = true;
            collidedWithShield = false;
            attackingTime.resetCooldown();
            setMoveSpeed(getInitialMoveSpeed());
            setAttacking(false);
            knockBackEnemy = true;
        }
    }
    private void knockBackEnemy(Player player, Camera2D camera){
        isAttacking = false;
        shouldAttack = false;
        canAttack = false;
        setDirectionLocked(true);
        getVector().moveObject(player.getPosition(), "away", camera);
        if (enemyKnockbackAfterShieldCollision.cooldown(1000)) {
            knockBackEnemy = false;
            setDirectionLocked(false);
            startAttackCD = true;
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