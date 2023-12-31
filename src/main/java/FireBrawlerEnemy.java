import com.raylib.Raylib;

public class FireBrawlerEnemy extends BrawlerEnemy{
    private Fire fire;
    private int burnCountDown;

    public FireBrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, color);
        fire = new Fire();
    }

    public void attack(Player player){
        fire.setBurnTime(10);
        player.setBurnDamage(fire.getBurnDamage());
        super.attack(player);
        if (calculateDistance(player) <= getRange()){
            fire.attack(player);
            player.setFireInRange(true);
            player.setOnFire(true);
            player.setIntialBurn(fire.getBurnTime());
        }
    }
}
