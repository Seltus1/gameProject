package Creatures.Enemies.Stealth;

import Creatures.Enemies.Enemy;
import Debuffs.Poison;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.Random;

import static com.raylib.Jaylib.*;
import Creatures.*;
import Handlers.*;
import Attacks.*;

public class StealthEnemy extends Enemy {
    private Raylib.Color initialColor;
    private VectorHandler vector;
    private Jaylib.Vector2 pos;
    private int shotSpeed;
    private boolean isReloading;
    private boolean canShoot;
    private boolean isCloaked;
    private CooldownHandler cooldown;
    private int footstepCooldown;
    private int numShots;
    private Footstep footstep;
    private Random rand;
    private int maxShots;

    public StealthEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, int shotSpeed, Raylib.Color color, Camera2D camera) {
        super(hp, damage, posX, posY, moveSpeed, size, range, color, camera);
        initialColor = color;
        vector = new VectorHandler(posX, posY, moveSpeed, camera);
        this.shotSpeed = shotSpeed;
        pos = new Jaylib.Vector2();
        pos.x(posX);
        pos.y(posY);
        isReloading = false;
        footstep = new Footstep(posX, posY, 0);
        rand = new Random();
        cooldown = new CooldownHandler();
        maxShots = 5;
    }

    public void cloak(Player player) {
        if (getVector().distanceToOtherObject(player.getPosX(), player.getPosY()) <= getRange()) {
            setShouldDraw(true);
            isCloaked = false;
        } else {
            setShouldDraw(false);
            isCloaked = true;
        }
    }

    public void move(Player player, Camera2D camera) {
        if (!isReloading) {
            if (!isGotinRange()) {
                if (getRange() / 1.5 < getVector().distanceToOtherObject(player.getPosX(), player.getPosY())) {
                    getVector().moveObject(player.getPosition(), "to", camera);
                } else {
                    getVector().moveObject(player.getPosition(), "away", camera);
                    setGotinRange(true);
                }
            } else {
                getVector().circlePlayer(player, getRange());
            }
        } else {
            if (getRange() * 1.5 < getVector().distanceToOtherObject(player.getPosX(), player.getPosY())) {
                getVector().moveObject(player.getPosition(), "to", camera);
            } else if (getRange() * 2 > getVector().distanceToOtherObject(player.getPosX(), player.getPosY())) {
                getVector().moveObject(player.getPosition(), "away", camera);
                setGotinRange(true);
            } else {
                getVector().circlePlayer(player, getRange() + 200);
            }
        }
    }

    public void shoot(Player player, ProjectileHandler projList, Camera2D camera, Poison poison) {
        if (!isReloading) {
            if (getVector().distanceToOtherObject(player.getPosX(), player.getPosY()) <= getRange()) {
                if (cooldown.cooldown(500)) {
                    canShoot = true;
                }
                if (canShoot) {
                    canShoot = false;
                    numShots++;
                    Projectile proj = new Projectile(shotSpeed, getPosX(), getPosY(), 7, player.getPosX(), player.getPosY(), "Enemy", getRange(), true, camera, BLACK);
                    projList.add(proj);
                    proj.createShotLine(camera);

                    if (numShots == maxShots) {
                        proj.setShotTag("Enemy_Poison");
                        proj.setColor(poison.getColor());
                        isReloading = true;
                        numShots = 0;
                    }
                }
            }
        } else {
            if (cooldown.cooldown(3000)) {
                isReloading = false;
            }
        }
    }

    public void footsteps() {
        if (isCloaked) {
            footstepCooldown++;
            if ((footstepCooldown + 1) % 61 == 0) {
                footstep.setLifeTime(400);
                footstep.setPosX(getPosX() + rand.nextInt(200) - 100);
                footstep.setPosY(getPosY() + rand.nextInt(200) - 100);
                footstep.setTimeFirstDrawn(System.currentTimeMillis());
            }
        }
    }

    public void update(Player player, ProjectileHandler projList, Camera2D camera, Poison poison) {
        move(player, camera);
        cloak(player);
        shoot(player, projList, camera, poison);
        footsteps();
        footstep.draw();
    }
}

