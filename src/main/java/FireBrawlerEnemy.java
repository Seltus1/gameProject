import com.raylib.Raylib;

public class FireBrawlerEnemy extends BrawlerEnemy{
    private Fire fire;

    public FireBrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, color);
        fire = new Fire();
    }

    public void attack(Player player){
        super.attack(player);
        if (calculateDistance(player) <= getRange()){
            fire.attack(player);
            fire.setInRange(true);
        }
        else{
            fire.setInRange(false);
        }
        fire.pushFireDamage(player);
    }
}