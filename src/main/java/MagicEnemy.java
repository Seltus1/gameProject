import com.raylib.Raylib;

import java.util.Random;
import java.util.Vector;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;

public class MagicEnemy extends Enemy {
    private int spellCoolDown;
    private int shotSpeed;
    private float drawRadious;
    private int spellRange;
    private Raylib.Color PoolColor;
    Random random;

    public MagicEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int moveRange, int spellRange, int shotSpeed, Raylib.Color color) {
        super(hp, damage, posX, posY, moveSpeed, size, moveRange, color);
        this.shotSpeed = shotSpeed;
        this.spellRange = spellRange;
        random = new Random();
        PoolColor = ColorFromHSV(278,.93f,.26f);
    }

    public void castLongSpell(Player player, ProjectileHandler projList, Raylib.Color color, String tag) {
        Projectile spell = new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getPosX(), player.getPosY(), tag, spellRange, true, color);
        spell.shootInLine();
        projList.add(spell);
    }



    public void castCloseSpell(Player player, ProjectileHandler projList, Raylib.Color color) {
        Projectile closeSpell1 = new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getPosX(), player.getPosY(), "Enemy", spellRange, true, color);
        Projectile closeSpell2= new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getPosX(), player.getPosY(), "Enemy", spellRange, true, color);
        closeSpell1.doubleVectorCalc("above");
        closeSpell2.doubleVectorCalc("below");
        projList.add(closeSpell1);
        projList.add(closeSpell2);
    }

    public void castPoolSpell(Player player, ProjectileHandler projList, Raylib.Color color) {
        Projectile poolShot = new Projectile(shotSpeed, getPosX(), getPosY(), 5, player.getPosX(),player.getPosY(), "Enemy_Pool", spellRange, true, color);
        poolShot.shootInLine();
        projList.add(poolShot);
    }

    public void shoot(Player player, ProjectileHandler projList, Raylib.Color color){
        int rand = random.nextInt(2) + 1;
        if (getRange() > calculateDistanceToPlayer(player)) {
            spellCoolDown++;
            if (calculateDistanceToPlayer(player) > getRange() / 1.5) {
                if ((spellCoolDown + 1) % 91 == 0) {
                    if (rand == 1) {
                        castLongSpell(player, projList, color, "Enemy");
                    } else {
                        castPoolSpell(player, projList, PoolColor);
                    }
                    spellCoolDown = 0;
                }
            } else if (calculateDistanceToPlayer(player) < getRange() / 2) {
                if ((spellCoolDown + 1) % 91 == 0) {
                    castCloseSpell(player, projList, color);
                    spellCoolDown = 0;
                }
            } else {
                if ((spellCoolDown + 1) % 91 == 0) {
                    if (rand == 1) {
                        castLongSpell(player, projList, color, "Enemy");
                    } else {
                        castPoolSpell(player, projList, PoolColor);
                    }
                    spellCoolDown = 0;
                }
            }
        }
    }
    public void move(Player player){
        if (calculateDistanceToPlayer(player) > getRange() / 1.5) {
            followPlayer(player, "to");
        }
        else if (calculateDistanceToPlayer(player) < getRange() / 2) {
            followPlayer(player, "away");
        }
    }

    public void drawHat(){
        Vector2D v1 = new Vector2D(getPosX(), getPosY() - 50, getMoveSpeed());
        Vector2D v2 = new Vector2D(getPosX() - 20, getPosY() - 30, getMoveSpeed());
        Vector2D v3 = new Vector2D(getPosX() + 20, getPosY() - 30, getMoveSpeed());
        DrawTriangle(v1.getPosition(),v2.getPosition(),v3.getPosition(),BLACK);
    }
    public void update(Player player, ProjectileHandler projList, Raylib.Color color) {
        DrawCircle(getPosX(), getPosY() - 50, (float) (spellCoolDown / 7.5), color);
        shoot(player,projList, color);
        move(player);
        drawHat();
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