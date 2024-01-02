import com.raylib.Raylib;

import java.util.Random;

public class SniperEnemy extends Enemy{
    private boolean canShoot;
    private Random rand = new Random();
    private int shotSpeed;
    private String shotTag;
    private int shotTimer;
    private int shotCooldown;
    private int shootRange;


    public SniperEnemy(int hp, int dps, int posX, int posY, int moveSpeed, int size, int range, int shotSpeed, Raylib.Color color) {
        super(hp, dps, posX, posY, moveSpeed, size, range, color);
        this.shotSpeed = shotSpeed;
        setActualXPos(posX);
        setActualYPos(posY);
    }

    public void shootPlayer(Player player, ProjectileHandler projList, String shotTag, Raylib.Color color){
        if (getRange() > calculateDistanceToPlayer(player)){
            shotTimer++;
            shotCooldown = rand.nextInt(30) + 60;
            if((shotTimer +1 ) % shotCooldown == 0) {
                int playerXPos = player.getPosX();
                int playerYPos = player.getPosY();
                Projectile shot = new Projectile(shotSpeed, getPosX(), getPosY(), 7, playerXPos, playerYPos, shotTag, getRange(), color);
                shot.vectorCalc();
                projList.add(shot);
                canShoot = false;
                shotTimer = 0;
            }
        }
    }

    public String getShotTag() {
        return shotTag;
    }

    public void setShotTag(String shotTag) {
        this.shotTag = shotTag;
    }
}