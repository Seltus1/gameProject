package Creatures.Players;

import Creatures.Creature;
import Debuffs.Poison;
import Handlers.*;
import Elements.Fire;
import Interactables.Interactable;
import Items.BasicItem;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;

import static com.raylib.Jaylib.*;

public abstract class Player implements Creature {
    //    HP
    private int hp;
    private int initalHp;
    private int regenAmt;
    private int shieldMaxHp;
    private int shieldHp;
    private int defence;








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










    //   Player states
    private boolean isAlive;
    private boolean isOnFire;
    private boolean isFireHex;
    private boolean reachedDestination;
    private boolean directionLocked;
    private boolean isShooting;
    private boolean isRegening;
    private boolean isPoisoned;
    private boolean isMeleeing;
    private boolean isUsingUtility;
    private boolean isUsingSecondary;
    private boolean isUsingUltimate;
    private boolean didMelee;
    private boolean isUsingSpaceSuit;
    private boolean connectingPowerLine;











    //    cooldowns
    private CooldownHandler meleeCD;
    private CooldownHandler regenCooldown;
    private CooldownHandler shotCooldownHandler;
    private CooldownHandler applyRegenCooldown;
    private CooldownHandler shieldCD;
    private CooldownHandler chargeCD;
    private CooldownHandler poisonCooldown;
    private CooldownHandler ultimateTimer;
    private CooldownHandler secondaryTimer;
    private CooldownHandler specialTimer;
    private CooldownHandler primaryTimer;
    private CooldownHandler outsideBorderDmgCH;
    private CooldownHandler spaceSuitOxygenCH;


    private boolean canShoot;
    private boolean canMelee;
    private boolean canRegen;
    private boolean canUseSecondary;
    private boolean canUseUtility;
    private boolean canUseUltimate;
    private boolean startUltCD;


    private int shotCD;
    private int initialShotCD;
    private int regenCooldownMilliseconds;
    private int totalShieldCD;
    private int totalChargeCD;
    private int infernoCooldown;
    private int ultimateUpTime;
    private int ultimateCD;
    private int ultimateCooldown;
    private int secondaryUpTime;
    private int outsideBorderDmgCD;
    private int spaceSuitOxygenDecreaseCD;
    private int spaceSuitOxygenIncreaseCD;











    //    instance of other stuffs
    private final Fire fire;
    private Camera2D camera;
    private Poison poison = new Poison(5,.3f,1.3f,2500);
    private HealthHandler playerHP;
    private Interactable materializer;





//    info vars



    private int burnDamage;
    private int burnCountDown;
    private int InfernoCount;
    private boolean isFireInRange;
    private int shotRange;
    private int fireHexCount;
    private int shotFrameCount;
    private int burnTicks;
    private int poisonTicks;
    private int intialBurn;
    private int shieldingSpeed;
    private CooldownHandler attackCooldown;
    private ArrayList<BasicItem> items;
    private int itemTotal;
    private boolean isOutsideArea;
    private int numCoins;
    private float outsideBorderSpeedMult;
    private int spaceSuitOxygen;
    private int initialSpaceSuitOxygen;
    private boolean didEquipMaterializer;
    private boolean drawPlayer;



