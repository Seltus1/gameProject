import com.raylib.Raylib;

public class FireBrawlerEnemy extends BrawlerEnemy{
    private Fire fire;

    public FireBrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, color);
        fire = new Fire();
    }

    public void attack(Player player){
        fire.setBurnTime(10);
        super.attack(player);

        if (calculateDistance(player) <= getRange()){
            fire.attack(player);
            fire.setInRange(true);
//            fire.setBurnCountdown(10);
        }
        else{
            fire.setInRange(false);
        }
        fire.pushFireDamage(player);
    }

}
