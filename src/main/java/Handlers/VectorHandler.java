package Handlers;

import Creatures.Enemies.Enemy;
import Creatures.Player;
import com.raylib.Raylib;
import com.raylib.Jaylib;

import java.util.Random;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;

public class VectorHandler {
    private final float diagCheck = (float) Math.sqrt(0.5);
    private int posX;
    private int posY;
    private double actualXPos;
    private double actualYPos;
    private Raylib.Vector2 position;
    private int moveSpeed;
    private double yNormalizedMovement;
    private double xNormalizedMovement;
    private double angularPosition = 0.0;
    private Raylib.Vector2 shotPosition;
    private CooldownHandler circleCooldown;
    private Random rand;

    public VectorHandler(int posX, int posY, int moveSpeed, Camera2D camera){
        this.posX = posX;
        actualXPos = posX;
        this.posY = posY;
        actualYPos = posY;
        this.moveSpeed = moveSpeed;
        position = new Jaylib.Vector2(posX, posY);
    }

    public void playerMove(Camera2D camera) {
        DrawText("X" + posX + "Y" + posY, 400,400, 30, BLACK);
        int hCheck = playerHorizontalCheck();
        int vCheck = playerVerticalCheck();
        if (hCheck != 0 && vCheck != 0) {
            posX += (hCheck * moveSpeed * diagCheck);
            posY += (vCheck * moveSpeed) * diagCheck;
        } else {
            posX += (hCheck * moveSpeed);
            posY += (vCheck * moveSpeed);
        }
        screenToWorld(position,camera);
        updateActualPositions(camera);
    }

    private void updateCamera(Raylib.Vector2 position, Camera2D camera){
        camera.target(position);
    }

    private void updateActualPositions(Camera2D camera){
        Raylib.Vector2 updatedPosition = new Jaylib.Vector2(posX, posY);
        setPosition(updatedPosition);
        updateCamera(updatedPosition,camera);
    }

    public int playerHorizontalCheck() {
        int left = 0;
        int right = 0;
        if (IsKeyDown(KEY_A)) {
            left = 1;
        }
        if (IsKeyDown(KEY_D)) {
            right = 1;
        }
        return right - left;
    }

    public int playerVerticalCheck() {
        int up = 0;
        int down = 0;
        if (IsKeyDown(KEY_W)) {
            up = 1;
        }
        if (IsKeyDown(KEY_S)) {
            down = 1;
        }
        return down - up;
    }

    public int distanceToOtherObject(int otherX, int otherY){
        double y = Math.pow((otherY - posY), 2);
        double x = Math.pow((otherX - posX), 2);
//        returning the total distance
        return (int)(Math.sqrt(x+y));
    }

    public void moveObject(Raylib.Vector2 otherPosition, String tag, Camera2D camera){
//
        double[] positions = determinePositions(otherPosition, tag, camera);
        double verticalValues = positions[0];
        double horizontalValues = positions[1];
        double[] normalizedValues = normalizeValues(verticalValues, horizontalValues);
        double xScaled = normalizedValues[0] * moveSpeed;
        double yScaled = normalizedValues[1] * moveSpeed;
        updateObjectPositions(xScaled, yScaled);
    }


    //    why is this function here
//    you can use a setter to change the move speed in projectile and you why are you changing the move speed?
    public void moveObject(Raylib.Vector2 otherPosition, String tag, double moveSpeed, Camera2D camera){
        double[] positions = determinePositions(otherPosition, tag, camera);
        double verticalValues = positions[0];
        double horizontalValues = positions[1];
        double[] normalizedValues = normalizeValues(verticalValues, horizontalValues);
        double xScaled = normalizedValues[0] * moveSpeed;
        double yScaled = normalizedValues[1] * moveSpeed;
        updateObjectPositions(xScaled, yScaled);
    }


    //    why is this function here
//    you can use a setter to change the move speed in projectile and you why are you changing the move speed?



    public double[] determinePositions(Raylib.Vector2 position, String tag, Camera2D camera) {
        double otherX, otherY, myXPos, myYPos;
//        This is moving the object towards the other pos
        if (tag.equals("to")) {
            otherX = position.x();
            otherY = position.y();
            myXPos = actualXPos;
            myYPos = actualYPos;
//        This is moving the object away from the other pos
        } else {
            otherX = actualXPos;
            otherY = actualYPos;
            myXPos = position.x();
            myYPos = position.y();
        }
        return new double[]{otherY - myYPos, otherX - myXPos};
    }

    private double[] normalizeValues(double verticalValues, double horizontalValues) {
        double magnitude = Math.sqrt(Math.pow(verticalValues, 2) + Math.pow(horizontalValues, 2));
        double yNormalized = verticalValues / magnitude;
        double xNormalized = horizontalValues / magnitude;
        return new double[]{xNormalized, yNormalized};
    }

    private void updateObjectPositions(double xScaled, double yScaled) {
        double updateX = actualXPos + xScaled;
        double updateY = actualYPos + yScaled;
        actualXPos = updateX;
        actualYPos = updateY;
        posX = (int) Math.round(updateX);
        posY = (int) Math.round(updateY);
        xNormalizedMovement = xScaled;
        yNormalizedMovement = yScaled;
        setPosition(new Jaylib.Vector2(posX, posY));
    }

