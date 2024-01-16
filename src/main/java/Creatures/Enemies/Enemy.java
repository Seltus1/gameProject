package Creatures.Enemies;
import Handlers.*;
import Creatures.*;
import Attacks.*;
import Elements.*;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import static com.raylib.Raylib.CheckCollisionCircles;
import static com.raylib.Raylib.DrawCircle;

public class Enemy implements Creature {
    private int hp;
    private int damage;
    private int range;
    private int moveSpeed;
    private int size;
    private boolean canMelee;
    private boolean canRange;
    private boolean isAlive;
    private boolean shouldDraw;
    private Raylib.Color color;
    private Jaylib.Vector2 pos;
    private Ellipse2D.Double circle;
    private Vector2D vector;
    private Random rand;
    private boolean shooting;

    public Enemy(int hp, int damage, int posX, int posY, int moveSpeed, int size, int range, Raylib.Color color) {
        this.hp = hp;
        this.damage = damage;
        this.range = range;
        vector = new Vector2D(posX, posY, moveSpeed);
        this.moveSpeed = moveSpeed;
        this.size = size;
        this.color = color;
        this.isAlive = true;
        this.pos = new Jaylib.Vector2(posX,posY);
        DrawCircle(posX, posY, size, color);
        circle = new Ellipse2D.Double(posX,posY,size,size);
        shouldDraw = true;
        rand = new Random();
    }

    public int calculateDistanceToPlayer(Player player){
        int totalDistance = vector.distanceToOtherObject(player.getPosX(), player.getPosY());
        return totalDistance;
    }
    public void followPlayer(Player player, String tag) {
        vector.moveObject(player.getPosition(), tag);
//        updateObjectPositions();
        if(isShouldDraw()) {
            DrawCircle(getPosX(), getPosY(), getSize(), getColor());
        }
    }


    public boolean collisionWIthOtherEnemy(ArrayList<Enemy> enemyList, Player player, String tag){
        double[] positions = determinePositions(player, tag);
        double verticalValues = positions[0];
        double horizontalValues = positions[1];
        double[] normalizedValues = normalizeValues(verticalValues, horizontalValues);
        double xScaled = normalizedValues[0] * getMoveSpeed();
        double yScaled = normalizedValues[1] * getMoveSpeed();
        double updateX = vector.getPosX() + xScaled;
        double updateY = vector.getPosY() + yScaled;
        Jaylib.Vector2 projectedVector = new Jaylib.Vector2((float) updateX,(float) updateY);
        for (int i = 0; i < enemyList.size(); i++) {
            Enemy otherEnemy = enemyList.get(i);
            if (otherEnemy.equals(this)){
                continue;
            }
            double otherPosX = otherEnemy.getPosX();
            double otherPosY = otherEnemy.getPosY();
            Jaylib.Vector2 enemyPos = new Jaylib.Vector2((float) otherPosX,(float) otherPosY);
            if (CheckCollisionCircles(projectedVector,getSize(),enemyPos,otherEnemy.getSize())){
                return true;
            }
        }
        return false;
    }
    private double[] determinePositions(Player player, String tag) {
        double playerXPos, playerYPos, myXPos, myYPos;
        if (tag.equals("to")) {
            playerXPos = player.getPosX();
            playerYPos = player.getPosY();
            myXPos = vector.getPosX();
            myYPos = vector.getPosY();
        } else {
            playerXPos = vector.getActualXPos();
            playerYPos = vector.getActualYPos();
            myXPos = player.getPosX();
            myYPos = player.getPosY();
        }
        return new double[]{playerYPos - myYPos, playerXPos - myXPos};
    }
    private double[] normalizeValues(double verticalValues, double horizontalValues) {
        double magnitude = Math.sqrt(Math.pow(verticalValues, 2) + Math.pow(horizontalValues, 2));
        double yNormalized = verticalValues / magnitude;
        double xNormalized = horizontalValues / magnitude;
        return new double[]{xNormalized, yNormalized};
    }

    @Override
    public Raylib.Color getColor() {
        return color;
    }

    @Override
    public int getBurnTicks() {
        return 0;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public int getFireHexCount() {
        return 0;
    }

    @Override
    public boolean isShooting() {
        return false;
    }

    @Override
    public boolean isFireHex() {
        return false;
    }

    @Override
    public void setColor(Raylib.Color color) {
        this.color = color;
    }

    @Override
    public void setBurnTicks(int burn1) {

    }

    @Override
    public void setOnFire(boolean onFire) {

    }

    @Override
    public void setFireHexCount(int hex) {

    }

    @Override
    public void setFireHex(boolean fireHex) {

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
        return vector.getPosX();
    }

    @Override
    public void setPosX(int posX) {
        vector.setPosX(posX);
    }

    @Override
    public int getPosY() {
        return vector.getPosY();
    }

    @Override
    public void setPosY(int posY) {
        vector.setPosY(posY);
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

    public boolean isCanRange() {
        return canRange;
    }

    public void setCanRange(boolean canRange) {
        this.canRange = canRange;
    }

    public boolean isCanMelee() {
        return canMelee;
    }

    public void setCanMelee(boolean canMelee) {
        this.canMelee = canMelee;
    }

    public Ellipse2D.Double getCircle() {
        return circle;
    }

    public void setCircle(Ellipse2D.Double circle) {
        this.circle = circle;
    }

    public boolean isShouldDraw() {
        return shouldDraw;
    }

    public void setShouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
    }

    public Vector2D getVector() {
        return vector;
    }

    public void setVector(Vector2D vector) {
        this.vector = vector;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public void setShooting(boolean shooting){
        this.shooting = shooting;
    }

    public Jaylib.Vector2 getPos(){
        return vector.getPosition();
    }
}