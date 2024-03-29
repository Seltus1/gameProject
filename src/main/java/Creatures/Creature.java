package Creatures;

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
    int getBurnTicks();
    boolean isOnFire();
    int getFireHexCount();
    boolean isShooting();
    boolean isFireHex();
    boolean isFireInRange();
    boolean isPoisoned();
    int getPoisonTicks();
    int getShotcooldown();
    boolean didMelee();




    void setHp(int hp);
    void setDamage(int dps);
    void setRange(int range);
    void setPosX(int posX);
    void setPosY(int posY);
    void setMoveSpeed(int moveSpeed);
    void setSize(int size);
    void setAlive(boolean isAlive);
    void setColor(Raylib.Color color);
    void setBurnTicks(int burn);
    void setOnFire(boolean onFire);
    void setFireHexCount(int hex);
    void setFireHex(boolean fireHex);
    void setAttacking(boolean isAttacking);

    void setShooting(boolean b);
    void setFireInRange(boolean isFireInRange);
    void setPoisoned(boolean poisoned);
    void setPoisonTicks(int poisonCount);
    void setShotCooldown(int shotCooldown);
    void setDidMelee(boolean didMelee);
}