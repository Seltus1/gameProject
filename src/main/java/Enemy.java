import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;

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

    public void gotDamagedRanged(ProjectileHandler projList) {
        for (int i = 0; i < projList.size(); i++) {
            // Get the current projectile from the ProjectileHandler at index 'i'
            Projectile currProj = (Projectile) projList.get(i);

            // Get the current projectile's position as a Vector2
            Jaylib.Vector2 currPos = new Jaylib.Vector2(currProj.getPosX(), currProj.getPosY());

            // Check for collision between circles: enemy position and size, and projectile position and shot radius
            if (CheckCollisionCircles(pos, size, currPos, currProj.getShotRad())) {
                projList.removeIndex(i);
                // Reduce enemy HP by the damage from the current projectile
                hp -= currProj.getDamage();
                System.out.println(hp);

                // Check if enemy HP is less than or equal to 0
                if (hp <= 0) {
                    // If HP is zero or less, mark the enemy as not alive
                    isAlive = false;
                }
            }
        }
    }
}
