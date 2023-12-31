import com.raylib.Jaylib;
import com.raylib.Raylib;

public interface Creature {
    int getHp();
    int getDamage();
    int getRange();
    int getPosX();
    int getPosY();
    int getMoveSpeed();
    int getSize();
    boolean isAlive();
    Raylib.Color getColor();

    void setHp(int hp);
    void setDamage(int dps);
    void setRange(int range);
    void setPosX(int posX);
    void setPosY(int posY);
    void setMoveSpeed(int moveSpeed);
    void setSize(int size);
    void setAlive(boolean isAlive);
    void setColor(Raylib.Color color);
}