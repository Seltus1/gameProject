import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Enemy extends Creature{
    private boolean isAlive = true;
    public Enemy(int hp, int posX, int posY, int size){
        super(hp,posX,posY,size);
        DrawCircle(posX,posY,size,PURPLE);

    }
    private Jaylib.Vector2 pos = new Jaylib.Vector2(posX,posY);
    @Override
    public int getHp() {
        return super.getHp();
    }

    @Override
    public int getPosX() {
        return super.getPosX();
    }

    @Override
    public int getPosY() {
        return super.getPosY();
    }

    @Override
    public int getSize() {
        return super.getSize();
    }

    public boolean getIsAlive(){
        return isAlive;
    }

    public void gotDamagedRanged(){
        for (int i = 0; i < Player.getProjList().size(); i++) {
            Projectile currProj = Player.getProjList().get(i);
            Jaylib.Vector2 currPos = new Jaylib.Vector2(currProj.getPosX(),currProj.getPosY());
            if (CheckCollisionCircles(pos,size,currPos,currProj.getShotRad())){
                hp -= currProj.getDamage();
                if (hp <= 0){
                    isAlive = false;
                }
            }
        }
    }
//    TEST
    public void update(){
        if (isAlive){
            gotDamagedRanged();
            DrawCircle(posX,posY,size,PURPLE);
        }
    }
}
