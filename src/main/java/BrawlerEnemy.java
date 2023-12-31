import static com.raylib.Raylib.*;

import com.raylib.Raylib;

public class BrawlerEnemy extends Enemy{

    private int xMul;
    private int yMul;
    private MeleeAttack melee;
    private double xPct;
    private double yPct;


    public BrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, range, color);
        melee = new MeleeAttack(getDamage(),getPosX(),getPosY());
    }

    public void attack(Player player){
        int distance = calculateDistanceToPlayer(player);
        if(distance <= getRange()){
            melee.attack(player, 31);
        }
    }
}
