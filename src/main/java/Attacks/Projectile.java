package Attacks;
import Handlers.*;

import Handlers.Vector2D;
import com.raylib.Jaylib;
import com.raylib.Raylib;

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
    private CooldownHandler cooldown;

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
        this.maxRange = maxRange;
        this.circle = circle;
    }

    public void updateMove(){
        vector.updateShootLinePosition();
        if(isDraw()) {
            DrawCircle(getPosX(), getPosY(), shotRad, color);
        }
    }

    public void shootLine(){
        Jaylib.Vector2 shotPosition = new Jaylib.Vector2(finalX, finalY);
        vector.setShotPosition(shotPosition);
        vector.setShootLine();
    }

    public void checkProjIsOnScreen(){
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

    public void triangleShot(String aboveOrBelow){
        shootLine();
        int[] newFinals = vector.TriangleShotVectorCalc(aboveOrBelow,finalX,finalY);
        finalX = newFinals[0];
        finalY = newFinals[1];
    }
//    ZENE ADD THIS LAATER

//    public void homingShot(double moveSpeed, Player player){

//        vector.moveObject(player.getPosition(),"to",moveSpeed);

//    }

    public void explodePoolSpell(){
        shotRad = 50;
        setxMoveSpeed(0);
        setyMoveSpeed(0);
    }

    public boolean isInBounds() {
        return isInBounds;
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