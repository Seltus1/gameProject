import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Projectile {
    private int shotSpeed;
    private int shotRad;
    private int posX;
    private int posY;

    private double actualPosX;
    private double actualPosY;
    private boolean isInBounds;
    private int angleOfMovement;
    private int damage;
    private int finalX;
    private int finalY;
    private String shotTag;
    private double xMoveSpeed;
    private double yMoveSpeed;

    private int xMul;
    private int yMul;
    private int slopeMul;

    public Projectile(int shotSpeed, int posX, int posY, int shotRad, int angleOfMovement) {
        this.shotSpeed = shotSpeed;
        this.posX = posX;
        this.posY = posY;
        this.shotRad = shotRad;
        isInBounds = true;
        this.angleOfMovement = angleOfMovement;
        shotTag = null;
        //Add this to constructor later when we have weapons
        damage = 10;
        DrawCircle(this.posX, this.posY, shotRad, BLACK);
    }

    public Projectile(int shotSpeed, int posX, int posY, int shotRad, int finalX, int finalY) {
        this.shotSpeed = shotSpeed;
        this.posX = posX;
        this.posY = posY;
        this.shotRad = shotRad;
        isInBounds = true;
        this.finalX = finalX;
        this.finalY = finalY;
        //Add this to constructor later when we have weapons
        damage = 10;
        DrawCircle(this.posX, this.posY, shotRad, BLACK);
    }

    public boolean isInBounds() {
        return isInBounds;
    }
    public void move(){
        if(angleOfMovement == 45 || angleOfMovement == 90 || angleOfMovement == 135 ){
            posX += shotSpeed;
        }
        if (angleOfMovement == 315 || angleOfMovement == 270 || angleOfMovement == 225) {
            posX -=shotSpeed;
        }
        if(angleOfMovement == 315 || angleOfMovement == 0 || angleOfMovement == 45){
            posY -=shotSpeed;
        }
        if(angleOfMovement == 225 || angleOfMovement == 180 || angleOfMovement == 135){
            posY +=shotSpeed;
        }
        update();
    }
    public void vectorCalculations(){
        double enemyX = posX, enemyY = posY;
        double playerX = finalX, playerY = finalY;
//        making a y = mx + b function
        double y = playerY - enemyY;
        double x = playerX - enemyX;
        quadrentCheck(y, x);
        double slope = (y / x) * slopeMul;
        double step1 = slope * enemyX; // m * x
        double b = enemyY + (step1 * -1); // brining the step one to the y
        double totalDistance = (Math.abs(playerX - enemyX) + Math.abs(playerY - enemyY));
        double xPct = Math.abs(playerX - enemyX) / totalDistance;
        double yPct = 1 - xPct;
        xMoveSpeed = (xPct * shotSpeed) * xMul;
        yMoveSpeed = (yPct * shotSpeed) * yMul;
        actualPosX = enemyX;
        actualPosY = enemyY;
    }

    private void quadrentCheck(double num, double deno){
        if (num < 0 && deno < 0){
            xMul = -1;
            yMul = -1;
            slopeMul = -1;
        }
        else if (num < 0 && deno > 0){
            xMul = 1;
            yMul = -1;
            slopeMul = 1;
        }
        else if (num > 0 && deno > 0) {
            xMul = 1;
            yMul = 1;
            slopeMul = -1;
        }
        else{
            xMul = -1;
            yMul = 1;
            slopeMul = 1;
        }
    }


    public void updateMove(){
        this.actualPosY += yMoveSpeed;
        this.posY = (int) actualPosY;
        this.actualPosX += xMoveSpeed;
        this.posX = (int) actualPosX;
        update();
    }

    private void shotError(double ShotError){

    }
    public void boundsCheck(){
        if(posX < 0 || posX > GetScreenWidth()){
            isInBounds = false;
        }
        if(posY < 0 || posY > GetScreenHeight()){
            isInBounds = false;
        }
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getShotRad() {
        return shotRad;
    }

    public int getDamage(){
        return damage;
    }

    public void update(){
        DrawCircle(posX, posY, shotRad, BLACK);
    }

    public double getXMoveSpeed() {return xMoveSpeed;}
    public double getYMoveSpeed() {return yMoveSpeed;}

    public void setShotTage(String tag){
        shotTag = tag;
    }

    public String getShotTag(){
        return shotTag;
    }

}
