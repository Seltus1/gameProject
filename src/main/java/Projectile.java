import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Projectile {
    private int shotSpeed;
    private int shotRad;
    private int posX;
    private int posY;
    private boolean isInBounds;
    private int angleOfMovement;
    private int damage;

    public Projectile(int shotSpeed, int posX, int posY, int shotRad, int angleOfMovement) {
        this.shotSpeed = shotSpeed;
        this.posX = posX;
        this.posY = posY;
        this.shotRad = shotRad;
        isInBounds = true;
        this.angleOfMovement = angleOfMovement;
        //Add this to constructor later when we have weapons
        damage = 10;
        DrawCircle(this.posX, this.posY, shotRad, BLACK);
    }
//TEST
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
}
