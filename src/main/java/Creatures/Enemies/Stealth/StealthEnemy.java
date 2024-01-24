package Creatures.Enemies.Stealth;

import Creatures.Enemies.Enemy;
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

    public StealthEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, int shotSpeed, Raylib.Color color, Camera2D camera){
        super(hp,damage,posX,posY,moveSpeed,size,range,color, camera);
        initialColor = color;
        vector = new VectorHandler(posX, posY, moveSpeed, camera);
        this.shotSpeed = shotSpeed;
        pos = new Jaylib.Vector2();
        pos.x(posX);
        pos.y(posY);
        isReloading = false;
        footstep = new Footstep(posX,posY,0);
        rand = new Random();
        cooldown = new CooldownHandler();
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

    public void move(Player player, Camera2D camera){
        if(!isReloading) {
            if (getRange() / 1.5 < calculateDistanceToPlayer(player)) {
                followPlayer(player, "to", camera);
            }
            if (getRange() / 2 > calculateDistanceToPlayer(player)) {
                followPlayer(player, "away", camera);
            }
        }
        else{
            if (getRange() * 1.5 < calculateDistanceToPlayer(player)) {
                followPlayer(player, "to", camera);
            }
            if (getRange() * 2 > calculateDistanceToPlayer(player)) {
                followPlayer(player, "away", camera);
            }
        }
    }

    public void shoot(Player player, ProjectileHandler projList, Camera2D camera) {
        if (!isReloading) {
            if (calculateDistanceToPlayer(player) <= getRange()) {
                if (cooldown.cooldown(500)) {
                    canShoot = true;
                }
                if (canShoot) {
                    canShoot = false;
                    numShots++;
                    Projectile proj = new Projectile(shotSpeed, getPosX(), getPosY(), 7, player.getPosX(), player.getPosY(), "Creatures.Enemies.Enemy", getRange(), true, camera, BLACK);
                    projList.add(proj);
                    proj.createShotLine(camera);
                    if (numShots > 10) {
                        isReloading = true;
                        numShots = 0;
                    }
                }
            }
        } else {
            if (cooldown.cooldown(1000)) {
                isReloading = false;
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

    public void update(Player player, ProjectileHandler projList, Camera2D camera){
        move(player, camera);
        cloak(player);
        shoot(player, projList, camera);
        footsteps();
        footstep.draw();
    }

    public Raylib.Color getInitialColor() {
        return initialColor;
    }

    public void setInitialColor(Raylib.Color initialColor) {
        this.initialColor = initialColor;
    }

    public VectorHandler getVector() {
        return vector;
    }

    public void setVector(VectorHandler vector) {
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
