package Interactables;

import Creatures.Players.Player;
import Handlers.GameHandler;
import Handlers.InteractablesHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;

import static com.raylib.Jaylib.*;

public class PowerGenerator extends Interactable{
    private boolean connectedToOtherInteractable;
    private boolean isPoweringInteractable;
    private int currentCharge;
    private ArrayList<Interactable> poweringList;

    public PowerGenerator(int size, int interactRadius, int posX, int posY, Raylib.Camera2D camera, InteractablesHandler interactables) {
        super(size, interactRadius, posX, posY, camera, interactables);
        isPoweringInteractable = false;
        currentCharge = 100;
        poweringList = new ArrayList<>();
        interactables.getAllGens().add(this);
        setColor(BROWN);
    }
    public void updateActionStatus(Player player, GameHandler game, InteractablesHandler interactables){
        interactables.setGenLastInteractedWith(this);
        setOpenPowerMenu(game.switchBoolVal(isOpenPowerMenu()));
        if(!isOpenPowerMenu()){
            player.setDrawPlayer(true);
        }
//                else {
//                    player.setDrawPlayer(false);
//                }
        if(isConnectPower()){
            setConnectPower(false);
        }
        super.updateActionStatus(player, game, interactables);

    }

    public void update(Player player, GameHandler game, InteractablesHandler interactables, Raylib.Camera2D camera){
        if(isOpenPowerMenu()){
            openPowerMenu(player, game);
        }
        if(isConnectPower()){
            powerLineToOtherInteractable(player);
        }
    }
    private void powerLineToOtherInteractable(Player player){
        DrawLineV(player.getPosition(),getPos(), BLACK);
        player.setConnectingPowerLine(true);
    }


    private void openPowerMenu(Player player, GameHandler game){
//        draw components and make player invisible
        player.setDrawPlayer(false);
        DrawRectangle(player.getPosX() - getMenuSize(),player.getPosY() - getMenuSize()/2, (int) getMenuSize() * 2, getMenuSize(), GRAY);
        DrawCircle(player.getPosX() - getMenuSize()/2, player.getPosY(), 70, BLACK);
        drawPowerLeftOnGen(player);
        Raylib.Vector2 newPowerLineCircle = new Raylib.Vector2(new Jaylib.Vector2(player.getPosX() - getMenuSize()/2, player.getPosY()));
//      check if the player presses on the circle to connect a new power line
        if(IsMouseButtonPressed(MOUSE_BUTTON_LEFT)){
            if(getDistanceVector().distanceBetweenTwoObjects(newPowerLineCircle, game.getMousePos()) <= 70){
                setOpenPowerMenu(false);
                setConnectPower(true);
                player.setDrawPlayer(true);
            }
        }
    }
    private void drawPowerLeftOnGen(Player player){
        float percentage = (float) currentCharge /  100;
        float height = percentage * 300;
        float remainingHeight = 300 - height;
        float barTop = player.getPosY() + remainingHeight - 150;
        Raylib.Rectangle oxygen = new Raylib.Rectangle(new Jaylib.Rectangle(player.getPosX()  + getMenuSize()/2, barTop, 40, height));
        Raylib.Rectangle OxyContainer = new Raylib.Rectangle(new Jaylib.Rectangle(player.getPosX()  + getMenuSize()/2, player.getPosY() - 150, 40, 300));
        DrawRectangleRounded(oxygen,10,5,GREEN);
        DrawRectangleRoundedLines(OxyContainer, 10,5,3, BLACK);
        String s = String.format("%d", currentCharge);
        DrawText(s, player.getPosX() + getMenuSize()/2 + 5, player.getPosY() - 100, 20, BLACK);
    }

    public boolean isConnectedToOtherInteractable() {
        return connectedToOtherInteractable;
    }

    public void setConnectedToOtherInteractable(boolean connectedToOtherInteractable) {
        this.connectedToOtherInteractable = connectedToOtherInteractable;
    }

    public boolean isPoweringInteractable() {
        return isPoweringInteractable;
    }

    public void setPoweringInteractable(boolean poweringInteractable) {
        isPoweringInteractable = poweringInteractable;
    }

    public int getCurrentCharge() {
        return currentCharge;
    }

    public ArrayList<Interactable> getPoweringList() {
        return poweringList;
    }

    public void setPoweringList(ArrayList<Interactable> poweringList) {
        this.poweringList = poweringList;
    }

    public void setCurrentCharge(int currentCharge) {
        this.currentCharge = currentCharge;
    }
}
