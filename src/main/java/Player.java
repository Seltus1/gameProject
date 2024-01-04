import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.raylib.Raylib.*;

public class Player implements Creature {
//    HP

    private int hp;
    private int initalHp;

//    DMG

    private int damage;
    private int range;

//    POS

    private int posX;
    private int posY;
    private Jaylib.Vector2 pos;

//    Move

    private int moveSpeed;

//    size/color

    private int size;
    private Raylib.Color color;


    //    Player states
    private boolean isAlive;
    private boolean isOnFire;
    private int burnTicks;
    private int intialBurn;

//    cooldowns

    private boolean canShoot;
    private boolean canMelee;
    private final int SHOT_COOLDOWN = 500;
    private static final int MELEE_COOLDOWN = 1000;
    private long timeSinceHit;
    private int regenCooldown;


    //    instance of other stuffs
    private MeleeAttack sword = new MeleeAttack(damage, posX, posY);

    private int burnDamage;
    private int burnCountDown;
    private boolean isFireInRange;
    private int shotRange;
    private Vector vector;


    public Player(int hp, int damage, int meleeRange, int posX, int posY, int moveSpeed, int size, int shotRange, Raylib.Color color) {
        this.hp = hp;
        initalHp = hp;
        this.damage = damage;
        this.range = meleeRange;
        this.posX = posX;
        this.posY = posY;
        this.moveSpeed = moveSpeed;
        this.size = size;
        this.isAlive = true;
        this.color = color;
        this.pos = new Jaylib.Vector2(posX, posY);
        this.canShoot = true;
        this.canMelee = true;
        isOnFire = false;
        burnCountDown = 0;
        intialBurn = 10;
        burnDamage = 1;
        this.shotRange = shotRange;
        vector = new Vector(posX, posY, moveSpeed);
        regenCooldown = 5000;
    }

    public void update(ProjectileHandler projList) {
        vector.playerMove();
        updatePosition(vector.getPosX(), vector.getPosY());
        sword.update();
        burn();
        DrawCircle(posX, posY, size, color);
        setPos(new Jaylib.Vector2(posX,posY));
    }

    public void updatePosition(int xPos, int yPos){
        posX = xPos;
        posY = yPos;
    }

    public void burn() {
        burnCountDown++;
        if (burnTicks != 0) {
            if ((burnCountDown + 1) % 15 == 0) {
                hp = (hp - burnDamage);
                setTimeSinceHit(System.currentTimeMillis());
                if (!isFireInRange) {
                    burnTicks -= burnDamage;
                }
            }
        } else {
            isOnFire = false;
        }
    }


    public int getBurnDamage() {
        return burnDamage;
    }

    public void setBurnDamage(int burnDamage) {
        this.burnDamage = burnDamage;
    }

    public boolean canShoot() {
        return canShoot;
    }

    public boolean canMelee() {
        return canMelee;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int getMoveSpeed() {
        return moveSpeed;
    }

    @Override
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public Raylib.Color getColor() {
        return color;
    }

    @Override
    public void setColor(Raylib.Color color) {
        this.color = color;
    }

    @Override
    public Jaylib.Vector2 getPos() {
        return pos;
    }

    @Override
    public void setPos(Jaylib.Vector2 pos) {
        this.pos = pos;
    }

    public int getInitalHp() {
        return initalHp;
    }

    public void setInitalHp(int initalHp) {
        this.initalHp = initalHp;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean isCanMelee() {
        return canMelee;
    }

    public void setCanMelee(boolean canMelee) {
        this.canMelee = canMelee;
    }

    public int getSHOT_COOLDOWN() {
        return SHOT_COOLDOWN;
    }

    public MeleeAttack getSword() {
        return sword;
    }

    public void setSword(MeleeAttack sword) {
        this.sword = sword;
    }
    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    public int getBurnTicks() {
        return burnTicks;
    }

    public void setBurnTicks(int burnTicks) {
        this.burnTicks = burnTicks;
    }

    public int getIntialBurn() {
        return intialBurn;
    }

    public void setIntialBurn(int intialBurn) {
        this.intialBurn = intialBurn;
    }


    public int getBurnCountDown() {
        return burnCountDown;
    }

    public void setBurnCountDown(int burnCountDown) {
        this.burnCountDown = burnCountDown;
    }

    public boolean isInFireRange() {
        return isFireInRange;
    }

    public void setFireInRange(boolean fireInRange) {
        isFireInRange = fireInRange;
    }

    public boolean isFireInRange() {
        return isFireInRange;
    }

    public int getShotRange() {
        return shotRange;
    }

    public void setShotRange(int shotRange) {
        this.shotRange = shotRange;
    }

    public long getTimeSinceHit() {
        return timeSinceHit;
    }

    public void setTimeSinceHit(long timeSinceHit) {
        this.timeSinceHit = timeSinceHit;
    }

    public int getRegenCooldown() {
        return regenCooldown;
    }

    public void setRegenCooldown(int regenCooldown) {
        this.regenCooldown = regenCooldown;
    }
}