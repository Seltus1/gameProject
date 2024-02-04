package Creatures.Enemies.Sniper;

import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import com.raylib.Raylib;
import Handlers.*;
import Attacks.*;

import java.util.Random;

public class SniperEnemy extends Enemy {
    private boolean canShoot;
    private Random rand = new Random();
    private int shotSpeed;
    private String shotTag;
    private int shotTimer;
    private int shotCooldown;
    private int shootRange;
    private CooldownHandler cooldown;

    public SniperEnemy(int hp, int dps, int posX, int posY, int moveSpeed, int size, int range, int shotSpeed, Raylib.Color color, Raylib.Camera2D camera) {
        super(hp, dps, posX, posY, moveSpeed, size, range, color, camera);
        this.shotSpeed = shotSpeed;
        cooldown = new CooldownHandler();
    }

    public void shootPlayer(Player player, ProjectileHandler projList, String shotTag, Raylib.Color color, Raylib.Camera2D camera){
        if (getRange() > getVector().distanceToOtherObject(player.getPosX(),player.getPosY())){
            shotCooldown = rand.nextInt(100) + 10;
            if(cooldown.cooldown(shotCooldown)){
                int playerXPos = player.getPosX();
                int playerYPos = player.getPosY();
                Projectile shot = new Projectile(shotSpeed, getPosX(), getPosY(), 7, playerXPos, playerYPos, shotTag, getRange(), true, camera, color);
                shot.createShotLine(camera);
                projList.add(shot);
                canShoot = false;
            }
        }
    }

    public void update(Player player, ProjectileHandler projList, String tag, Raylib.Color color, Raylib.Camera2D camera){
        shootPlayer(player, projList, tag, color, camera);
    }

    public String getShotTag() {
        return shotTag;
    }

    public void setShotTag(String shotTag) {
        this.shotTag = shotTag;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    @Override
    public Random getRand() {
        return rand;
    }

    @Override
    public void setRand(Random rand) {
        this.rand = rand;
    }

    public int getShotSpeed() {
        return shotSpeed;
    }

    public void setShotSpeed(int shotSpeed) {
        this.shotSpeed = shotSpeed;
    }

    public int getShotTimer() {
        return shotTimer;
    }

    public void setShotTimer(int shotTimer) {
        this.shotTimer = shotTimer;
    }

    public int getShotCooldown() {
        return shotCooldown;
    }

    public void setShotCooldown(int shotCooldown) {
        this.shotCooldown = shotCooldown;
    }

    public int getShootRange() {
        return shootRange;
    }

    public void setShootRange(int shootRange) {
        this.shootRange = shootRange;
    }
}