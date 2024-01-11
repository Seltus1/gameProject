package Attacks;
import Handlers.*;
import Creatures.*;
import Elements.*;

import Handlers.Vector2D;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;

public class Projectile {
    private int shotSpeed;
    private int shotRad;
    private boolean isInBounds;
    private int angleOfMovement;
    private int damage;
    private int finalX;
    private int finalY;
    private String shotTag;
    private int cooldown;

    private boolean hitPlayer;
    private boolean draw = true;

    private  Raylib.Color color;
    private int maxRange;
    private int distanceTravelled;

    private Vector2D vector;
    private boolean circle;

    private int wallXpoint1;
    private int wallYPoint1;
    private int wallXPoint2;
    private int wallYPoint2;

    public Projectile(int shotSpeed, int posX, int posY, int shotRad, int finalX, int finalY, String shotTag, int maxRange, boolean circle, Raylib.Color color) {
        this.shotSpeed = shotSpeed;
        this.shotRad = shotRad;
        isInBounds = true;
        vector = new Vector2D(posX, posY, shotSpeed);
        this.finalX = finalX;
        this.finalY = finalY;
        //Add this to constructor later when we have weapons
        damage = 10;
        this.color = color;
        this.shotTag = shotTag;
        if (circle){
            DrawCircle(getPosX(), getPosY(), shotRad, color);
        }
        else{
            DrawRectangle(posX, posY, 20, 60, color);
        }
        this.maxRange = maxRange;
        this.circle = circle;
    }

    public boolean isInBounds() {
        return isInBounds;
    }

    public void shootLine(){
        Jaylib.Vector2 shotPosition = new Jaylib.Vector2(finalX, finalY);
        vector.setShotPosition(shotPosition);
        vector.setShootLine();
    }

    public void doubleVectorCalc(String aboveOrBelow) {
        shootLine();
        double swap = vector.getxNormalizedMovement();
        vector.setxNormalizedMovement(vector.getyNormalizedMovement());
        vector.setyNormalizedMovement(swap);
        if (aboveOrBelow.equals("above")){
            finalX += (vector.getxNormalizedMovement() * -3);
            finalY += (vector.getyNormalizedMovement() * 3);
        }
        else{
            finalX -= (vector.getxNormalizedMovement() * -3);
            finalY -= (vector.getyNormalizedMovement() * 3);
        }
        shootLine();
    }

    public double[] updateDoubleVectorPosition() {
        shootLine();
        double[] array = new double[4];
        double swap = vector.getxNormalizedMovement();
        vector.setxNormalizedMovement(vector.getyNormalizedMovement());
        vector.setyNormalizedMovement(swap);
        array[0] = vector.getActualXPos() - (vector.getxNormalizedMovement() * -12);
        array[1] = vector.getActualYPos() - (vector.getyNormalizedMovement() * 12);
        array[2] = vector.getActualXPos() + (vector.getxNormalizedMovement() * -12);
        array[3] = vector.getActualYPos() + (vector.getyNormalizedMovement() * 12);
        wallXpoint1 = (int) array[0];
        wallYPoint1 = (int) array[1];
        wallXPoint2 = (int) array[2];
        wallYPoint2 = (int) array[3];
        return array;
    }

    public void drawWall() {
        if (distanceTravelled <= maxRange) {
            double[] array = updateDoubleVectorPosition();
            DrawLine((int) array[0], (int) array[1], (int) array[2],(int) array[3], BLACK);
        }
        else{
            DrawLine(wallXpoint1, wallYPoint1, wallXPoint2, wallYPoint2, BLACK);
        }
    }

    public void updateMove(){
        vector.updateShootLinePosition();
        DrawCircle(getPosX(), getPosY(), shotRad, color);
//        this.actualYPos += yMoveSpeed;
//        this.posY = (int) Math.round(actualYPos);
//        this.actualXPos += xMoveSpeed;
//        this.posX = (int) Math.round(actualXPos);
//        update();
    }
    public void boundsCheck(){
        if(getPosX() < 0 || getPosX() > GetScreenWidth()){
            isInBounds = false;
        }
        if(getPosY() < 0 || getPosY() > GetScreenHeight()){
            isInBounds = false;
        }
    }

    public boolean pastMaxDistanceTravelled(){
        return distanceTravelled >= maxRange;
    }

    public void explodePoolSpell(){
        shotRad = 50;
        cooldown++;
        if((cooldown + 1) % 61 == 0){
            draw = false;
            cooldown = 0;
        }
    }
    public int getPosX() {
        return vector.getPosX();
    }

    public int getPosY() {
        return vector.getPosY();
    }

    public int getShotRad() {
        return shotRad;
    }

    public int getDamage(){
        return damage;
    }
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
        vector.setPosX(posX);
    }

    public void setPosY(int posY) {
        vector.setPosY(posY);
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


    public void setxMoveSpeed(double xMoveSpeed) {
        vector.setxNormalizedMovement(xMoveSpeed);
    }
    public void setyMoveSpeed(double yMoveSpeed) {
        vector.setyNormalizedMovement(yMoveSpeed);
    }
    public Raylib.Color getColor() {
        return color;
    }

    public void setColor(Raylib.Color color) {
        this.color = color;
    }

    public boolean isHitPlayer() {
        return hitPlayer;
    }

    public void setHitPlayer(boolean hitPlayer) {
        this.hitPlayer = hitPlayer;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(int distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public Jaylib.Vector2 getPosition(){
        return vector.getPosition();
    }

    public Vector2D getVector() {
        return vector;
    }
}