    public Player(int hp, int damage, int range, int posX, int posY, int moveSpeed, int size, Camera2D camera, Raylib.Color color) {
        this.hp = hp;
        initalHp = hp;
        this.damage = damage;
        this.range = range;
        this.moveSpeed = moveSpeed;
        this.size = size;
        this.color = color;
        this.isAlive = true;
        this.canShoot = true;
        this.canMelee = true;
        this.camera = camera;


        isOnFire = false;
        isRegening = false;
        drawPlayer = true;


        burnCountDown = 0;
        numCoins = 0;
        burnDamage = 1;
        intialBurn = 10;
        regenCooldownMilliseconds = 10000;
        regenAmt = 10;
        shotCD = 250;
        itemTotal = 0;
        outsideBorderDmgCD = 250;
        initialShotCD = shotCD;
        initialMoveSpeed = moveSpeed;
        setShieldMaxHp(150);
        outsideBorderSpeedMult = .5f;
        spaceSuitOxygenDecreaseCD = 100;
        spaceSuitOxygenIncreaseCD = 200;
        spaceSuitOxygen = 100;
        initialSpaceSuitOxygen = 100;

        fire = new Fire();
        vector = new VectorHandler(posX, posY, moveSpeed, camera);
        regenCooldown = new CooldownHandler();
        applyRegenCooldown = new CooldownHandler();
        shotCooldownHandler = new CooldownHandler();
        poisonCooldown = new CooldownHandler();
        shieldCD = new CooldownHandler();
        chargeCD = new CooldownHandler();
        meleeCD = new CooldownHandler();
        ultimateTimer = new CooldownHandler();
        specialTimer = new CooldownHandler();
        secondaryTimer = new CooldownHandler();
        primaryTimer = new CooldownHandler();
        attackCooldown = new CooldownHandler();
        outsideBorderDmgCH = new CooldownHandler();
        spaceSuitOxygenCH = new CooldownHandler();
        items = new ArrayList<>();
        playerHP = new HealthHandler();

    }

    public void update(ProjectileHandler projList, Camera2D camera, Raylib.Vector2 mousePos, EnemyHandler enemies, GameHandler game, InteractablesHandler interactables) {
        equipMaterializer(interactables);
        playerOutsideBorderArea();
        if(!directionLocked){
            move(camera);
        }
        checkIfIsMeleeing();
//        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)){
//            shoot(projList, camera, mousePos);
//        }
        equipSpaceSuit();
        usingSpaceSuit();
        fireHex();
        setShooting(false);
        shotCooldown();
        burn();
//        DrawText(getItemTotal() + "", getPosX() - 100, getPosY(), 20, BLACK);
        regen();
        poisoned();
        Jaylib.Vector2 pos = new Jaylib.Vector2((float) getPosX(),(float)getPosY() + size);
        camera.target(pos);
//        DrawText(game.getAreaSize() + "", getPosX(), getPosY() - 100,30, BLACK);

//        vector.rangeLine(this,mousePos);
    }

    public void burn() {
        if (isOnFire){
            fire.burn(this);
        }
    }


// function for the original shoot

//    public void shoot(ProjectileHandler projList, Camera2D camera, Raylib.Vector2 mousePos) {
//        if (canShoot()) {
//            int mouseX, mouseY;
//            mouseX = (int)mousePos.x();
//            mouseY = (int) mousePos.y();
//            setCanShoot(false);
//            setShooting(true);
//            Projectile shot = new Projectile(13, getPosX(), getPosY() , 7,  mouseX, mouseY, "Creatures/Players", getShotRange(), true, camera, BLACK);
//            shot.setShotTag("Player");
//            shot.createShotLine(camera);
//            projList.add(shot);
//        }
//    }


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

