package Interactables;

import Creatures.Players.Player;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class Interactable {
    private int size;
    private int interactRadius;
    private int type;
    private int posX;
    private int posY;
    private Raylib.Vector2 pos;
    private boolean isInRange;
    private boolean pickup;

    public Interactable(int size, int interactRadius, int type, int posX, int posY){
        this.size = size;
        this.interactRadius = interactRadius;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        pos = new Raylib.Vector2(new Jaylib.Vector2(posX,posY));
    }
    public void pickUpItem(Player player) {
        if (pickup) {
            posX = player.getPosX();
            posY = player.getPosY() - size;
            pos = new Raylib.Vector2(new Jaylib.Vector2(posX,posY));
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getInteractRadius() {
        return interactRadius;
    }

    public void setInteractRadius(int interactRadius) {
        this.interactRadius = interactRadius;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Raylib.Vector2 getPos() {
        return pos;
    }

    public void setPos(Raylib.Vector2 pos) {
        this.pos = pos;
    }

    public boolean isInRange() {
        return isInRange;
    }

    public void setInRange(boolean inRange) {
        isInRange = inRange;
    }

    public boolean isPickup() {
        return pickup;
    }

    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }
}
