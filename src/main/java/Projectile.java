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
    private int cooldown;

    private boolean hitPlayer;
    private boolean draw = true;

    private  Raylib.Color color;
    private int maxRange;
    private int distanceTravelled;

    public Projectile(int shotSpeed, int posX, int posY, int shotRad, int finalX, int finalY, String shotTag, int maxRange, Raylib.Color color) {
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
        DrawCircle(this.posX, this.posY, shotRad, color);
        this.maxRange = maxRange;
        actualPosX = posX;
        actualPosY = posY;

    }

    public boolean isInBounds() {
        return isInBounds;
    }

    public void vectorCalc(){
        int enemyX = posX, enemyY = posY;
        int playerX = finalX, playerY = finalY;
//        making a y = mx + b function
        int y = playerY - enemyY;
        int x = playerX - enemyX;
        yMul = verticalCheck(y);
        xMul = horizontalCheck(x);
        double hypot = Math.sqrt(x*x + y*y);
        double yNormalized = (y / hypot);
        double xNormalized = (x / hypot);
        xMoveSpeed = xNormalized * shotSpeed;
        yMoveSpeed = yNormalized * shotSpeed;
        double updateX = getActualPosX() + xMoveSpeed;
        double updateY = getActualPosY() + yMoveSpeed;
        setActualPosX(updateX);
        setActualPosY(updateY);
        setPosX((int)(Math.round(updateX)));
        setPosY((int)(Math.round(updateY)));

    }

    public void doubleVectorCalc(String aboveOrBelow) {
        vectorCalc();
        double thing = xMoveSpeed;
        xMoveSpeed = yMoveSpeed;
        yMoveSpeed = thing;
        if (aboveOrBelow.equals("above")){
            finalX += (xMoveSpeed * -3);
            finalY += (yMoveSpeed * 3);
        }
        else{
            finalX -= (xMoveSpeed * -3);
            finalY -= (yMoveSpeed * 3);
        }
        vectorCalc();
    }

    private int horizontalCheck(int xValues){
        int left = 0;
        int right = 0;
        if (xValues < 0){
            left = 1;
        }
        if (xValues > 0){
            right = 1;
        }
        return right - left;
    }

    private int verticalCheck(int yValues){
        int up = 0;
        int down = 0;
        if (yValues < 0){
            up = 1;
        }
        if (yValues > 0){
            down = 1;
        }
        return down - up;
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
        else if( num == 0 && deno > 0){
            xMul = 1;
            yMul = 0;
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
