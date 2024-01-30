package Creatures.Enemies.Brawler;

import Creatures.Enemies.Enemy;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import Creatures.*;
import Handlers.*;
import Attacks.*;

import static com.raylib.Raylib.DrawCircle;

public class BrawlerEnemy extends Enemy {

    private int xMul;
    private int yMul;
    private MeleeAttack melee;
    private double xPct;
    private double yPct;


    public BrawlerEnemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, Raylib.Color color, Raylib.Camera2D camera){
        super(hp, damage, posX, posY, moveSpeed, size, range, color, camera);
        melee = new MeleeAttack(getDamage(),getPosX(),getPosY());
    }

    public void attack(Player player){
        int distance = getVector().distanceToOtherObject(player.getPosX(),player.getPosY());
        if(distance <= getRange()){
            melee.attack(player, 500);
        }
    }
    public void move(Player player, Raylib.Camera2D camera){
        if (getRange() < getVector().distanceToOtherObject(getPosX(),player.getPosY())){
            getVector().moveObject(player.getPosition(),"to", camera);
            DrawCircle(getPosX(), getPosY(), getSize(), getColor());
        }
        else{
            setGotinRange(true);
        }
        if(isGotinRange()){
            getVector().circlePlayer(player,getRange());
        }
    }
}
