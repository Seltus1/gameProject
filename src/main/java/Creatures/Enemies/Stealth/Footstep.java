package Creatures.Enemies.Stealth;

import com.raylib.Raylib;

import java.util.Random;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;
public class Footstep {
    private int posX;
    private int posY;
    private int lifeTime;
    private long timeFirstDrawn;
    private Random rand;
    public Footstep(int posX, int posY, int lifeTime){
        this.posX = posX;
        this.posY = posY;
        this.lifeTime = lifeTime;
        timeFirstDrawn = System.currentTimeMillis();
        rand = new Random();
    }

    public void draw(){
//        DrawText(""+ (System.currentTimeMillis() - timeFirstDrawn), 200,200,20,BLACK);
        if(System.currentTimeMillis() - timeFirstDrawn <= lifeTime){
            DrawEllipse(posX,posY,3,8,BLACK);
        }
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public long getTimeFirstDrawn() {
        return timeFirstDrawn;
    }

    public void setTimeFirstDrawn(long timeFirstDrawn) {
        this.timeFirstDrawn = timeFirstDrawn;
    }

}
