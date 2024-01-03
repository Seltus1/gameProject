import com.raylib.Raylib;

import java.util.Random;

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
        setActualYPos(posY);
        setActualXPos(posX);
        this.shotSpeed = shotSpeed;
        this.spellRange = spellRange;
        random = new Random();
        PoolColor = ColorFromHSV(278,.93f,.26f);
    }

    public void castLongSpell(Player player, ProjectileHandler projList, Raylib.Color color) {
        Projectile spell = new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getPosX(), player.getPosY(), "Enemy", spellRange, color);
        spell.shootLine();
        projList.add(spell);
    }



    public void castCloseSpell(Player player, ProjectileHandler projList, Raylib.Color color) {
        Projectile closeSpell1 = new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getPosX(), player.getPosY(), "Enemy", spellRange, color);
        Projectile closeSpell2= new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getPosX(), player.getPosY(), "Enemy", spellRange, color);
        closeSpell1.doubleVectorCalc("above");
        closeSpell2.doubleVectorCalc("below");
        projList.add(closeSpell1);
        projList.add(closeSpell2);
    }

    public void castPoolSpell(Player player, ProjectileHandler projList, Raylib.Color color) {
        Projectile poolShot = new Projectile(shotSpeed, getPosX(), getPosY(), 5, player.getPosX(),player.getPosY(), "Enemy_Pool", spellRange, color);
        poolShot.shootLine();
        projList.add(poolShot);
    }

    public void update(Player player, ProjectileHandler projList, Raylib.Color color) {
        if (getRange() > calculateDistanceToPlayer(player)) {
            spellCoolDown++;
            DrawCircle(getPosX(), getPosY() - 50, (float) (spellCoolDown / 7.5), color);
            int rand = random.nextInt(2) + 1;
            if (calculateDistanceToPlayer(player) > getRange() / 1.5) {
                if ((spellCoolDown + 1) % 91 == 0){
                    if(rand == 1) {
                        castLongSpell(player, projList, color);
                    }
                    else {
                        castPoolSpell(player, projList, PoolColor);
                    }
                    spellCoolDown = 0;
                }
                followPlayer(player, "to");
            }
            else if (calculateDistanceToPlayer(player) < getRange() / 2) {
                if ((spellCoolDown + 1) % 91 == 0){
                    castCloseSpell(player, projList, color);
                    spellCoolDown = 0;
                }
                followPlayer(player, "away");
            }
            else {
                if ((spellCoolDown + 1) % 91 == 0){
                    if(rand == 1) {
                        castLongSpell(player, projList, color);
                    }
                    else {
                        castPoolSpell(player, projList, PoolColor);
                    }
                    spellCoolDown = 0;
                }
            }

        }
        else{
            followPlayer(player, "to");
        }
    }
}