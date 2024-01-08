import com.raylib.Raylib;

public class FireMagicEnemy extends MagicEnemy{
    private Fire fire;
    private int longCooldown;

    public FireMagicEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int moveRange, int spellRange, int shotSpeed, Raylib.Color color) {
        super(hp, damage, posX, posY, moveSpeed, size, moveRange, spellRange, shotSpeed, color);
        fire = new Fire();
    }

    public void castInferno(Player player, ProjectileHandler projList, Raylib.Color color) {
        super.castLongSpell(player,projList,color, "Inferno_Enemy");
    }



    public void castCloseSpell(Player player, ProjectileHandler projList, Raylib.Color color) {

    }

    public void castPoolSpell(Player player, ProjectileHandler projList, Raylib.Color color) {
    }
    public void update(Player player, ProjectileHandler projList, Raylib.Color color) {
        longCooldown++;
        if ((longCooldown + 1) % 91 == 0) {
            castInferno(player, projList, color);
            longCooldown = 0;
        }
    }
}
