package Creatures;

import Attacks.Projectile;
import Debuffs.Poison;
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
    private int regenAmt;

    //    DMG
    private int damage;
    private int range;

    //    POS
    private VectorHandler vector;
    //    Move
    private int moveSpeed;
    private int initialMoveSpeed;

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
    private boolean isPoisoned;
    private int poisonTicks;
    private Poison poison = new Poison(5,.3f,1.3f,2500);

    //    cooldowns
    private boolean canShoot;
    private boolean canMelee;
    private boolean canRegen;
    private int shotCD;
    private int initialShotCD;
    private long timeSinceHit;
    private int regenCooldownMilliseconds;
    private CooldownHandler regenCooldown;
    private CooldownHandler shotCooldownHandler;
    private CooldownHandler applyRegenCooldown;
    private CooldownHandler poisonCooldown;

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
    private Camera2D camera;

    public Player(int hp, int damage, int meleeRange, int posX, int posY, int moveSpeed, int size, int shotRange, Camera2D camera, Raylib.Color color) {
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
        regenCooldownMilliseconds = 10000;
        fire = new Fire();
        vector = new VectorHandler(posX, posY, moveSpeed, camera);
        regenCooldown = new CooldownHandler();
        applyRegenCooldown = new CooldownHandler();
        shotCooldownHandler = new CooldownHandler();
        poisonCooldown = new CooldownHandler();
        this.camera = camera;
        regenAmt = 10;
        shotCD = 250;
        initialShotCD = shotCD;
        initialMoveSpeed = moveSpeed;

    }

    public void update(ProjectileHandler projList, Camera2D camera, Raylib.Vector2 mousePos) {
        move(camera);
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)){
            shoot(projList, camera, mousePos);
        }
        fireHex();
        setShooting(false);
        shotCooldown();
        burn();
        regen();
        poisoned();
        Jaylib.Vector2 pos = new Jaylib.Vector2((float) getPosX(),(float)getPosY()+ size);
        camera.target(pos);
        DrawCircle((int)getPosition().x(),(int)getPosition().y(), size, color);
        DrawText("moveSpeed " + moveSpeed,100,400,30,BLACK);
//        DrawCircle(getPosX() / GetScreenWidth() /2, getPosY() / GetScreenHeight() / 2, size, color);
    }

    public void burn() {
        if (isOnFire){
            fire.burn(this);
        }
    }

    public void shoot(ProjectileHandler projList, Camera2D camera, Raylib.Vector2 mousePos) {
        if (canShoot()) {
            int mouseX, mouseY;
            mouseX = (int)mousePos.x();
            mouseY = (int) mousePos.y();
            setCanShoot(false);
            setShooting(true);
            Projectile shot = new Projectile(13, getPosX(), getPosY() , 7,  mouseX, mouseY, "Player", getShotRange(), true, camera, BLACK);
            shot.setShotTag("Player");
            shot.createShotLine(camera);
            projList.add(shot);
        }
    }


    public void shotCooldown(){
        if (!canShoot()){
            if(shotCooldownHandler.cooldown(shotCD)){
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
            if (getHp() + regenAmt < getInitalHp()) {
                setHp(getHp() + regenAmt);
            } else {
                setHp(getInitalHp());
            }
        }
    }
    public void move(Camera2D camera) {
        vector.setMoveSpeed(getMoveSpeed());
        vector.playerMove(camera);
    }
    public void poisoned(){
        if(isPoisoned){
            if(poisonCooldown.cooldown(poison.getLifetime())){
                moveSpeed = initialMoveSpeed;
                shotCD = initialShotCD;
                setPoisoned(false);
            }
        }
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

    public Raylib.Vector2 getPosition() {
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

    @Override
    public void setFireInRange(boolean isFireInRange){
        isFireInRange = isFireInRange;
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

    @Override
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

    public CooldownHandler getShotCooldownHandler() {
        return shotCooldownHandler;
    }

    public void setShotCooldownHandler(CooldownHandler shotCooldownHandler) {
        this.shotCooldownHandler = shotCooldownHandler;
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

    @Override
    public void setPoisoned(boolean poisoned){
        isPoisoned = poisoned;
    }

    @Override
    public void setPoisonTicks(int poisonTicks){
        this.poisonTicks = poisonTicks;
    }
    @Override
    public boolean isPoisoned(){
        return isPoisoned;
    }

    @Override
    public int getPoisonTicks(){
        return poisonTicks;
    }

    @Override
    public int getShotcooldown() {
        return shotCD;
    }

    @Override
    public void setShotCooldown(int shotCooldown){
        this.shotCD = shotCooldown;
    }

    public int getInitialMoveSpeed() {
        return initialMoveSpeed;
    }

    public void setInitialMoveSpeed(int initialMoveSpeed) {
        this.initialMoveSpeed = initialMoveSpeed;
    }

    public Poison getPoison() {
        return poison;
    }

    public void setPoison(Poison poison) {
        this.poison = poison;
    }
}