    public void move(Camera2D camera) {
        vector.playerMove(camera);
    }
    public void equipSpaceSuit(){
        if(IsKeyPressed(KEY_LEFT_SHIFT)){
            if(isUsingSpaceSuit){
                isUsingSpaceSuit = false;
            }
            else if(spaceSuitOxygen > 75){
                isUsingSpaceSuit = true;
            }
        }
    }
    public void usingSpaceSuit(){
        if(isUsingSpaceSuit){
            if(spaceSuitOxygenCH.cooldown(spaceSuitOxygenDecreaseCD)){
                spaceSuitOxygen--;
                if(spaceSuitOxygen == 0){
                    isUsingSpaceSuit = false;
                }
            }
        }
    }
    public void equipMaterializer(InteractablesHandler interactables){
        if(IsKeyDown(KEY_SPACE)){
            DrawCircle(getPosX()-50,getPosY(),5,BLACK);
            didEquipMaterializer = true;
        }
        else{
            didEquipMaterializer = false;
        }
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
//    public abstract void primary();
//    public abstract void secondary();
//    public abstract void utility();
//    public abstract void ultimate();
    private void checkIfIsMeleeing() {
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT) && !isUsingUtility() && !isMeleeing && !isUsingSecondary()){
            setMeleeing(true);
            return;
        }
        setMeleeing(false);
    }
    public void checkIfOutsideArea(GameHandler game){
        if(vector.distanceBetweenTwoObjects(getPosition(),game.getCenterOfArena()) > game.getAreaSize()/2){
            isOutsideArea = true;
            return;
        }
        isOutsideArea = false;
    }
    public void playerOutsideBorderArea(){
        if(isOutsideArea && !isUsingSpaceSuit){
            if(outsideBorderDmgCH.cooldown(outsideBorderDmgCD)){
                playerHP.damagePlayer(this,2);
            }
            setMoveSpeed(getMoveSpeed() - (int) (getMoveSpeed() * outsideBorderSpeedMult));
        }
    }
    public void newItemAdded(BasicItem item){
        item.applyStatsOrEffect(this);
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
        vector.setMoveSpeed(moveSpeed);
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
    public void setAttacking(boolean isAttacking) {

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
    public boolean didMelee() {
        return didMelee;
    }


    @Override
    public void setShotCooldown(int shotCooldown){
        this.shotCD = shotCooldown;
    }

    @Override
    public void setDidMelee(boolean didMelee) {
        this.didMelee = didMelee;
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

    public boolean isMeleeing() {
        return isMeleeing;
    }

    public void setMeleeing(boolean meleeing) {
        isMeleeing = meleeing;
    }

    public int getShieldingSpeed() {
        return shieldingSpeed;
    }

    public void setShieldingSpeed(int shieldingSpeed) {
        this.shieldingSpeed = shieldingSpeed;
    }

    public boolean isDirectionLocked() {
        return directionLocked;
    }
    public void setDirectionLocked(boolean directionLocked) {
        this.directionLocked = directionLocked;
    }

    public boolean isReachedDestination() {
        return reachedDestination;
    }

    public void setReachedDestination(boolean reachedDestination) {
        this.reachedDestination = reachedDestination;
    }

    public boolean isUsingUtility() {
        return isUsingUtility;
    }

    public void setUsingUtility(boolean usingUtility) {
        isUsingUtility = usingUtility;
    }

    public boolean isUsingSecondary() {
        return isUsingSecondary;
    }

    public void setUsingSecondary(boolean usingSecondary) {
        isUsingSecondary = usingSecondary;
    }

    public int getShieldMaxHp() {
        return shieldMaxHp;
    }

    public void setShieldMaxHp(int shieldThreshold) {
        this.shieldMaxHp = shieldThreshold;
    }

    public int getShieldHp() {
        return shieldHp;
    }

    public void setShieldHp(int shieldHp) {
        this.shieldHp = shieldHp;
    }

    public boolean isCanUseSecondary() {
        return canUseSecondary;
    }

    public void setCanUseSecondary(boolean canUseSecondary) {
        this.canUseSecondary = canUseSecondary;
    }

    public CooldownHandler getShieldCD() {
        return shieldCD;
    }

    public void setShieldCD(CooldownHandler shieldCD) {
        this.shieldCD = shieldCD;
    }

    public int getTotalShieldCD() {
        return totalShieldCD;
    }

    public void setTotalShieldCD(int totalShieldCD) {
        this.totalShieldCD = totalShieldCD;
    }

    public CooldownHandler getChargeCD() {
        return chargeCD;
    }

    public void setChargeCD(CooldownHandler chargeCD) {
        this.chargeCD = chargeCD;
    }

    public Boolean getCanUseUtility() {
        return canUseUtility;
    }

    public void setCanUseUtility(Boolean canUseUtility) {
        this.canUseUtility = canUseUtility;
    }

    public int getTotalChargeCD() {
        return totalChargeCD;
    }

    public void setTotalChargeCD(int totalChargeCD) {
        this.totalChargeCD = totalChargeCD;
    }

    public CooldownHandler getMeleeCD() {
        return meleeCD;
    }

    public boolean isCanUseUltimate() {
        return canUseUltimate;
    }

    public void setCanUseUltimate(boolean canUseUltimate) {
        this.canUseUltimate = canUseUltimate;
    }

    public boolean isUsingUltimate() {
        return isUsingUltimate;
    }

    public void setUsingUltimate(boolean usingUltimate) {
        isUsingUltimate = usingUltimate;
    }

    public int getUltimateUpTime() {
        return ultimateUpTime;
    }

    public void setUltimateUpTime(int ultimateUpTime) {
        this.ultimateUpTime = ultimateUpTime;
    }

    public int getUltimateCD() {
        return ultimateCD;
    }

    public void setUltimateCD(int ultimateCD) {
        this.ultimateCD = ultimateCD;
    }

    public int getDefenceOrArmorForEthan() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public CooldownHandler getUltimateTimer() {
        return ultimateTimer;
    }

    public void setUltimateTimer(CooldownHandler ultimateTimer) {
        this.ultimateTimer = ultimateTimer;
    }

    public CooldownHandler getPoisonCooldown() {
        return poisonCooldown;
    }

    public void setPoisonCooldown(CooldownHandler poisonCooldown) {
        this.poisonCooldown = poisonCooldown;
    }

    public CooldownHandler getSecondaryTimer() {
        return secondaryTimer;
    }

    public void setSecondaryTimer(CooldownHandler secondaryTimer) {
        this.secondaryTimer = secondaryTimer;
    }

    public CooldownHandler getSpecialTimer() {
        return specialTimer;
    }

    public void setSpecialTimer(CooldownHandler specialTimer) {
        this.specialTimer = specialTimer;
    }

    public CooldownHandler getPrimaryTimer() {
        return primaryTimer;
    }

    public void setPrimaryTimer(CooldownHandler primaryTimer) {
        this.primaryTimer = primaryTimer;
    }

    public CooldownHandler getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(CooldownHandler attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public ArrayList<BasicItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<BasicItem> items) {
        this.items = items;
    }

    public int getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(int itemTotal) {
        this.itemTotal = itemTotal;
    }

    public boolean isStartUltCD() {
        return startUltCD;
    }

    public void setStartUltCD(boolean startUltCD) {
        this.startUltCD = startUltCD;
    }

    public int getSecondaryUpTime() {
        return secondaryUpTime;
    }

    public void setSecondaryUpTime(int secondaryUpTime) {
        this.secondaryUpTime = secondaryUpTime;
    }

    public boolean isOutsideArea() {
        return isOutsideArea;
    }

    public void setOutsideArea(boolean outsideArea) {
        isOutsideArea = outsideArea;
    }

    public int getNumCoins() {
        return numCoins;
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }

    public int getSpaceSuitOxygen() {
        return spaceSuitOxygen;
    }

    public void setSpaceSuitOxygen(int spaceSuitOxygen) {
        this.spaceSuitOxygen = spaceSuitOxygen;
    }

    public int getInitialSpaceSuitOxygen() {
        return initialSpaceSuitOxygen;
    }

    public void setInitialSpaceSuitOxygen(int initialSpaceSuitOxygen) {
        this.initialSpaceSuitOxygen = initialSpaceSuitOxygen;
    }

    public boolean isUsingSpaceSuit() {
        return isUsingSpaceSuit;
    }

    public void setUsingSpaceSuit(boolean usingSpaceSuit) {
        isUsingSpaceSuit = usingSpaceSuit;
    }

    public boolean isDidEquipMaterializer() {
        return didEquipMaterializer;
    }

    public void setDidEquipMaterializer(boolean didEquipMaterializer) {
        this.didEquipMaterializer = didEquipMaterializer;
    }

    public boolean isConnectingPowerLine() {
        return connectingPowerLine;
    }

    public void setConnectingPowerLine(boolean connectingPowerLine) {
        this.connectingPowerLine = connectingPowerLine;
    }

    public boolean isDrawPlayer() {
        return drawPlayer;
    }

    public void setDrawPlayer(boolean drawPlayer) {
        this.drawPlayer = drawPlayer;
    }
}