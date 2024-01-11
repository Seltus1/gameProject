package Handlers;

import com.raylib.Jaylib;

import static com.raylib.Raylib.*;

public class Vector2D {
    private final float diagCheck = (float) Math.sqrt(0.5);
    private int posX;
    private int posY;
    private double actualXPos;
    private double actualYPos;
    private Jaylib.Vector2 position;
    private int moveSpeed;
    private double yNormalizedMovement;
    private double xNormalizedMovement;
    private Jaylib.Vector2 shotPosition;

    public Vector2D(int posX, int posY, int moveSpeed){
        this.posX = posX;
        actualXPos = posX;
        this.posY = posY;
        actualYPos = posY;
        this.moveSpeed = moveSpeed;
        position = new Jaylib.Vector2(posX, posY);
    }

    public void playerMove() {
        int hCheck = playerHorizontalCheck();
        int vCheck = playerVerticalCheck();
        if (hCheck != 0 && vCheck != 0) {
            posX += (hCheck * moveSpeed) * diagCheck;
            posY += (vCheck * moveSpeed) * diagCheck;
        } else {
            posX += (hCheck * moveSpeed);
            posY += (vCheck * moveSpeed);
        }
        updatePosition();
    }

    private void updatePosition(){
        Jaylib.Vector2 updatedPosition = new Jaylib.Vector2(posX, posY);
        setPosition(updatedPosition);
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
        int totalDist = Math.abs(x) + Math.abs(y);
        return totalDist;
    }

    public void moveObject(Jaylib.Vector2 otherPosition, String tag){
        double[] positions = determinePositions(otherPosition, tag);
        double verticalValues = positions[0];
        double horizontalValues = positions[1];
        double[] normalizedValues = normalizeValues(verticalValues, horizontalValues);
        double xScaled = normalizedValues[0] * moveSpeed;
        double yScaled = normalizedValues[1] * moveSpeed;
        updatePositions(xScaled, yScaled);
    }

    public double[] determinePositions(Jaylib.Vector2 position, String tag) {
        double otherX, otherY, myXPos, myYPos;
        if (tag.equals("to")) {
            otherX = position.x();
            otherY = position.y();
            myXPos = actualXPos;
            myYPos = actualYPos;
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

    private void updatePositions(double xScaled, double yScaled) {
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

    public void setShootLine() {
        double[] positions = determinePositions(getShotPosition(), "to");
        double[] normalizedValues = normalizeValues(positions[0], positions[1]);
        double xScaled = normalizedValues[0] * moveSpeed;
        double yScaled = normalizedValues[1] * moveSpeed;
        setxNormalizedMovement(xScaled);
        setyNormalizedMovement(yScaled);
    }
    public void updateShootLinePosition(){
        actualXPos = actualXPos + xNormalizedMovement;
        posX = (int) actualXPos;
        actualYPos = actualYPos + yNormalizedMovement;
        posY = (int) actualYPos;
        updatePosition();
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

    public Jaylib.Vector2 getPosition() {
        return position;
    }

    public void setPosition(Jaylib.Vector2 position) {
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

    public Jaylib.Vector2 getShotPosition() {
        return shotPosition;
    }
    public void setShotPosition(Jaylib.Vector2 shotPosition) {
        this.shotPosition = shotPosition;
    }
}
