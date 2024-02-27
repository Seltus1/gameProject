package Attacks;
import Handlers.*;

import Handlers.VectorHandler;
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


    private Vector2 fixedPos;
    private Vector2 fixedFinalPos;
    private String shotTag;
    private CooldownHandler cooldown;

    private boolean hitPlayer;
    private boolean draw = true;

    private  Raylib.Color color;
    private int maxRange;
    private int distanceTravelled;

    private VectorHandler vector;
    private boolean circle;
    private VectorHandler player;
    private String toOrAway;

    public Projectile(int shotSpeed, int posX, int posY, int shotRad, int finalX, int finalY, String shotTag, int maxRange, boolean circle, Camera2D camera, Raylib.Color color) {
        this.shotSpeed = shotSpeed;
        this.shotRad = shotRad;
        isInBounds = true;
        vector = new VectorHandler(posX, posY, shotSpeed, camera);
        this.finalX = finalX;
        this.finalY = finalY;
        //Add this to constructor later when we have weapons
        damage = 70;
        this.color = color;
        this.shotTag = shotTag;
        this.maxRange = maxRange;
        this.circle = circle;
    }

    public Projectile(int shotSpeed, int posX, int posY, int shotRad, VectorHandler playerPOS, String shotTag, int maxRange, boolean circle, Camera2D camera, Raylib.Color color) {
        this.shotSpeed = shotSpeed;
        this.shotRad = shotRad;
        isInBounds = true;
        vector = new VectorHandler(posX, posY, shotSpeed, camera);
        player = playerPOS;
        //Add this to constructor later when we have weapons
        damage = 10;
        this.color = color;
        this.shotTag = shotTag;
        this.maxRange = maxRange;
        this.circle = circle;
    }

    public void updateMove(Camera2D camera){
        vector.updateShootLinePosition(camera);
        if(isDraw()) {
            DrawCircle(getPosX(), getPosY(), shotRad, color);
        }
    }

    public void createShotLine(Camera2D camera){
        setToOrAway("to");
        vector.setShotPosition(new Jaylib.Vector2(finalX, finalY));
        vector.setShootLineProjectiles(camera, this);
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

    public void triangleShot(String aboveOrBelow, Camera2D camera){
        createShotLine(camera);
        int[] newFinals = vector.TriangleShotVectorCalc(aboveOrBelow,finalX,finalY);
        finalX = newFinals[0];
        finalY = newFinals[1];
    }
//    ZENE ADD THIS LAATER

//    public void homingShot(double moveSpeed, Player player){
//        createShotLine();
//        vector.moveObject(player.getPosition(),"to",moveSpeed, ca);
//    }


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
    public void setMoveSpeed(int moveSpeed){
        vector.setMoveSpeed(moveSpeed);
        setShotSpeed(moveSpeed);
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

    public Raylib.Vector2 getPosition(){
        return vector.getPosition();
    }

    public VectorHandler getVector() {
        return vector;
    }

    public Raylib.Vector2 getTargetPosition() {
        return vector.getShotPosition();
    }

    public String getToOrAway() {
        return toOrAway;
    }

    public void setToOrAway(String toOrAway) {
        this.toOrAway = toOrAway;
    }

}