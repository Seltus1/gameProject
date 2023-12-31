import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;

public class Projectile {
    private int shotSpeed;
    private int shotRad;
    private int posX;
    private int posY;

    private double actualXPos;
    private double actualYPos;
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
        this.posX = posX;
        this.posY = posY;
        this.shotRad = shotRad;
        isInBounds = true;
        this.finalX = finalX;
        this.finalY = finalY;
        //Add this to constructor later when we have weapons
        damage = 10;
        this.color = color;
        this.shotTag = shotTag;
        if (circle){
            DrawCircle(this.posX, this.posY, shotRad, color);
        }
        else{
            DrawRectangle(posX, posY, 20, 60, color);
        }
        this.maxRange = maxRange;
        actualXPos = posX;
        actualYPos = posY;
        vector = new Vector2D(posX, posY, shotSpeed);
        this.circle = circle;
    }

    public boolean isInBounds() {
        return isInBounds;
    }

    public void shootInLine(){
        Jaylib.Vector2 position = new Jaylib.Vector2(finalX, finalY);
        vector.moveObject(position, "to");
        updateObjectPositions();
    }

    private void updateObjectPositions() {
        actualXPos = vector.getActualXPos();
        actualYPos = vector.getActualYPos();
        posX = vector.getPosX();
        posY = vector.getPosY();
        yMoveSpeed = vector.getyNormalizedMovement();
        xMoveSpeed = vector.getxNormalizedMovement();
    }

    public void doubleVectorCalc(String aboveOrBelow) {
        shootInLine();
        double swap = xMoveSpeed;
        vector.setxNormalizedMovement(vector.getyNormalizedMovement());
        vector.setyNormalizedMovement(swap);
        if (aboveOrBelow.equals("above")){
            finalX += (xMoveSpeed * -3);
            finalY += (yMoveSpeed * 3);
        }
        else{
            finalX -= (xMoveSpeed * -3);
            finalY -= (yMoveSpeed * 3);
        }
        shootInLine();
    }

    public double[] updateDoubleVectorPosition() {
        shootInLine();
        double[] array = new double[4];
        double swap = xMoveSpeed;
        vector.setxNormalizedMovement(vector.getyNormalizedMovement());
        vector.setyNormalizedMovement(swap);
        array[0] = actualXPos - (vector.getxNormalizedMovement() * -12);
        array[1] = actualYPos - (vector.getyNormalizedMovement() * 12);
        array[2] = actualXPos + (vector.getxNormalizedMovement() * -12);
        array[3] = actualYPos + (vector.getyNormalizedMovement() * 12);
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
        this.actualYPos += yMoveSpeed;
        this.posY = (int) Math.round(actualYPos);
        this.actualXPos += xMoveSpeed;
        this.posX = (int) Math.round(actualXPos);
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

    public boolean distanceTravelled(){
        if (distanceTravelled >= maxRange){
            return true;
        }
        return false;
    }

    public void explodePoolSpell(){
        shotRad = 50;
        cooldown++;
        if((cooldown + 1) % 61 == 0){
            draw = false;
            cooldown = 0;
        }
    }
    public void update(){
        DrawCircle(posX, posY, shotRad, color);
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
        vector.setxNormalizedMovement(xMoveSpeed);
    }

    public double getyMoveSpeed() {
        return yMoveSpeed;
    }

    public void setyMoveSpeed(double yMoveSpeed) {
        vector.setyNormalizedMovement(yMoveSpeed);
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
}
