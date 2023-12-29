import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class MovableEnemy extends Enemy{

    private int xMul;
    private int yMul;


    public MovableEnemy(int hp, int damage, int range, int posX, int posY, int moveSpeed, int size, Raylib.Color color){
        super(hp, damage, range, posX, posY, moveSpeed, size, color);
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
        int moveSpeed = getMoveSpeed();
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
}
