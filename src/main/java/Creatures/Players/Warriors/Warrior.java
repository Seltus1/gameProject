package Creatures.Players.Warriors;
import Attacks.Shield;
import Attacks.Sword;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.EnemyHandler;
import Handlers.ProjectileHandler;
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
    private boolean isCharging;
    private Raylib.Vector2 endOfChargeLocation;
    private boolean startChargeCD;
    private boolean isMeleeing;
    private CooldownHandler chargingTime;
    private CooldownHandler chargeCooldown;
    private boolean canCharge;
    private float chargeX;
    private float chargeY;
    private int counter;

    public Warrior(int hp, int damage, int meleeRange, int posX, int posY, int moveSpeed, int size, Raylib.Camera2D camera, Raylib.Color color) {
        super(hp, damage, meleeRange, posX, posY, moveSpeed, size, camera, color);
        sword = new Sword(damage, camera);
        shield = new Shield(camera);
        attackCooldown = new CooldownHandler();
        drawCooldown = new CooldownHandler();
        chargingTime = new CooldownHandler();
        chargeCooldown = new CooldownHandler();
        canCharge = true;
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
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
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
            endOfChargeLocation = getVector().findIntersectingPointOnCircleAndMousePos(getPosition(), 1000000, mousePos);
//            chargeX = mousePos.x();
//            chargeY = mousePos.y();
//            getVector().setShotPosition(new Jaylib.Vector2(chargeX,chargeY));
//            getVector().setShootLine(camera);
            startChargeCD = true;
            isCharging = true;
            canCharge = false;
            setDirectionLocked(true);
        }
        if (isCharging) {
//            getVector().updateShootLinePosition(camera);
            getVector().moveObject(endOfChargeLocation, "to", camera);
//            shield.drawShield(shield.calculateShieldLocation(player,mousePos));
        }
        if (chargingTime.cooldown(2000)) {
            setMoveSpeed(getInitialMoveSpeed());
            isCharging = false;
            setDirectionLocked(false);
            return;
        }
        if (startChargeCD) {
            if (chargeCooldown.cooldown(1000)) {
                canCharge = true;
                startChargeCD = false;
            }
        }
    }

    private void dealingDamage(EnemyHandler enemies, Raylib.Vector2 mousePos) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = (Enemy) enemies.get(i);
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
}
