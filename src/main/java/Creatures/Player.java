package Creatures;

import Attacks.Projectile;
import Handlers.CooldownHandler;
import Handlers.ProjectileHandler;
import Elements.Fire;
import Handlers.VectorHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class Player implements Creature {
//    HP
    private int hp;
    private int initalHp;

//    DMG
    private int damage;
    private int range;

//    POS
    private VectorHandler vector;
//    Move
    private int moveSpeed;

//    size/color
    private int size;
    private Raylib.Color color;


    //    Creatures.Player states
    private boolean isAlive;
    private boolean isOnFire;
    private boolean isFireHex;
    private int burnTicks;
    private int intialBurn;
    private boolean isShooting;
    private boolean isRegening;

//    cooldowns
    private boolean canShoot;
    private boolean canMelee;
    private boolean canRegen;
    private final int SHOT_COOLDOWN = 250;
    private long timeSinceHit;
    private int regenCooldownMilliseconds;
    private CooldownHandler regenCooldown;
    private CooldownHandler shotCooldown;
    private CooldownHandler applyRegenCooldown;

    private int infernoCooldown;

    //    instance of other stuffs
    private int burnDamage;
    private int burnCountDown;
    private int InfernoCount;
    private boolean isFireInRange;
    private int shotRange;
    private final Fire fire;
    private int fireHexCount;
    private int shotFrameCount;

    public Player(int hp, int damage, int meleeRange, int posX, int posY, int moveSpeed, int size, int shotRange, Raylib.Color color) {
        this.hp = hp;
        initalHp = hp;
        this.damage = damage;
        this.range = meleeRange;
        this.moveSpeed = moveSpeed;
        this.size = size;
        this.color = color;
        this.shotRange = shotRange;
        this.isAlive = true;
        this.canShoot = true;
        isOnFire = false;
        this.canMelee = true;
        isRegening = false;
        burnCountDown = 0;
        intialBurn = 10;
        burnDamage = 1;
        regenCooldownMilliseconds = 5000;
        fire = new Fire();
        vector = new VectorHandler(posX, posY, moveSpeed);
        regenCooldown = new CooldownHandler();
        applyRegenCooldown = new CooldownHandler();
        shotCooldown = new CooldownHandler();
    }

    public void update(ProjectileHandler projList) {
        move();
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)){
            shoot(projList);
        }
        fireHex();
        setShooting(false);
        shotCooldown();
        burn();
        regen();
        DrawCircle(getPosX(), getPosY(), size, RED);
    }

    public void burn() {
        if (isOnFire){
            fire.burn(this);
        }
    }

    public void shoot(ProjectileHandler projList) {
        if (canShoot()) {
            setCanShoot(false);
            setShooting(true);
            int mouseX = GetMouseX();
            int mouseY = GetMouseY();
            Projectile shot = new Projectile(13, getPosX(), getPosY(), 7, mouseX, mouseY, "Player", getShotRange(), true, BLACK);
            shot.setShotTag("Player");
            shot.createShotLine();
            projList.add(shot);
        }
    }


    public void shotCooldown(){
        if (!canShoot()){
            if(shotCooldown.cooldown(250)){
                canShoot = true;
            }
        }
    }

    public void regen() {
        if (hpLessThanInitalHP()) {
//        if the hp is less then initalHp start the regen counter
            boolean regenCD = regenCooldown.cooldown(regenCooldownMilliseconds);
            if (regenCD) {
                canRegen = true;
            }
            if (canRegen) {
                applyingRegen();
            }
            return;
        }
//        if the hp is equal to the initial hp no need to regen
        canRegen = false;
        isRegening = false;
    }

    private boolean hpLessThanInitalHP(){
//        checking if the player needs to regen health or not
        if (hp < initalHp) {
            return true;
        }
        return false;
    }

    private void applyingRegen() {
        boolean canRegenCooldown = applyRegenCooldown.cooldown(150);
        if (canRegenCooldown) {
            if (getHp() + 10 < getInitalHp()) {
                setHp(getHp() + 10);
            } else {
                setHp(getInitalHp());
            }
        }
    }

    public void move() {
        vector.playerMove();
    }

    public void fireHex() {
        fire.fireHex(this);
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
        return vector.getPosX();
    }

    @Override
    public void setPosX(int posX) {
        vector.setPosX(posX);
    }

    @Override
    public int getPosY() {
        return vector.getPosY();
    }

    @Override
    public void setPosY(int posY) {
        vector.setPosY(posY);
    }

    public Jaylib.Vector2 getPosition() {
        return vector.getPosition();
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

    public boolean isOnFire() {
        return isOnFire;
    }

    @Override
    public int getFireHexCount() {
        return fireHexCount;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    @Override
    public void setFireHexCount(int hex) {
        fireHexCount = hex;
    }

    @Override
    public void setFireHex(boolean fireHex) {
        isFireHex = fireHex;
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

    public CooldownHandler getRegenCooldown() {
        return regenCooldown;
    }

    public void setRegenCooldown(int regenCooldown) {
        this.regenCooldownMilliseconds = regenCooldown;
    }


    public int getInfernoCount() {
        return InfernoCount;
    }

    public void setInfernoCount(int infernoCount) {
        InfernoCount = infernoCount;
    }

    public boolean isShooting() {
        return isShooting;
    }

    @Override
    public boolean isFireHex() {
        return isFireHex;
    }

    public VectorHandler getVector() {
        return vector;
    }

    public void setVector(VectorHandler vector) {
        this.vector = vector;
    }

    public boolean isRegening() {
        return isRegening;
    }

    public void setRegening(boolean regening) {
        isRegening = regening;
    }

    public boolean isCanRegen() {
        return canRegen;
    }

    public void setCanRegen(boolean canRegen) {
        this.canRegen = canRegen;
    }

    public int getRegenCooldownMilliseconds() {
        return regenCooldownMilliseconds;
    }

    public void setRegenCooldownMilliseconds(int regenCooldownMilliseconds) {
        this.regenCooldownMilliseconds = regenCooldownMilliseconds;
    }

    public CooldownHandler getShotCooldown() {
        return shotCooldown;
    }

    public void setShotCooldown(CooldownHandler shotCooldown) {
        this.shotCooldown = shotCooldown;
    }

    public CooldownHandler getApplyRegenCooldown() {
        return applyRegenCooldown;
    }

    public void setApplyRegenCooldown(CooldownHandler applyRegenCooldown) {
        this.applyRegenCooldown = applyRegenCooldown;
    }

    public int getInfernoCooldown() {
        return infernoCooldown;
    }

    public void setInfernoCooldown(int infernoCooldown) {
        this.infernoCooldown = infernoCooldown;
    }

    public int getBurnDamage() {
        return burnDamage;
    }

    public Fire getFire() {
        return fire;
    }

    public int getShotFrameCount() {
        return shotFrameCount;
    }

    public void setShotFrameCount(int shotFrameCount) {
        this.shotFrameCount = shotFrameCount;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }
}