    public void setShootLine(Camera2D camera) {
        screenToWorld(getShotPosition(), camera);
        double[] positions = determinePositions(getShotPosition(), "to", camera);
        double[] normalizedValues = normalizeValues(positions[0], positions[1]);
        double xScaled = normalizedValues[0] * moveSpeed;
        double yScaled = normalizedValues[1] * moveSpeed;
        setxNormalizedMovement(xScaled);
        setyNormalizedMovement(yScaled);

    }
    public void updateShootLinePosition(Camera2D camera){
        actualXPos = actualXPos + xNormalizedMovement;
        posX = (int) actualXPos;
        actualYPos = actualYPos + yNormalizedMovement;
        posY = (int) actualYPos;
        updateActualPositions(camera);
    }

    public int[] TriangleShotVectorCalc(String aboveOrBelow, int finalX, int finalY) {
        double swap = getxNormalizedMovement();
        setxNormalizedMovement(getyNormalizedMovement());
        setyNormalizedMovement(swap);
        if (aboveOrBelow.equals("above")){
            finalX += (getxNormalizedMovement() * -3);
            finalY += (getyNormalizedMovement() * 3);
        }
        else{
            finalX -= (getxNormalizedMovement() * -3);
            finalY -= (getyNormalizedMovement() * 3);
        }
        return new int[]{finalX, finalY};
    }



    public void circlePlayer(Player player, float radius) {
        float angularSpeed = 0.01f;
        angularPosition += angularSpeed;

        double newX = player.getPosX() + radius * Math.cos(angularPosition);
        double newY = player.getPosY() + radius * Math.sin(angularPosition);

        setActualXPos(newX);
        setActualYPos(newY);
        setPosX((int) Math.round(newX));
        setPosY((int) Math.round(newY));
        setPosition(new Jaylib.Vector2(getPosX(), getPosY()));
    }

    public double[] findIntersectingPoints(Vector2 circle1, Vector2 circle2, int r1, int r2){
//        getting the 1st circle
        float a =  circle1.x();
        float b =  circle1.y();
        float r =  r1;

//        getting the 2nd circle
        float p =  circle2.x();
        float q =  circle2.y();
        float d =  r2;

//        making mx + c
        float m = (p - a) / (b - q);
        float c = ((a * a) + (b * b) + (d * d) - ((p * p) + (q * q) + (r * r))) / (2 * (b - q));

//        making quadratic formula
        float A = 1 + (m * m);
        float B = 2 * (m * (c - b) - a);
        float C = (a * a) + ((c - b) * (c - b)) - (r * r);

//        getting the x intersections
        float a1 = (B * B) - (4 * A * C);
        double x1 = (-B + Math.sqrt(a1) / (2 * A));
        double x2 = (-B - Math.sqrt(a1) / (2 * A));

//        getting the y intersections
        double y1 = (m * x1) + c;
        double y2 = (m * x2) + c;

        double[] returnIntersections = {x1, y1, x2, y2};
        return returnIntersections;
    }

    public void randMoveInAttackRange(Player player, Enemy enemy, Camera2D camera){
        double[] moveRange = findIntersectingPoints(player.getPosition(), enemy.getPos(), enemy.getRange(), enemy.getRange());
        int x1 = (int) moveRange[0];
        int x2 = (int) moveRange[2];
        int y1 = (int) moveRange[1];
        int y2 = (int) moveRange[3];
        int randX,randY;
        if(x1 > x2){
            randX = rand.nextInt(x1) + x2;
        }
        else{
            randX = rand.nextInt(x2) + x1;
        }
        if(y1 > y2){
            randY = rand.nextInt(y1) + y2;
        }
        else{
            randY = rand.nextInt(y2) + y1;
        }
        Raylib.Vector2 newPos = new Raylib.Vector2(new Jaylib.Vector2(randX, randY));
        moveObject(newPos,"to", camera);
    }




    public Raylib.Vector2 screenToWorld(Raylib.Vector2 vector, Camera2D camera){
        return GetScreenToWorld2D(vector, camera);
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

    public Raylib.Vector2 getPosition() {
        return position;
    }

    public void setPosition(Raylib.Vector2 position) {
        this.position = position;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public double getActualXPos() {
        return actualXPos;
    }

    public void setActualXPos(double actualXPos) {
        this.actualXPos = actualXPos;
    }

    public double getActualYPos() {
        return actualYPos;
    }

    public void setActualYPos(double actualYPos) {
        this.actualYPos = actualYPos;
    }

    public double getyNormalizedMovement() {
        return yNormalizedMovement;
    }

    public void setyNormalizedMovement(double yNormalizedMovement) {
        this.yNormalizedMovement = yNormalizedMovement;
    }

    public double getxNormalizedMovement() {
        return xNormalizedMovement;
    }

    public void setxNormalizedMovement(double xNormalizedMovement) {
        this.xNormalizedMovement = xNormalizedMovement;
    }

    public Raylib.Vector2 getShotPosition() {
        return shotPosition;
    }
    public void setShotPosition(Raylib.Vector2 shotPosition) {
        this.shotPosition = shotPosition;
    }

}