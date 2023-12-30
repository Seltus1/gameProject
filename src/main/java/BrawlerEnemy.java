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


    public BrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, color);
        setRange(100);
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
        quadrentCheck(verticalValues, horizontalValues);
        double updateX, updateY;
        if (verticalValues != 0 && horizontalValues != 0){
            pythagCheck(verticalValues, horizontalValues);
            updateX = myXPos + getMoveSpeed() * xPct;
            updateY = myYPos + getMoveSpeed() * yPct;

        }
        else {
            updateX = (myXPos + getMoveSpeed()) * xMul;
            updateY = (myYPos + getMoveSpeed()) * yMul;
        }
        setActualXPos(updateX);
        setActualYPos(updateY);
        setPosX((int) Math.round(updateX));
        setPosY((int) Math.round(updateY));
        DrawCircle(getPosX(), getPosY(), getSize(), getColor());
    }

    private void pythagCheck(double verticalValues, double horizontalValues){
        double hypot = Math.sqrt(Math.pow(horizontalValues, 2) + Math.pow(verticalValues, 2));
        double multipler = 1 / hypot;
        yPct = verticalValues * multipler;
        xPct = horizontalValues * multipler;
    }

    private void quadrentCheck(double yValues, double xValues){
        if (yValues < 0 && xValues < 0){
            xMul = -1;
            yMul = -1;
        }
        else if (yValues < 0 && xValues > 0){
            xMul = 1;
            yMul = -1;
        }
        else if (yValues > 0 && xValues > 0) {
            xMul = 1;
            yMul = 1;
        }
        else if (yValues == 0 && xValues > 0){
            xMul = 1;
            yMul = 0;
        }
        else if (yValues == 0 && xValues < 0){
            xMul = -1;
            yMul = 0;
        }
        else if (xValues == 0 && yValues > 0){
            yMul = 1;
            xMul = 0;
        }
        else if (xValues == 0 && yValues < 0){
            yMul = -1;
            xMul = 0;
        }
        else {
            xMul = -1;
            yMul = 1;
        }
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
