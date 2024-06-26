package Creatures.Enemies.Magic;

import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import com.raylib.Raylib;
import Handlers.*;
import Attacks.*;
import java.util.Random;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;

public class MagicEnemy extends Enemy {
    private int spellCoolDown;
    private int shotSpeed;
    private float drawRadious;
    private int spellRange;
    private int randMoveSpeed;
    private Raylib.Color PoolColor;
    private CooldownHandler shotCooldown;
    private  CooldownHandler randMoveCooldown;
    private Random rand;
    Raylib.Vector2 newPos;


    private Random random;

    public MagicEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int moveRange, int spellRange, int shotSpeed, int numCoins, Raylib.Color color, Camera2D camera) {
        super(hp, damage, posX, posY, moveSpeed, size, moveRange, numCoins, color, camera);
        this.shotSpeed = shotSpeed;
        this.spellRange = spellRange;
        random = new Random();
        PoolColor = ColorFromHSV(278,.93f,.26f);
        shotCooldown = new CooldownHandler();
        randMoveCooldown = new CooldownHandler();
        rand = new Random();
        newPos = new Raylib.Vector2();
        setInitialSpeed(getMoveSpeed());
        randMoveSpeed = moveSpeed -1;

    }

    public void castLongSpell(Player player, ProjectileHandler projList, Raylib.Color color, String tag, Camera2D camera) {
        Projectile spell = new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getPosX(), player.getPosY(), tag, spellRange, true, camera, color);
        spell.createShotLine(camera);
        projList.add(spell);
    }

    public void castCloseSpell(Player player, ProjectileHandler projList, Raylib.Color color, Camera2D camera) {
        Projectile closeSpell1 = new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getPosX(), player.getPosY(), "Enemy", spellRange, true,camera, color);
        Projectile closeSpell2= new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getPosX(), player.getPosY(), "Enemy", spellRange, true, camera, color);
        closeSpell1.triangleShot("above", camera);
        closeSpell2.triangleShot("below", camera);
        projList.add(closeSpell1);
        projList.add(closeSpell2);
    }

    public void castPoolSpell(Player player, ProjectileHandler projList, Raylib.Color color, Camera2D camera) {
        Projectile poolShot = new Projectile(shotSpeed, getPosX(), getPosY(), 5, player.getPosX(),player.getPosY(), "Enemy_Pool_Shot", spellRange, true, camera, color);
        poolShot.createShotLine(camera);
        projList.add(poolShot);

    }

//    public void castHomingSpell(Player player, ProjectileHandler projList, Raylib.Color color, String tag, double moveSpeed, Camera2D camera){
//        Projectile spell = new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getVector(), tag, spellRange, true, camera, color);
//        spell.homingShot(moveSpeed,player);
//        projList.add(spell);
//    }

    public void shoot(Player player, ProjectileHandler projList, Raylib.Color color, Camera2D camera) {
        int rand = random.nextInt(2) + 1;
//        far away chooses between a long shot or pool shot
        if (getRange() > getVector().distanceToOtherObject(player.getPosX(), player.getPosY())) {
//            checking the cooldowns
            if (shotCooldown.cooldown(1500)) {
//                checking if its doing the close shot or not
                if (getVector().distanceToOtherObject(player.getPosX(), player.getPosY()) < getRange() / 2) {
                    closeRangeShot(player, projList, color, camera);
                    return;
                }
                shotFromDistance(player, projList, rand, color, camera);
            }
        }
    }

    private void shotFromDistance(Player player, ProjectileHandler projList, int rand, Color color, Camera2D camera) {
        if (getVector().distanceToOtherObject(player.getPosX(),player.getPosY()) > getRange() / 1.5) {
            if (rand == 1) {
                castLongSpell(player, projList, color, "Enemy", camera);
            } else {
//                castPoolSpell(player, projList, PoolColor, camera);
                castLongSpell(player, projList, color, "Enemy", camera);
            }
        }
    }

    private void closeRangeShot(Player player,  ProjectileHandler projList, Color color, Camera2D camera) {
        if (getVector().distanceToOtherObject(player.getPosX(), player.getPosY()) < getRange() / 2) {
//          castCloseSpell(player, projList, color, camera);
            castLongSpell(player, projList, color, "Enemy", camera);
        }
    }


    public void move(Player player, Camera2D camera){
        if (getVector().distanceToOtherObject(player.getPosX(),player.getPosY()) > getRange() / 2 &&!isRandMoving()) {
            getVector().setMoveSpeed(getInitialMoveSpeed());
            getVector().moveObject(player.getPosition(), "to", camera);
        }
        else{
            getVector().setMoveSpeed(randMoveSpeed);
            getVector().randEnemyMove(player,this,300, camera);
        }
    }

    public void drawHat(Camera2D camera){
        VectorHandler v1 = new VectorHandler(getPosX(), getPosY() - 50, getMoveSpeed(), camera);
        VectorHandler v2 = new VectorHandler(getPosX() - 20, getPosY() - 30, getMoveSpeed(), camera);
        VectorHandler v3 = new VectorHandler(getPosX() + 20, getPosY() - 30, getMoveSpeed(), camera);
        DrawTriangle(v1.getPosition(),v2.getPosition(),v3.getPosition(),BLACK);
    }

    public void update(Player player, ProjectileHandler projList, Raylib.Color color, Camera2D camera) {
        DrawCircle(getPosX(), getPosY() - 50, (float) (spellCoolDown / 7.5), color);
        shoot(player,projList, color, camera);
        move(player, camera);
        drawHat(camera);
    }

    public int getSpellCoolDown() {
        return spellCoolDown;
    }

    public void setSpellCoolDown(int spellCoolDown) {
        this.spellCoolDown = spellCoolDown;
    }

    public int getShotSpeed() {
        return shotSpeed;
    }

    public void setShotSpeed(int shotSpeed) {
        this.shotSpeed = shotSpeed;
    }

    public float getDrawRadious() {
        return drawRadious;
    }

    public void setDrawRadious(float drawRadious) {
        this.drawRadious = drawRadious;
    }

    public int getSpellRange() {
        return spellRange;
    }

    public void setSpellRange(int spellRange) {
        this.spellRange = spellRange;
    }

    public Color getPoolColor() {
        return PoolColor;
    }

    public void setPoolColor(Color poolColor) {
        PoolColor = poolColor;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}