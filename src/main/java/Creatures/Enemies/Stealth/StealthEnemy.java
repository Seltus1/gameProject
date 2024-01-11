package Creatures.Enemies.Stealth;

import Creatures.Enemies.Enemy;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.Random;

import static com.raylib.Jaylib.*;
import Creatures.*;
import Handlers.*;
import Attacks.*;
import Elements.*;

public class StealthEnemy extends Enemy {
    private Raylib.Color initialColor;
    private Vector2D vector;
    private Jaylib.Vector2 pos;
    private int shotSpeed;
    private boolean isReloading;
    private boolean canShoot;
    private boolean isCloaked;
    private int cooldown;
    private int footstepCooldown;
    private int numShots;
    private Footstep footstep;
    private Random rand;

    public StealthEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, int shotSpeed, Raylib.Color color){
        super(hp,damage,posX,posY,moveSpeed,size,range,color);
        initialColor = color;
        vector = new Vector2D(posX, posY, moveSpeed);
        this.shotSpeed = shotSpeed;
        pos = new Jaylib.Vector2();
        pos.x(posX);
        pos.y(posY);
        isReloading = false;
        footstep = new Footstep(posX,posY,0);
        rand = new Random();

    }

    public void cloak(Player player){
        if(calculateDistanceToPlayer(player) <= getRange()) {
            setShouldDraw(true);
            isCloaked = false;
        }
        else{
            setShouldDraw(false);
            isCloaked = true;
        }
    }

    public void move(Player player){
        if(!isReloading) {
            if (getRange() / 1.5 < calculateDistanceToPlayer(player)) {
                followPlayer(player, "to");
            }
            if (getRange() / 2 > calculateDistanceToPlayer(player)) {
                followPlayer(player, "away");
            }
        }
        else{
            if (getRange() * 1.5 < calculateDistanceToPlayer(player)) {
                followPlayer(player, "to");
            }
            if (getRange() * 2 > calculateDistanceToPlayer(player)) {
                followPlayer(player, "away");
            }
        }
    }

    public void shoot(Player player, ProjectileHandler projList) {
        if (!isReloading) {
            if (calculateDistanceToPlayer(player) <= getRange()) {
            cooldown++;
                if(((cooldown + 1) % 31) == 0){
                    canShoot = true;
                    cooldown = 0;
                }
                if(canShoot) {
                    canShoot = false;
                    numShots++;
                    Projectile proj = new Projectile(shotSpeed, getPosX(), getPosY(), 7, player.getPosX(), player.getPosY(), "Creatures.Enemies.Enemy", getRange(), true, BLACK);
                    projList.add(proj);
                    proj.shootLine();
                    if (numShots > 10) {
                        isReloading = true;
                        numShots = 0;
                    }
                }
            }
        }
        else{
            cooldown++;
            if(((cooldown + 1) % 500) == 0){
                isReloading = false;
                cooldown = 0;
            }
        }
    }
    public void footsteps(){
        if(isCloaked){
            footstepCooldown++;
            if((footstepCooldown + 1) % 61 == 0){
                footstep.setLifeTime(200);
                footstep.setPosX(getPosX() + rand.nextInt(200) - 100);
                footstep.setPosY(getPosY()+ rand.nextInt(200) - 100);
                footstep.setTimeFirstDrawn(System.currentTimeMillis());
            }
        }
    }

    public void update(Player player, ProjectileHandler projList){
        move(player);
        cloak(player);
        shoot(player, projList);
        footsteps();
        footstep.draw();
    }

    public Raylib.Color getInitialColor() {
        return initialColor;
    }

    public void setInitialColor(Raylib.Color initialColor) {
        this.initialColor = initialColor;
    }

    public Vector2D getVector() {
        return vector;
    }

    public void setVector(Vector2D vector) {
        this.vector = vector;
    }


    public int getShotSpeed() {
        return shotSpeed;
    }

    public void setShotSpeed(int shotSpeed) {
        this.shotSpeed = shotSpeed;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public void setReloading(boolean reloading) {
        isReloading = reloading;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean isCloaked() {
        return isCloaked;
    }

    public void setCloaked(boolean cloaked) {
        isCloaked = cloaked;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getFootstepCooldown() {
        return footstepCooldown;
    }

    public void setFootstepCooldown(int footstepCooldown) {
        this.footstepCooldown = footstepCooldown;
    }

    public int getNumShots() {
        return numShots;
    }

    public void setNumShots(int numShots) {
        this.numShots = numShots;
    }
}
