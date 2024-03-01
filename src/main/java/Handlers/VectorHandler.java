package Handlers;

import Attacks.Projectile;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import com.raylib.Raylib;
import com.raylib.Jaylib;

import java.util.HashMap;
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
    private double intersectingCircleDistance;
    private Raylib.Vector2 shotPosition;
    private CooldownHandler circleCooldown;
    private Random rand;
    private Raylib.Vector2 newPos;
    private int counter;
    private boolean hasAlrdyBooleanMoved;
    private double[] positions;

    public VectorHandler(int posX, int posY, int moveSpeed, Camera2D camera){
        this.posX = posX;
        actualXPos = posX;
        this.posY = posY;
        actualYPos = posY;
        this.moveSpeed = moveSpeed;
        position = new Jaylib.Vector2(posX, posY);
        rand = new Random();
        newPos = getPosition();
    }

    public void playerMove(Camera2D camera) {
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
        setActualXPos(getPosX());
        setActualYPos(getPosY());
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
    public int distanceBetweenTwoObjects(Raylib.Vector2 pos1, Raylib.Vector2 pos2){
        double x = Math.pow(pos2.x() - pos1.x(), 2);
        double y = Math.pow(pos2.y() - pos1.y(), 2);
        return (int) (Math.sqrt(x+y));
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

    public void setShootLineProjectiles(Camera2D camera, Projectile projectile) {
        screenToWorld(getShotPosition(), camera);
        positions = determinePositions(getShotPosition(), projectile.getToOrAway(), camera);
    }
    public void setStraightLine(Camera2D camera) {
        screenToWorld(getShotPosition(), camera);
        positions = determinePositions(getShotPosition(), "to", camera);
    }
    public void updateSpeed(){
        double[] normalizedValues = normalizeValues(positions[0], positions[1]);
        double xScaled = normalizedValues[0] * moveSpeed;
        double yScaled = normalizedValues[1] * moveSpeed;
        setxNormalizedMovement(xScaled);
        setyNormalizedMovement(yScaled);
    }
    public void updateShootLinePosition(Camera2D camera){
        updateSpeed();
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
    }

    public double[] findIntersectingPoints(Vector2 circle1, Vector2 circle2, int r1, int r2, Raylib.Vector2 mousePos) {
        // Getting the 1st circle
        float a = circle1.x();
        float b = circle1.y();
        float r = r1;

        // Getting the 2nd circle
        float p = circle2.x();
        float q = circle2.y();
        float d = r2;

        // Handling infinite slope
        double x1, x2, y1, y2;
        float m,c;

        if (b - q != 0) {
            // Making mx + c
            m = (p - a) / (b - q);
            c = ((a * a) + (b * b) + (d * d) - ((p * p) + (q * q) + (r * r))) / (2 * (b - q));

            // Making quadratic formula
            float A = 1 + (m * m);
            float B = 2 * (m * (c - b) - a);
            long C = (long) ((a * a) + ((c - b) * (c - b)) - (r * r));

            // Getting the x intersections

            x1 = ((-B + Math.sqrt((B * B) - (4 * A * C))) / (2 * A));
            x2 = ((-B - Math.sqrt((B * B) - (4 * A * C))) / (2 * A));
            // Getting the y intersections
            y1 = (m * x1) + c;
            y2 = (m * x2) + c;
            intersectingCircleDistance = Math.sqrt(Math.pow((double) y2 - (double) y1, 2) + Math.pow((double) x2 - (double) x1, 2));
        }
        else{
            x1 = x2 = a;
            // Handle infinite slope separately (vertical line)
//          Raylib.Vector2 pos = findIntersectingPointOnCircleAndMousePos(circle1,r1,mousePos);  // Line passes through the point (a, b)
            y1 = b + intersectingCircleDistance / 2;  // One intersection point
            y2 = b - intersectingCircleDistance / 2;  // Other intersection poi nt
        }

        double[] returnIntersections = {x1, y1, x2, y2};
        return returnIntersections;
    }


    private HashMap<String, Float> findSlopeAndIntercept(Raylib.Vector2 startPoint, Raylib.Vector2 endPoint){
        float slope = (endPoint.y() - startPoint.y()) / (endPoint.x() - startPoint.x());
        float intercept = endPoint.y() - (slope * endPoint.x());
        HashMap<String, Float> lineInformation = new HashMap<>();
        lineInformation.put("slope", slope);
        lineInformation.put("intercept", intercept);
        return lineInformation;
    }

    private float[] collisionBetweenLines(HashMap<String, Float> line1, HashMap<String, Float> line2){
//        solving for X
        float newSlope = line2.get("slope") - line1.get("slope");
        float newIntercept = line1.get("intercept") - line2.get("intercept");
        float newX = newIntercept / newSlope;

//        solving for Y
        float newY = line2.get("slope") * newX + line2.get("intercept");
        return new float[]{newX, newY};
    }

    public void randEnemyMove(Player player, Enemy enemy, int rad, Camera2D camera){
        if(!hasAlrdyBooleanMoved){
            newPos = (new Jaylib.Vector2(enemy.getPosX(), enemy.getPosY()));
            setHasAlrdyBooleanMoved(true);
            return;
        }
        enemy.setRandMoving(false);
        int randX,randY;
        if(Math.abs(getPosX() - newPos.x()) >= 20 && Math.abs(getPosY() - newPos.y()) >= 20 ){
            moveObject(newPos, "to", camera);
            enemy.setRandMoving(true);
        }
        else{
            if(player.getPosition().x() - enemy.getPosX() < 0) {
                randX = -rand.nextInt(rad) + getPosX();
            }
            else{
                randX = rand.nextInt(rad) + getPosX();
            }
            if(player.getPosition().y() - enemy.getPosY() < 0) {
                randY = -rand.nextInt(rad) + getPosY();
            }
            else{
                randY = rand.nextInt(rad)  + getPosY();
            }
            newPos = new Raylib.Vector2(new Jaylib.Vector2(randX, randY));
        }
    }
    public void rangeLine(Player player, Raylib.Vector2 mousePos){
        Raylib.Vector2 endPoint = findEndPointOfLine(player.getPosition(),player.getRange(),mousePos);
        // Draw the line from playerPos to endPoint
        DrawLineV(player.getPosition(), endPoint, BLACK);
    }

    public Raylib.Vector2 findEndPointOfLine(Raylib.Vector2 pos1, float rad, Raylib.Vector2 pos2){
        // Calculate the direction vector from player to mouse
        Raylib.Vector2 direction = new Raylib.Vector2(new Jaylib.Vector2(pos2.x() - pos1.x(), pos2.y() - pos1.y()));

        // Normalize the direction vector
        double magnitude =  Math.sqrt((double) (direction.x() * direction.x()) + (double) (direction.y() * direction.y()));
        direction.x((float) (direction.x() / magnitude));
        direction.y((float)(direction.y() / magnitude));

        // Calculate the endpoint of the line based on player's position and direction
        Raylib.Vector2 endPoint = new Raylib.Vector2(new Jaylib.Vector2(pos1.x() + direction.x() * rad, pos1.y() + direction.y() * rad));
        return endPoint;
    }


    public boolean canTheProjectileHitThePlayerCircle(Projectile projectile, Player player, Raylib.Vector2 linePoint1, Raylib.Vector2 linePoint2){
//            calculating projectile shot Line
        Raylib.Vector2 projectileFinalPosition = projectile.getTargetPosition();
        Raylib.Vector2 projectileCurrentPosition = projectile.getPosition();
        HashMap<String, Float> projectileLineInformation = findSlopeAndIntercept(projectileCurrentPosition, projectileFinalPosition);
        HashMap<String, Float> shieldLineInformation = findSlopeAndIntercept(linePoint1, linePoint2);
        float[] interceptingPoint = collisionBetweenLines(projectileLineInformation, shieldLineInformation);
        float x = interceptingPoint[0];
        float y = interceptingPoint[1];
        float maxX = Math.max(linePoint2.x(), linePoint1.x());
        float minX = Math.min(linePoint2.x(), linePoint1.x());
        float maxY = Math.max(linePoint2.y(), linePoint1.y());
        float minY = Math.min(linePoint2.y(), linePoint1.y());
        if ((x >= minX && x <= maxX) && (y >= minY && y <= maxY)){
            return true;
        }
        return false;
    }
    public boolean canTheEnemyHitThePlayerCircle(Enemy enemy, Player player, Raylib.Vector2 linePoint1, Raylib.Vector2 linePoint2){
//            calculating projectile shot Line
        if(enemy.getVector().getShotPosition() == null){
            enemy.getVector().setShotPosition(player.getPosition());
        }
        Raylib.Vector2 projectileFinalPosition = enemy.getVector().getShotPosition();
        Raylib.Vector2 projectileCurrentPosition = enemy.getPos();
        HashMap<String, Float> projectileLineInformation = findSlopeAndIntercept(projectileCurrentPosition, projectileFinalPosition);
        HashMap<String, Float> shieldLineInformation = findSlopeAndIntercept(linePoint1, linePoint2);
        float[] interceptingPoint = collisionBetweenLines(projectileLineInformation, shieldLineInformation);
        float x = interceptingPoint[0];
        float y = interceptingPoint[1];
        float maxX = Math.max(linePoint2.x(), linePoint1.x());
        float minX = Math.min(linePoint2.x(), linePoint1.x());
        float maxY = Math.max(linePoint2.y(), linePoint1.y());
        float minY = Math.min(linePoint2.y(), linePoint1.y());
        if ((x >= minX && x <= maxX) && (y >= minY && y <= maxY)){
            return true;
        }
        return false;
    }
    public boolean CheckCollisionBetweenLineAndCircle(Raylib.Vector2 linePoint1, Raylib.Vector2 linePoint2, Raylib.Vector2 circle, int circleRad) {
        float slope = (float) ((linePoint2.y() - linePoint1.y()) / (linePoint2.x() - linePoint1.x()));
        float b = (float) (linePoint2.y() - (slope * linePoint2.x()));
        int maxY = (int) Math.max(linePoint2.y(), linePoint1.y());
        int maxX = (int) Math.max(linePoint2.x(), linePoint1.x());
        int yIterations = (int) (maxY - Math.min(linePoint2.y(), linePoint1.y()));
        int xIterations = (int) (maxX - Math.min(linePoint2.x(), linePoint1.x()));
        if (xIterations <= yIterations){
            return iterationForY(linePoint1, linePoint2, circle, circleRad, slope, b);
        }
        return iterationsForX(linePoint1, linePoint2, circle, circleRad, slope, b);
    }


    private boolean iterationForY(Raylib.Vector2 linePoint1, Raylib.Vector2 linePoint2, Raylib.Vector2 circle, int circleRad, float slope, float b){
        int maxY = (int) Math.max(linePoint2.y(), linePoint1.y());
        int iterations = (int) (maxY - Math.min(linePoint2.y(), linePoint1.y()));
        for (int i = 0; i <= iterations; i++) {
            float newY = maxY - i;
            float newX = (newY - b) / slope;
            Raylib.Vector2 pos = new Raylib.Vector2(new Jaylib.Vector2(newX, newY));
            if (CheckCollisionPointCircle(pos, circle, circleRad)) {
                return true;
            }
        }
        return false;
    }

    private boolean iterationsForX(Raylib.Vector2 linePoint1, Raylib.Vector2 linePoint2, Raylib.Vector2 circle, int circleRad, float slope, float b){
        int maxX = (int) Math.max(linePoint2.x(), linePoint1.x());
        int iterations = (int) (maxX - Math.min(linePoint2.x(), linePoint1.x()));
        for (int i = 0; i <= iterations; i++) {
            float newX = maxX - i;
            float newY = (slope * newX) + b;
            Raylib.Vector2 pos = new Raylib.Vector2(new Jaylib.Vector2(newX, newY));
            if (CheckCollisionPointCircle(pos, circle, circleRad)) {
                return true;
            }
        }
        return false;
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

    public boolean isHasAlrdyBooleanMoved() {
        return hasAlrdyBooleanMoved;
    }

    public void setHasAlrdyBooleanMoved(boolean hasAlrdyBooleanMoved) {
        this.hasAlrdyBooleanMoved = hasAlrdyBooleanMoved;
    }
}