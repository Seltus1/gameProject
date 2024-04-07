package Attacks;
import Creatures.Enemies.Brawler.BrawlerEnemy;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import Handlers.*;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Shield {
    private VectorHandler vector;
    private Raylib.Vector2 linePoint1;
    private Raylib.Vector2 linePoint2;
    private int maxDamageToDeal;
    private int currentDamageDealt;
    private int shieldRegen;
    private int totalTimeNeededSinceLastHitToRegen;
    private CooldownHandler shieldRegenCD;
    private HealthHandler regenTimer;
    private boolean regenShield;
    private boolean didUpdateSpeed;
    private Raylib.Vector2 shieldCenterPoint;
    private boolean shieldTookDamage;
    private HealthHandler shieldRegener;
    private HealthHandler shieldDamager;
    private int shieldDefense;


    public Shield(Camera2D camera, Player player) {
        vector = new VectorHandler(0, 0, 0, camera);
         player.setCanUseSecondary(true);
         player.setShieldHp(150);
         player.setShieldMaxHp(150);
        maxDamageToDeal = 300;
        shieldRegen = 10;
        totalTimeNeededSinceLastHitToRegen = 5000;
        shieldRegenCD = new CooldownHandler();
        regenTimer = new HealthHandler();
        didUpdateSpeed = false;
        shieldRegener = new HealthHandler();
        shieldDamager = new HealthHandler();
        shieldDefense = 60;
    }

    public void update(Player player, Raylib.Vector2 mousePos, ProjectileHandler projList, Camera2D camera, EnemyHandler enemies){
        defend(player, mousePos, projList, camera, enemies);
        updateMovingSpeed(player);
        if(!player.isCanUseSecondary()){
            shieldCD(player);
        }
        else {
            regenShield(player);
        }
    }

    public void defend(Player player, Raylib.Vector2 mousePos, ProjectileHandler projList, Camera2D camera, EnemyHandler enemies){
        if (IsMouseButtonDown(MOUSE_BUTTON_RIGHT) && player.isCanUseSecondary() && !player.isUsingUtility()){
            double[] poses = calculateShieldLocation(player,mousePos);
            drawShield(poses, player);
            calculateShieldCenterPoint();
            for (int i = 0; i < projList.size() ; i++){
                Projectile projectile = (Projectile) projList.get(i);
                if (!projectile.isDraw()) {
                    continue;
                }
                calculateShieldAndCollision(player, projectile, projList);
            }
            for( int i = 0; i < enemies.size(); i++){
                Enemy enemy = (Enemy) enemies.get(i);
                checkEnemyCollisionWithShield(player,enemy, enemies, camera);
            }
        }

    }


//    shield block helper fucntions
    public double[] calculateShieldLocation(Player player, Raylib.Vector2 mousePos){
        Raylib.Vector2 endPoint = vector.findEndPointOfLine(player.getPosition(), player.getRange() / 2, mousePos);
        double[] poses = vector.findIntersectingPoints(player.getPosition(),endPoint, player.getRange() / 2,35, mousePos);
        linePoint1 = new Raylib.Vector2(new Jaylib.Vector2((float) poses[0], (float) poses[1]));
        linePoint2 = new Raylib.Vector2(new Jaylib.Vector2((float) poses[2], (float) poses[3]));

        return poses;
    }
    private void calculateShieldCenterPoint(){
        float centerX = (linePoint1.x() + linePoint2.x()) / 2;
        float centerY = (linePoint1.y() + linePoint2.y()) / 2;
        shieldCenterPoint = new Raylib.Vector2(new Jaylib.Vector2(centerX, centerY));
    }
    public void drawShield(double[] poses, Player player){
        if(player.isUsingUltimate()){
            DrawCircleLines(player.getPosX(), player.getPosY(), 37, BLACK);
            return;
        }
        DrawLine((int)poses[0],(int)poses[1],(int)poses[2],(int)poses[3], BLACK);
    }

    private void shieldCD(Player player){
        if(player.getShieldCD().cooldown(player.getTotalShieldCD())){
            player.setCanUseSecondary(true);
            player.setShieldHp(player.getShieldMaxHp());
        }
    }
    private void shieldingWhileUltimate(Player player, Projectile projectile){
        //             37 is the distance from the player to the shield
        //             (dont ask why it just is) - dany 2/27/24
        if(CheckCollisionCircles(projectile.getPosition(),projectile.getShotRad(),player.getPosition(), 37) && !projectile.isDidCollideWithShield()){
            projectile.setDidCollideWithShield(true);
            projectile.setMoveSpeed(projectile.getShotSpeed() * -1);
            projectile.setColor(ColorFromHSV(196f,.67f,.97f));
            projectile.setShotTag("Player");
        }
    }
    private void collidedWithShield(Player player, Projectile projectile, ProjectileHandler projList){
//    HERE projectile.getDamage() DAMAGE SHIELD
        shieldDamager.damageShield(player,projectile.getDamage(),this);
        projList.removeObject(projectile);
        if (player.getShieldHp() <= 0) {
            player.setCanUseSecondary(false);
        }
    }
    private void enemyCollisionsWithShield(Enemy enemy, EnemyHandler enemies, Player player, Camera2D camera){
//        enemy.setHp(enemy.getHp() - 5);
        if(!shieldTookDamage) {
            player.setShieldHp(player.getShieldHp() - 5);
            shieldRegenCD.resetCooldown();
            regenShield = false;
            shieldTookDamage = true;
        }
        if (player.getShieldHp() <= 0) {
            player.setCanUseSecondary(false);
        }
        if(enemy instanceof BrawlerEnemy){
            BrawlerEnemy brawlerEnemy = (BrawlerEnemy) enemy;
            brawlerEnemy.setCollidedWithShield(true);
        }
    }

    private void calculateShieldAndCollision(Player player, Projectile projectile, ProjectileHandler projList) {
        if (player.isUsingUltimate()) {
            shieldingWhileUltimate(player, projectile);
        } else {
            calculateShieldCenterPoint();
            if (!projectile.getShotTag().contains("Pool")) {
                if (CheckCollisionCircles(shieldCenterPoint, projectile.getShotSpeed(), projectile.getPosition(), projectile.getShotRad())) {
                    if (vector.canTheProjectileHitThePlayerCircle(projectile, player, linePoint1, linePoint2)) {
                        collidedWithShield(player, projectile, projList);
                    }
                }
            }
        }
    }
    private void checkEnemyCollisionWithShield(Player player, Enemy enemy, EnemyHandler enemies, Camera2D camera){
//       the shield becomes a circle while ulted, so a new check is needed to know if the enemy hit the shield.
        if(player.isUsingUltimate()){
//            why 37? check projectile collision with shield
            if(CheckCollisionCircles(enemy.getPos(),enemy.getSize(),player.getPosition(), 37)){
                enemyCollisionsWithShield(enemy, enemies,player,camera);
            }
            return;
        }
//        regular enemy collision with the shield
        if (CheckCollisionCircles(shieldCenterPoint, enemy.getMoveSpeed(), enemy.getPos(), enemy.getSize())) {
            if (vector.canTheEnemyHitThePlayerCircle(enemy, player, linePoint1, linePoint2)) {
                enemyCollisionsWithShield(enemy, enemies, player, camera);
                return;
            }
        }
        shieldTookDamage = false;
    }
    private void regenShield(Player player){
        shieldRegener.regenShield(player,this);
    }


//    block helper functions end here

    private void updateMovingSpeed(Player player){
        if(IsMouseButtonDown(MOUSE_BUTTON_RIGHT) && player.isCanUseSecondary() && !player.isUsingUtility() && !player.isUsingUltimate()){
            player.setUsingSecondary(true);
            if(!didUpdateSpeed) {
                player.setShieldingSpeed(player.getMoveSpeed() / 2);

                didUpdateSpeed = true;
                player.setMoveSpeed(player.getShieldingSpeed());
            }
        }
        else {
            player.setMoveSpeed(player.getInitialMoveSpeed());
            player.setUsingSecondary(false);
            didUpdateSpeed = false;
        }
    }



    public Raylib.Vector2 getLinePoint1() {
        return linePoint1;
    }

    public void setLinePoint1(Raylib.Vector2 linePoint1) {
        this.linePoint1 = linePoint1;
    }

    public Raylib.Vector2 getLinePoint2() {
        return linePoint2;
    }

    public void setLinePoint2(Raylib.Vector2 linePoint2) {
        this.linePoint2 = linePoint2;
    }

    public int getMaxDamageToDeal() {
        return maxDamageToDeal;
    }

    public void setMaxDamageToDeal(int maxDamageToDeal) {
        this.maxDamageToDeal = maxDamageToDeal;
    }

    public int getCurrentDamageDealt() {
        return currentDamageDealt;
    }

    public void setCurrentDamageDealt(int currentDamageDealt) {
        this.currentDamageDealt = currentDamageDealt;
    }

    public VectorHandler getVector() {
        return vector;
    }

    public void setVector(VectorHandler vector) {
        this.vector = vector;
    }

    public int getShieldRegen() {
        return shieldRegen;
    }

    public void setShieldRegen(int shieldRegen) {
        this.shieldRegen = shieldRegen;
    }

    public int getTotalTimeNeededSinceLastHitToRegen() {
        return totalTimeNeededSinceLastHitToRegen;
    }

    public void setTotalTimeNeededSinceLastHitToRegen(int totalTimeNeededSinceLastHitToRegen) {
        this.totalTimeNeededSinceLastHitToRegen = totalTimeNeededSinceLastHitToRegen;
    }

    public CooldownHandler getShieldRegenCD() {
        return shieldRegenCD;
    }

    public void setShieldRegenCD(CooldownHandler shieldRegenCD) {
        this.shieldRegenCD = shieldRegenCD;
    }

    public HealthHandler getRegenTimer() {
        return regenTimer;
    }

    public void setRegenTimer(HealthHandler regenTimer) {
        this.regenTimer = regenTimer;
    }

    public boolean isRegenShield() {
        return regenShield;
    }

    public void setRegenShield(boolean regenShield) {
        this.regenShield = regenShield;
    }

    public boolean isDidUpdateSpeed() {
        return didUpdateSpeed;
    }

    public void setDidUpdateSpeed(boolean didUpdateSpeed) {
        this.didUpdateSpeed = didUpdateSpeed;
    }

    public Raylib.Vector2 getShieldCenterPoint() {
        return shieldCenterPoint;
    }

    public void setShieldCenterPoint(Raylib.Vector2 shieldCenterPoint) {
        this.shieldCenterPoint = shieldCenterPoint;
    }

    public boolean isShieldTookDamage() {
        return shieldTookDamage;
    }

    public void setShieldTookDamage(boolean shieldTookDamage) {
        this.shieldTookDamage = shieldTookDamage;
    }

    public int getShieldDefense() {
        return shieldDefense;
    }

    public void setShieldDefense(int shieldDefense) {
        this.shieldDefense = shieldDefense;
    }
}
