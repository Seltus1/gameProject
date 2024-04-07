package Creatures.Players.Warriors;
import Attacks.Shield;
import Attacks.Sword;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import Handlers.*;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class Knight extends Player {

    private Sword sword;
    private Shield shield;
    private CooldownHandler drawCooldown;
    private Raylib.Vector2 currMousePos;
    private Raylib.Vector2 endOfChargeLocation;
    private boolean startChargeCD;
    private boolean isMeleeing;
    private CooldownHandler chargingTime;
    private CooldownHandler chargeCooldown;
    private double[] chargingShieldPos;
    private float chargeX;
    private float chargeY;
    private VectorHandler shieldVector;

    private CooldownHandler ultimateCooldown;



    public Knight(int hp, int damage, int meleeRange, int posX, int posY, int moveSpeed, int size, Raylib.Camera2D camera, Raylib.Color color) {
        super(hp, damage, meleeRange, posX, posY, moveSpeed, size, camera, color);
        sword = new Sword(damage, camera);
        shield = new Shield(camera, this);
        drawCooldown = new CooldownHandler();
        chargingTime = new CooldownHandler();
        chargeCooldown = new CooldownHandler();
        ultimateCooldown = new CooldownHandler();
        setCanUseUtility(true);
        setCanUseUltimate(true);
        shieldVector = new VectorHandler(posX,posY,getInitialMoveSpeed() + 7,camera);
        setTotalShieldCD(10000);
        setTotalChargeCD(5000);
        setSecondaryUpTime(1500);
        setUltimateUpTime(8000);
        setUltimateCD(20000);
        setDefence(60);
    }

    public void update(ProjectileHandler projList, Camera2D camera, Raylib.Vector2 mousePos, EnemyHandler enemies, GameHandler game) {
//        checkIfIsCharging();
        primary(enemies, mousePos);
        secondary(mousePos,projList,camera,enemies);
        special(mousePos, camera, this, enemies);
        ultimate(this);
//        this needs to update last so that the camera doesn't jiggle
        super.update(projList, camera, mousePos, enemies, game);
    }

    public void primary(EnemyHandler enemies, Raylib.Vector2 mousePos) {
//        is the player off cooldown for attacking
        if(canMelee() && !isUsingUtility() && !isUsingSecondary()){
//            is the player attacking
            if(isMeleeing() && !didMelee()){
                setDidMelee(true);
            }
            if(didMelee()) {
                dealingDamage(enemies, mousePos);
//              set to true
                sword.drawSword(this, mousePos);
                if (drawCooldown.cooldown(200)) {
                    setCanMelee(false);
                    setDidMelee(false);
//                    set to false
                }
            }
            return;
        }
        if (!canMelee()) {
            if (getAttackCooldown().cooldown(getShotcooldown())) {
                setCanMelee(true);
            }
        }
    }
    public void secondary(Raylib.Vector2 mousePos, ProjectileHandler projList, Camera2D camera, EnemyHandler enemies){
        shield.update(this, mousePos, projList, camera, enemies);
    }

    public void special(Raylib.Vector2 mousePos, Camera2D camera, Player player, EnemyHandler enemies) {
        if (IsKeyPressed(KEY_Q) && player.getCanUseUtility() && !player.isMeleeing() && !player.isUsingSecondary()) {
            currMousePos = mousePos;
//            endOfChargeLocation = getVector().findIntersectingPointOnCircleAndMousePos(getPosition(), 1000000, mousePos);
            chargeX = mousePos.x();
            chargeY = mousePos.y();
            getVector().setShotPosition(new Jaylib.Vector2(chargeX,chargeY));
            getVector().setStraightLine(camera);
            setUsingUtility(true);
            setCanUseUtility(false);
            setDirectionLocked(true);
        }
        if (isUsingUtility()) {
            getVector().setMoveSpeed(getInitialMoveSpeed() * 2);
            currMousePos = shieldVector.findEndPointOfLine(player.getPosition(),100000,currMousePos);
            chargingShieldPos = shield.calculateShieldLocation(player,currMousePos);
//            System.out.println(chargingShieldPos[0] + chargingShieldPos[1] +  chargingShieldPos[2] + chargingShieldPos[3]);
            shield.drawShield(chargingShieldPos, player);
            getVector().updateShootLinePosition(camera);
            dealingDamage(enemies,mousePos);
//            getVector().moveObject(endOfChargeLocation, "to", camera);
            if (chargingTime.cooldown(getSecondaryUpTime())){
                setMoveSpeed(getInitialMoveSpeed());
                setUsingUtility(false);
                setDirectionLocked(false);
                startChargeCD = true;
                return;
            }
        }
        if (startChargeCD) {
            if (getChargeCD().cooldown(getTotalChargeCD())) {
                setCanUseUtility(true);
                startChargeCD = false;
            }
        }
    }

    public void ultimate(Player player){
//        can the ultimate be used
        if(player.isCanUseUltimate() && !player.isUsingSecondary() && !player.isUsingUtility() && !player.isMeleeing()){
//            is the ultimate key being pressed?
            if(IsKeyPressed(KEY_E)){
                player.setShieldHp(150);
                player.setCanUseSecondary(true);
                player.setUsingUltimate(true);
            }
        }
        if(player.isUsingUltimate()){
            player.setMoveSpeed((int) (player.getMoveSpeed() * 1.5));
            if(ultimateCooldown.cooldown(player.getUltimateUpTime())){
                player.setUsingUltimate(false);
                player.setCanUseUltimate(false);
                player.setStartUltCD(true);
            }
            return;
        }
        if(player.isStartUltCD()) {
            if (player.getUltimateTimer().cooldown(player.getUltimateCD())) {
                player.setCanUseUltimate(true);
                player.setStartUltCD(false);
            }
        }
    }

    private void dealingDamage(EnemyHandler enemies, Raylib.Vector2 mousePos) {
        if(isUsingUtility() || isMeleeing()) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);
                if (isUsingUtility()) {
                    dealDamageToEnemiesWhileCharging(enemy);
                }
                Raylib.Vector2[] trianglePoints = sword.calculateTriangle(this, mousePos);
                if (CheckCollisionPointTriangle(enemy.getPos(), trianglePoints[0], trianglePoints[1], trianglePoints[2])) {
                    sword.attack(enemy, this);
                }
                if (CheckCollisionPointTriangle(enemy.getPos(), trianglePoints[0], trianglePoints[2], trianglePoints[1])) {
                    sword.attack(enemy, this);
                }
            }
        }
    }



    private void dealDamageToEnemiesWhileCharging(Enemy enemy){
        if(shieldVector.CheckCollisionBetweenLineAndCircle(shield.getLinePoint1(),shield.getLinePoint2(),enemy.getPos(),enemy.getSize())){
//          check if the shield can do more damage
            if(shield.getCurrentDamageDealt() < shield.getMaxDamageToDeal()) {
//                check if the enemies helath is less than or = to the amount of damage left to deal on the shield
                if(shield.getMaxDamageToDeal() - shield.getCurrentDamageDealt() >= enemy.getHp()){
                    shield.setCurrentDamageDealt(shield.getCurrentDamageDealt() + enemy.getHp());
                    enemy.setHp(0);
                    return;
                }
                enemy.setHp(enemy.getHp() - (shield.getMaxDamageToDeal() - shield.getCurrentDamageDealt()));
                shield.setCurrentDamageDealt(0);
            }
        }
    }
}