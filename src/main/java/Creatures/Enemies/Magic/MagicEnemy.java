package Creatures.Enemies.Magic;

import Creatures.Enemies.Enemy;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import Creatures.*;
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
    private Raylib.Color PoolColor;
    private CooldownHandler cooldown;
    private CooldownHandler cooldown2;
    private CooldownHandler cooldown3;
    private  CooldownHandler randMoveCooldown;
    private Random rand;
    Raylib.Vector2 newPos;


    private Random random;

    public MagicEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int moveRange, int spellRange, int shotSpeed, Raylib.Color color, Camera2D camera) {
        super(hp, damage, posX, posY, moveSpeed, size, moveRange, color, camera);
        this.shotSpeed = shotSpeed;
        this.spellRange = spellRange;
        random = new Random();
        PoolColor = ColorFromHSV(278,.93f,.26f);
        cooldown = new CooldownHandler();
        cooldown2 = new CooldownHandler();
        cooldown3 = new CooldownHandler();
        randMoveCooldown = new CooldownHandler();
        rand = new Random();
        newPos = new Raylib.Vector2();
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

    public void shoot(Player player, ProjectileHandler projList, Raylib.Color color, Camera2D camera){
        int rand = random.nextInt(2) + 1;
        if (getRange() > getVector().distanceToOtherObject(player.getPosX(),player.getPosY())) {
            if (getVector().distanceToOtherObject(player.getPosX(),player.getPosY()) > getRange() / 1.5) {
                if (cooldown.cooldown(1500)){
                    if (rand == 1) {
                        castLongSpell(player, projList, color, "Enemy", camera);
//                        castHomingSpell(player,projList,color,"homing",3);
                    } else {
                        castPoolSpell(player, projList, PoolColor, camera);
                    }
                }
            } else if (getVector().distanceToOtherObject(player.getPosX(),player.getPosY()) < getRange() / 2) {
                if (cooldown2.cooldown(1500)){
//                    castCloseSpell(player, projList, color, camera);
                    castLongSpell(player,projList,color,"Enemy", camera);
                }
            } else {
                if (cooldown3.cooldown(1500)){
                    if (rand == 1) {
                        castLongSpell(player, projList, color, "Enemy", camera);
                    } else {
                        castPoolSpell(player, projList, PoolColor, camera);
                    }
                }
            }
        }
    }
    public void move(Player player, Camera2D camera){
        if (getVector().distanceToOtherObject(player.getPosX(),player.getPosY()) > getRange() / 2 &&!isRandMoving()) {
            getVector().moveObject(player.getPosition(), "to", camera);
        }
        else{
            getVector().randEnemyMove(player,this,450, camera);
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