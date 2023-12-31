import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;

import com.raylib.Raylib;

public class BrawlerEnemy extends Enemy{

    private int xMul;
    private int yMul;
    private double actualXPos;
    private double actualYPos;
    private MeleeAttack melee;
    private double xPct;
    private double yPct;


    public BrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, range, color);
        setActualXPos(getPosX());
        setActualYPos(getPosY());
        melee = new MeleeAttack(getDamage(),getPosX(),getPosY());
    }

    public void followPlayer(Player player){
        double playerXPos = player.getPosX();
        double playerYPos = player.getPosY();
        double myXPos = getActualXPos();
        double myYPos = getActualYPos();

        double verticalValues = playerYPos - myYPos;
        double horizontalValues = playerXPos - myXPos;
        //Good ol' vector math
        double magnitude = Math.sqrt(Math.pow(verticalValues,2) + Math.pow(horizontalValues,2));
        double yNormalized = verticalValues/magnitude;
        double xNormalized = horizontalValues/magnitude;
        //Apply moveSpeed to scale it
        double xScaled = xNormalized*getMoveSpeed();
        double yScaled = yNormalized*getMoveSpeed();
        double updateX, updateY;

        updateX = actualXPos += xScaled;
        updateY = actualYPos += yScaled;
        setActualXPos(updateX);
        setActualYPos(updateY);
        setPosX((int) Math.round(updateX));
        setPosY((int) Math.round(updateY));
        DrawCircle(getPosX(), getPosY(), getSize(), getColor());
    }

    public int calculateDistance(Player player){
        int y = player.getPosY() - getPosY();
        int x = player.getPosX() - getPosX();
        int totalDist = Math.abs(x) + Math.abs(y);
        return totalDist;
    }
    public void attack(Player player){
        int distance = calculateDistance(player);
        if(distance <= getRange()){
            melee.attack(player, 31);
        }
    }

    public double getActualXPos() {
        return actualXPos;
    }

    public void setActualXPos(double actualXPos) {
        this.actualXPos = actualXPos;
    }

    public double getActualYPos() {
        return actualYPos;
    }

    public void setActualYPos(double actualYPos) {
        this.actualYPos = actualYPos;
    }
}
