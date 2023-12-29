import com.raylib.Raylib;

public class FireMeleeEnemy extends MeleeEnemy{
    private Fire fire;

    public FireMeleeEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, color);
        fire = new Fire();
    }

    public void attack(Player player){
        fire.setBurnTime(2000);
        super.attack(player);
        fire.attack(player);
    }

}
