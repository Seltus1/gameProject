import static com.raylib.Raylib.*;

import com.raylib.Raylib;

public class MeleeEnemy extends Enemy{

    private int xMul;
    private int yMul;



    public MeleeEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, Raylib.Color color){
        super(hp, damage, posX, posY, moveSpeed, size, color);
        setRange(5);
    }

    public void followPlayer(Player player){
        double playerXPos = player.getPosX();
        double playerYPos = player.getPosY();
        double myXPos = getPosX();
        double myYPos = getPosY();
        double y = playerYPos - myYPos;
        double x = playerXPos - myXPos;
        quadrentCheck(y, x);
        double totalDistance = (Math.abs(playerXPos - myXPos) + Math.abs(playerYPos - myYPos));
        double xPct = Math.abs(playerXPos - myXPos) / totalDistance;

        double yPct = 1 - xPct;
        double xMoveSpeed = (xPct * getMoveSpeed()) * xMul;
        double yMoveSpeed = (yPct * getMoveSpeed()) * yMul;
        double updateX = myXPos + xMoveSpeed;
        double updateY = myYPos + yMoveSpeed;
        setPosX( (int) updateX);
        setPosY( (int) updateY);
        DrawCircle(getPosX(), getPosY(), getSize(), getColor());
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
        else{
            xMul = -1;
            yMul = 1;
        }
    }
    private int calculateDistance(Player player){
        int y = player.getPosY() - getPosY();
        int x = player.getPosX() - getPosX();
        int totalDist = Math.abs(x) + Math.abs(y);
        return totalDist;
    }
    public void attack(Player player){
        int distance = calculateDistance(player);
        if(distance <= getRange()){
            MeleeAttack melee = new MeleeAttack(getDamage(),getPosX(),getPosY());
            melee.attack(player);
        }
    }
}
