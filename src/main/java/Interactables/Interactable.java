package Interactables;

import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.GameHandler;
import Handlers.InteractablesHandler;
import Handlers.VectorHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class Interactable {
    private int size;
    private int interactRadius;
    private int posX;
    private int posY;
    private Raylib.Vector2 pos;
    private Raylib.Color color;
    private boolean isInRange;
    private boolean pickup;
    private boolean refillSpaceSuit;
    private boolean expandBorder;
    private boolean build;
    private CooldownHandler spaceSuitOxygenCH;
    private int spaceSuitOxygenIncreaseCD;
    private CooldownHandler expandBorderCH;
    private int expandBorderCD;
    private int buildingRadius;
    private VectorHandler distanceVector;
    protected boolean canBePowered;
    private boolean connectPower;
    private boolean hasPower;
    private boolean openPowerMenu;
    private int menuSize;
    private boolean beingUpdated;

    public Interactable(int size, int interactRadius, int posX, int posY, Raylib.Camera2D camera, InteractablesHandler interactables){
        this.size = size;
        this.interactRadius = interactRadius;
        this.posX = posX;
        this.posY = posY;
        pos = new Raylib.Vector2(new Jaylib.Vector2(posX,posY));
        spaceSuitOxygenCH = new CooldownHandler();
        expandBorderCH = new CooldownHandler();
        distanceVector = new VectorHandler(0,0,0,camera);
        expandBorderCD = 50;
        spaceSuitOxygenIncreaseCD = 200;
        buildingRadius = 150;
        menuSize = 500;
        beingUpdated = false;
        interactables.getInteractables().add(this);
    }
    private void setInitialValues(){
//        if()
    }

    public void update(Player player, GameHandler game, InteractablesHandler interactables, Raylib.Camera2D camera){
    }

    public void updateActionStatus(Player player, GameHandler game, InteractablesHandler interactables){
        if(!canBePowered && player.isConnectingPowerLine()){
            player.setConnectingPowerLine(false);
        }
        if(player.isConnectingPowerLine() && isCanBePowered()){
            connectedPower(player, game, interactables);
            if(!interactables.getActiveGens().contains(interactables.getGenLastInteractedWith())) {
                interactables.getActiveGens().add(interactables.getGenLastInteractedWith());
                interactables.getGenLastInteractedWith().getPoweringList().add(this);
            }
        }
    }
    public boolean isBeingUpdated(){
        if(this instanceof TelepatherPart){
            beingUpdated = build || pickup;
        }
        else if(this instanceof AtmosphereAmplifier){
            beingUpdated = refillSpaceSuit || expandBorder;
        }
        else if(this instanceof Telepather){

        }
        else if(this instanceof PowerGenerator){
            beingUpdated = openPowerMenu || connectPower;
        }
        return beingUpdated;
    }
    public void drawPowerLineBetweenPoweredInteractableAndGen(InteractablesHandler interactables){
        DrawLineV(getPos(),interactables.getGenLastInteractedWith().getPos(), BLACK);
    }
    private void connectedPower(Player player, GameHandler game, InteractablesHandler interactables){
        setHasPower(true);
        player.setConnectingPowerLine(false);
        setConnectPower(false);

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

    public boolean isExpandBorder() {
        return expandBorder;
    }

    public void setExpandBorder(boolean expandBorder) {
        this.expandBorder = expandBorder;
    }

    public boolean isHasPower() {
        return hasPower;
    }

    public void setHasPower(boolean hasPower) {
        this.hasPower = hasPower;
    }

    public boolean isCanBePowered() {
        return canBePowered;
    }

    public void setCanBePowered(boolean canBePowered) {
        this.canBePowered = canBePowered;
    }

    public void setBeingUpdated(boolean beingUpdated) {
        this.beingUpdated = beingUpdated;
    }

    public boolean isRefillSpaceSuit() {
        return refillSpaceSuit;
    }

    public void setRefillSpaceSuit(boolean refillSpaceSuit) {
        this.refillSpaceSuit = refillSpaceSuit;
    }

    public CooldownHandler getSpaceSuitOxygenCH() {
        return spaceSuitOxygenCH;
    }

    public void setSpaceSuitOxygenCH(CooldownHandler spaceSuitOxygenCH) {
        this.spaceSuitOxygenCH = spaceSuitOxygenCH;
    }

    public int getSpaceSuitOxygenIncreaseCD() {
        return spaceSuitOxygenIncreaseCD;
    }

    public void setSpaceSuitOxygenIncreaseCD(int spaceSuitOxygenIncreaseCD) {
        this.spaceSuitOxygenIncreaseCD = spaceSuitOxygenIncreaseCD;
    }

    public CooldownHandler getExpandBorderCH() {
        return expandBorderCH;
    }

    public void setExpandBorderCH(CooldownHandler expandBorderCH) {
        this.expandBorderCH = expandBorderCH;
    }

    public int getExpandBorderCD() {
        return expandBorderCD;
    }

    public void setExpandBorderCD(int expandBorderCD) {
        this.expandBorderCD = expandBorderCD;
    }

    public boolean isBuild() {
        return build;
    }

    public void setBuild(boolean build) {
        this.build = build;
    }

    public int getBuildingRadius() {
        return buildingRadius;
    }

    public void setBuildingRadius(int buildingRadius) {
        this.buildingRadius = buildingRadius;
    }

    public VectorHandler getDistanceVector() {
        return distanceVector;
    }

    public void setDistanceVector(VectorHandler distanceVector) {
        this.distanceVector = distanceVector;
    }

    public boolean isConnectPower() {
        return connectPower;
    }

    public void setConnectPower(boolean connectPower) {
        this.connectPower = connectPower;
    }

    public boolean isOpenPowerMenu() {
        return openPowerMenu;
    }

    public void setOpenPowerMenu(boolean openPowerMenu) {
        this.openPowerMenu = openPowerMenu;
    }

    public int getMenuSize() {
        return menuSize;
    }

    public void setMenuSize(int menuSize) {
        this.menuSize = menuSize;
    }

    public Raylib.Color getColor() {
        return color;
    }

    public void setColor(Raylib.Color color) {
        this.color = color;
    }
}
