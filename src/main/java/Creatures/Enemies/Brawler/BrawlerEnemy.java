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
    private boolean didDealDamageToPlayer;
    private boolean startAttackCD;
    private CooldownHandler betweenAttacksCD;
    private boolean attackLocked;


    public BrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, Raylib.Color color, Raylib.Camera2D camera){
        super(hp, damage, posX, posY, moveSpeed, size, range, color, camera);
        attackCooldown = 1000;
        attackCD = new CooldownHandler();
        attackingTime = new CooldownHandler();
        speedWhileAttacking = getMoveSpeed() * 2;
        attackTimeInMilli = 500;
        playerHp = new HealthHandler();
        intervalBetweenAttacks = 2500;
        dealDamageCD = new CooldownHandler();
        enemyKnockbackAfterShieldCollision = new CooldownHandler();
        resetMoveSpeed = new CooldownHandler();
        betweenAttacksCD = new CooldownHandler();
    }

    public void attack(Player player, Raylib.Camera2D camera){
        if(knockBackEnemy){
            isAttacking = false;
            shouldAttack = false;
            knockBackEnemy(player,camera);
            return;
        }
        int distance = getVector().distanceToOtherObject(player.getPosX(),player.getPosY());
        if(startAttackCD){
            setMoveSpeed(0);
            directionLocked  = false;
            attackLocked = true;
            if(betweenAttacksCD.cooldown(intervalBetweenAttacks)){
                startAttackCD = false;
                attackLocked = false;
                setMoveSpeed(getInitialMoveSpeed());
            }
        }
        if(distance <= getRange()){
            if(attackCD.cooldown(attackCooldown) &&!isAttacking&& !knockBackEnemy){
//                player.setHp(player.getHp() - getDamage());
//                player.getRegenCooldown().setCurrentFrame(0);
                shouldAttack = true;
            }
            lungeAttack(player,camera);
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
//            else {
//                getVector().randEnemyMove(player, this, 50, camera);
//            }
        }
    }
    public void update(Player player, Raylib.Camera2D camera){
        move(player,camera);
        attack(player, camera);
//        resetSpeedAfterCollisionWithShield();
        collisionWithShield(player,camera);
    }
    private void lungeAttack(Player player, Raylib.Camera2D camera){
        if(shouldAttack && !isAttacking && !knockBackEnemy && !attackLocked) {
            setInitialPositionBeforeAttack(getPos());
            getVector().setShotPosition(player.getPosition());
            getVector().setStraightLine(camera);
            shouldAttack = false;
            isAttacking = true;
        }
        if(isAttacking && !knockBackEnemy && !shouldAttack && !attackLocked){
            directionLocked = true;
            setMoveSpeed(speedWhileAttacking);
            getVector().updateShootLinePosition(camera);
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
//    private void resetSpeedAfterCollisionWithShield(){
//        if(collidedWithShield){

//    }
    private void collisionWithShield(Player player, Camera2D camera){
        if(collidedWithShield){
            knockBackEnemy = true;
            knockBackEnemy(player, camera);
            collidedWithShield = false;
        }
    }
    private void knockBackEnemy(Player player, Camera2D camera){
        if(knockBackEnemy) {
            startAttackCD = true;
            setDirectionLocked(true);
            getVector().moveObject(player.getPosition(), "away", camera);
            if (enemyKnockbackAfterShieldCollision.cooldown(200)) {
                knockBackEnemy = false;
                setDirectionLocked(false);
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
