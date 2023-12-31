import com.raylib.Raylib;

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

    private  Raylib.Color color;

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

    public Projectile(int shotSpeed, int posX, int posY, int shotRad, int finalX, int finalY, Raylib.Color color) {
        this.shotSpeed = shotSpeed;
        this.posX = posX;
        this.posY = posY;
        this.shotRad = shotRad;
        isInBounds = true;
        this.finalX = finalX;
        this.finalY = finalY;
        //Add this to constructor later when we have weapons
        damage = 10;
        this.color = color;
        DrawCircle(this.posX, this.posY, shotRad, color);
    }

    public boolean isInBounds() {
        return isInBounds;
    }

    public void vectorCalculations(){
        double enemyX = posX, enemyY = posY;
        double playerX = finalX, playerY = finalY;
//        making a y = mx + b function
        double y = playerY - enemyY;
        double x = playerX - enemyX;
        quadrentCheck(y, x);
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
        this.posY = (int) Math.round(actualPosY);
        this.actualPosX += xMoveSpeed;
        this.posX = (int) Math.round(actualPosX);
        update();
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
        DrawCircle(posX, posY, shotRad, color);
    }

    public double getXMoveSpeed() {return xMoveSpeed;}
    public double getYMoveSpeed() {return yMoveSpeed;}

    public void setShotTag(String tag){
        shotTag = tag;
    }

    public String getShotTag(){
        return shotTag;
    }

    public int getShotSpeed() {
        return shotSpeed;
    }

    public void setShotSpeed(int shotSpeed) {
        this.shotSpeed = shotSpeed;
    }

    public void setShotRad(int shotRad) {
        this.shotRad = shotRad;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public double getActualPosX() {
        return actualPosX;
    }

    public void setActualPosX(double actualPosX) {
        this.actualPosX = actualPosX;
    }

    public double getActualPosY() {
        return actualPosY;
    }

    public void setActualPosY(double actualPosY) {
        this.actualPosY = actualPosY;
    }

    public void setInBounds(boolean inBounds) {
        isInBounds = inBounds;
    }

    public int getAngleOfMovement() {
        return angleOfMovement;
    }

    public void setAngleOfMovement(int angleOfMovement) {
        this.angleOfMovement = angleOfMovement;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getFinalX() {
        return finalX;
    }

    public void setFinalX(int finalX) {
        this.finalX = finalX;
    }

    public int getFinalY() {
        return finalY;
    }

    public void setFinalY(int finalY) {
        this.finalY = finalY;
    }

    public double getxMoveSpeed() {
        return xMoveSpeed;
    }

    public void setxMoveSpeed(double xMoveSpeed) {
        this.xMoveSpeed = xMoveSpeed;
    }

    public double getyMoveSpeed() {
        return yMoveSpeed;
    }

    public void setyMoveSpeed(double yMoveSpeed) {
        this.yMoveSpeed = yMoveSpeed;
    }

    public int getxMul() {
        return xMul;
    }

    public void setxMul(int xMul) {
        this.xMul = xMul;
    }

    public int getyMul() {
        return yMul;
    }

    public void setyMul(int yMul) {
        this.yMul = yMul;
    }

    public int getSlopeMul() {
        return slopeMul;
    }

    public void setSlopeMul(int slopeMul) {
        this.slopeMul = slopeMul;
    }

    public Raylib.Color getColor() {
        return color;
    }

    public void setColor(Raylib.Color color) {
        this.color = color;
    }
}
