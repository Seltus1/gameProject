package Creatures.Players.Warriors;
import Attacks.Shield;
import Attacks.Sword;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.EnemyHandler;
import Handlers.ProjectileHandler;
import Handlers.VectorHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class Warrior extends Player {

    private Sword sword;
    private Shield shield;
    private boolean canAttack;
    private CooldownHandler attackCooldown;
    private CooldownHandler drawCooldown;
    private Raylib.Vector2 currMousePos;
    private Raylib.Vector2 endOfChargeLocation;
    private boolean startChargeCD;
    private boolean isMeleeing;
    private CooldownHandler chargingTime;
    private CooldownHandler chargeCooldown;
    private boolean canCharge;
    private double[] chargingShieldPos;
    private float chargeX;
    private float chargeY;
    private VectorHandler shieldVector;


    public Warrior(int hp, int damage, int meleeRange, int posX, int posY, int moveSpeed, int size, Raylib.Camera2D camera, Raylib.Color color) {
        super(hp, damage, meleeRange, posX, posY, moveSpeed, size, camera, color);
        sword = new Sword(damage, camera);
        shield = new Shield(camera);
        attackCooldown = new CooldownHandler();
        drawCooldown = new CooldownHandler();
        chargingTime = new CooldownHandler();
        chargeCooldown = new CooldownHandler();
        canCharge = true;
        shieldVector = new VectorHandler(posX,posY,getInitialMoveSpeed() + 7,camera);
    }

    public void update(ProjectileHandler projList, Camera2D camera, Raylib.Vector2 mousePos, EnemyHandler enemies) {
//        checkIfIsCharging();
        checkIfIsMeleeing();
        attack(enemies, mousePos);
        shield.update(this, mousePos, projList);
        charge(mousePos, camera, this);
//        this needs to update last so that the camera doesn't jiggle
        super.update(projList, camera, mousePos, enemies);
    }

    public void attack(EnemyHandler enemies, Raylib.Vector2 mousePos) {
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT) && !isCharging() && !isMeleeing) {
            if (!canMelee()) {
                if (attackCooldown.cooldown(getShotcooldown())) {
                    setCanMelee(true);
                }
            }
            if (canMelee()) {
                dealingDamage(enemies, mousePos);
                sword.drawSword(this, mousePos);
                if (drawCooldown.cooldown(200)) {
                    setCanMelee(false);
                }
            }
        }
    }

    public void charge(Raylib.Vector2 mousePos, Camera2D camera, Player player) {
        if (IsKeyPressed(KEY_Q) && canCharge) {
            currMousePos = mousePos;
            getVector().setMoveSpeed(getInitialMoveSpeed() + 7);
//            endOfChargeLocation = getVector().findIntersectingPointOnCircleAndMousePos(getPosition(), 1000000, mousePos);
            chargeX = mousePos.x();
            chargeY = mousePos.y();
            getVector().setShotPosition(new Jaylib.Vector2(chargeX,chargeY));
            getVector().setShootLine(camera);
            startChargeCD = true;
            setCharging(true);
            canCharge = false;
            setDirectionLocked(true);
        }
        if (isCharging()) {
            currMousePos = shieldVector.findIntersectingPointOnCircleAndMousePos(player.getPosition(),100000,currMousePos);
            chargingShieldPos = shield.calculateShieldLocation(player,currMousePos);
//            System.out.println(chargingShieldPos[0] + chargingShieldPos[1] +  chargingShieldPos[2] + chargingShieldPos[3]);
            shield.drawShield(chargingShieldPos);
            getVector().updateShootLinePosition(camera);
//            getVector().moveObject(endOfChargeLocation, "to", camera);
        }
        if (chargingTime.cooldown(2000)) {
            setMoveSpeed(getInitialMoveSpeed());
            setCharging(false);
            setDirectionLocked(false);
            return;
        }
        if (startChargeCD) {
            if (chargeCooldown.cooldown(5000)) {
                canCharge = true;
                startChargeCD = false;
            }
        }
    }

    private void dealingDamage(EnemyHandler enemies, Raylib.Vector2 mousePos) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = (Enemy) enemies.get(i);
            if(isCharging()){
                dealDamageToEnemies(enemy);
                return;
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

    private void checkIfIsMeleeing() {
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
            setMeleeing(true);
            return;
        }
        setMeleeing(false);
    }

    private void dealDamageToEnemies(Enemy enemy){
        if(shieldVector.CheckCollisionBetweenLineAndCircle(shield.getLinePoint1(),shield.getLinePoint2(),enemy.getPos(),enemy.getSize())){
            if(shield.getCurrentDamageDealt() < shield.getMaxDamageToDeal()) {
//                enemy.setHp(enemy.getHp() -);
            }
        }
    }
}
