package Interactables;

import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.GameHandler;
import Handlers.InteractablesHandler;
import Handlers.VectorHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.DrawLine;
import static com.raylib.Raylib.DrawLineV;

public class Interactable {
    private int size;
    private int interactRadius;
    private int type;
    private int posX;
    private int posY;
    private Raylib.Vector2 pos;
    private boolean isInRange;
    private boolean pickup;
    private boolean refillSpaceSuit;
    private boolean expandBorder;
    private CooldownHandler spaceSuitOxygenCH;
    private int spaceSuitOxygenIncreaseCD;
    private CooldownHandler expandBorderCH;
    private int expandBorderCD;
    private boolean build;
    private int buildingRadius;
    private VectorHandler distanceVector;
    private int futureType;
    private boolean connectedToOtherInteractable;
    private boolean hasPower;
    private boolean canBePowered;
    private boolean connectPower;

    public Interactable(int size, int interactRadius, int type, int posX, int posY, Raylib.Camera2D camera){
        this.size = size;
        this.interactRadius = interactRadius;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        pos = new Raylib.Vector2(new Jaylib.Vector2(posX,posY));
        spaceSuitOxygenCH = new CooldownHandler();
        expandBorderCH = new CooldownHandler();
        distanceVector = new VectorHandler(0,0,0,camera);
        expandBorderCD = 125;
        spaceSuitOxygenIncreaseCD = 200;
        buildingRadius = 150;
    }
    private void setInitialValues(){
//        if()
    }

    public void update(Player player, GameHandler game, InteractablesHandler interactables, Raylib.Camera2D camera){
        if(pickup){
            pickUpItem(player);
        }
        else if(refillSpaceSuit){
            refillSpaceSuit(player, game);
        }
        else if(expandBorder){
            expandBorder(game);
        }
        else if(build){
            build(interactables.getTelepatherParts(),interactables,camera);
        }
        else if(connectPower){
            powerLineToOtherInteractable(player);
        }
    }

    public void updateActionStatus(Player player, GameHandler game){
        switch(type){
//            type 1 = part for telepather
            case 1:
                futureType = 3;
                canBePowered = false;
                if(!player.isDidEquipMaterializer()) {
                    pickup = game.switchBoolVal(pickup);
                }
                else{
                    build = game.switchBoolVal(build);
                }
                break;
//                type 2 = Atmosphere Amplifier
            case 2:
                canBePowered = true;
                if(connectPower){
                    hasPower = true;
                }
                if(!player.isUsingSpaceSuit() && !(player.getSpaceSuitOxygen() == 100)){
                    refillSpaceSuit = game.switchBoolVal(refillSpaceSuit);
                }
                else{
                    expandBorder = game.switchBoolVal(expandBorder);
                    game.setShrinkBorder(game.switchBoolVal(game.isShrinkBorder()));
                }
                break;
//                type 3 = Telepather
//            not sure what I want to do with the telepather fully yet
            case 3:
                canBePowered = true;
                break;
//                type 4 = power generator
            case 4:
                connectPower =  game.switchBoolVal(connectPower);
                break;
        }
    }
    private void pickUpItem(Player player) {
        posX = player.getPosX();
        posY = player.getPosY() - size;
        pos = new Raylib.Vector2(new Jaylib.Vector2(posX,posY));
    }
    private void refillSpaceSuit(Player player, GameHandler game){
       if(player.getSpaceSuitOxygen() < 100) {
           if (spaceSuitOxygenCH.cooldown(spaceSuitOxygenIncreaseCD)) {
               player.setSpaceSuitOxygen(player.getSpaceSuitOxygen()+1);
               if(player.getSpaceSuitOxygen() == 100){
//                   set refillSpaceSuit to false
                   refillSpaceSuit = game.switchBoolVal(refillSpaceSuit);
                   updateActionStatus(player, game);
               }
           }
       }
    }
    private void expandBorder(GameHandler game){
        if(expandBorderCH.cooldown(expandBorderCD)){
            game.setAreaSize(game.getAreaSize()+1);
        }
    }

    private void build(ArrayList<Interactable> partList, InteractablesHandler interactables, Raylib.Camera2D camera){
        for(int i = 0; i < partList.size(); i++){
            if(i!= partList.size() - 1) {
                if (!(distanceVector.distanceBetweenTwoObjects(partList.get(i).getPos(), partList.get(i + 1).getPos()) < buildingRadius)){
                    return;
                }
            }
            else{
                if(!(distanceVector.distanceBetweenTwoObjects(partList.get(i).getPos(), partList.get(i - 1).getPos()) < buildingRadius)){
                    return;
                }
            }
        }
        int posX = 0, posY = 0;
        for(int i = 0; i < partList.size();i++){
            posX += partList.get(i).getPosX();
            posY += partList.get(i).getPosY();
            interactables.getInteractables().remove(partList.get(i));
        }
        posX /= partList.size();
        posY /= partList.size();
        Interactable upgradedInteractable = new Interactable(interactables.getDefaultInteractableSize(),interactables.getDefaultInteractRadius(),futureType,posX,posY,camera);
        interactables.getInteractables().add(upgradedInteractable);
        build = false;
        interactables.setBuildingQuestActive(false);
    }
    private void powerLineToOtherInteractable(Player player){
        DrawLineV(player.getPosition(),getPos(), BLACK);
        player.setConnectingPowerLine(true);

    }
    private void connectedPower(Interactable otherInteractable, Player player){
        hasPower = true;
        player.setConnectingPowerLine(false);
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

}
