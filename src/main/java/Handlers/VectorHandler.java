package Handlers;

import com.raylib.Raylib;
import com.raylib.Jaylib;

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
    private Raylib.Vector2 shotPosition;

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
        int y = otherY - posY;
        int x = otherX - posX;
//        returning the total distance
        return Math.abs(x) + Math.abs(y);
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