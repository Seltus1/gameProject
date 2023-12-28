import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.CheckCollisionCircles;
import static com.raylib.Raylib.DrawCircle;

public class Enemy implements Creature {
    private int hp;
    private int damage;
    private int range;
    private int posX;
    private int posY;
    private int moveSpeed;
    private int size;
    private boolean isAlive;
    private Raylib.Color color;
    private Jaylib.Vector2 pos;

    public Enemy(int hp, int damage, int range, int posX, int posY, int moveSpeed, int size, Raylib.Color color) {
        this.hp = hp;
        this.damage = damage;
        this.range = range;
        this.posX = posX;
        this.posY = posY;
        this.moveSpeed = moveSpeed;
        this.size = size;
        this.color = color;
        this.isAlive = true;
        this.pos = new Jaylib.Vector2(posX,posY);
        DrawCircle(posX, posY, size, color);
    }

    public void gotDamagedRanged(ProjectileHandler projList) {
        for (int i = 0; i < projList.size(); i++) {
            Projectile currProj = (Projectile) projList.get(i);
            Jaylib.Vector2 currPos = new Jaylib.Vector2(currProj.getPosX(), currProj.getPosY());
            if (CheckCollisionCircles(pos, size, currPos, currProj.getShotRad()) && currProj.getShotTag().equals("Player")) {
                projList.removeIndex(i);
                hp -= currProj.getDamage();
                if (hp <= 0) {
                    isAlive = false;
                }
            }
        }
    }

    @Override
    public Raylib.Color getColor() {
        return color;
    }

    @Override
    public void setColor(Raylib.Color color) {
        this.color = color;
    }
    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int getMoveSpeed() {
        return moveSpeed;
    }

    @Override
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public Jaylib.Vector2 getPos() {
        return pos;
    }

    @Override
    public void setPos(Jaylib.Vector2 pos) {
        this.pos = pos;
    }
}
