import com.raylib.Raylib;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class MagicEnemy extends Enemy{
    private int spellCoolDown;
    private int shotSpeed;
    private int spellRange;
    private float drawRadious;
    public MagicEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int spellRange, int shotSpeed, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, spellRange, color);
        setActualYPos(posY);
        setActualXPos(posX);
        this.shotSpeed = shotSpeed;
        this.spellRange = spellRange;
    }

    public void castSpell(Player player, ProjectileHandler projList, Raylib.Color color){
        if (spellRange > calculateDistanceToPlayer(player)){
            spellCoolDown++;
            DrawCircle(getPosX(), getPosY() - 50, (float) (spellCoolDown / 7.5), color);
            if ((spellCoolDown + 1) % 91 == 0){
                Projectile spell = new Projectile(shotSpeed, getPosX(), getPosY(), 12, player.getPosX(), player.getPosY(), "Enemy", color);
                spell.vectorCalculations();
                projList.add(spell);
                spellCoolDown = 0;
            }
        }
    }


}
