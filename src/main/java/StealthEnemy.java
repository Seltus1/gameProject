import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class StealthEnemy extends Enemy{
    private Raylib.Color initialColor;
    private Vector vector;
    private Jaylib.Vector2 pos;
    private int shotSpeed;
    private boolean isReloading;
    private boolean canShoot;
    private boolean isCloaked;
    private int cooldown;
    private int footstepCooldown;
    private int numShots;

    public StealthEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, int shotSpeed, Raylib.Color color){
        super(hp,damage,posX,posY,moveSpeed,size,range,color);
        initialColor = color;
        vector = new Vector(posX, posY, moveSpeed);
        this.shotSpeed = shotSpeed;
        pos = new Jaylib.Vector2();
        pos.x(posX);
        pos.y(posY);
        isReloading = false;

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
                    Projectile proj = new Projectile(shotSpeed, getPosX(), getPosY(), 7, player.getPosX(), player.getPosY(), "Enemy", getRange(), BLACK);
                    projList.add(proj);
                    proj.shootInLine();
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
            Footstep footstep;
            footstepCooldown++;
            if((footstepCooldown + 1) % 61 == 0){
                footstep = new Footstep(getPosX(),getPosY(), 3000);
                footstep.draw();
            }
        }
    }

    public void update(Player player, ProjectileHandler projList){
        move(player);
        cloak(player);
        shoot(player, projList);
        footsteps();
    }

    public Raylib.Color getInitialColor() {
        return initialColor;
    }

    public void setInitialColor(Raylib.Color initialColor) {
        this.initialColor = initialColor;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    @Override
    public Jaylib.Vector2 getPos() {
        return pos;
    }

    @Override
    public void setPos(Jaylib.Vector2 pos) {
        this.pos = pos;
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
