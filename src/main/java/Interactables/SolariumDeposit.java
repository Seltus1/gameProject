package Interactables;

import Creatures.Players.Player;
import Handlers.GameHandler;
import Handlers.InteractablesHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class SolariumDeposit extends Interactable{
    private int chargeIncrease;

    public SolariumDeposit(int size, int interactRadius, int posX, int posY, Raylib.Camera2D camera, InteractablesHandler interactables) {
        super(size, interactRadius, posX, posY, camera, interactables);
        setColor(YELLOW);
        chargeIncrease = 50;
    }
    public void update(Player player, GameHandler game, InteractablesHandler interactables, Raylib.Camera2D camera){
        if(isPickup()){
            pickUpItem(player);
        }
//        if(isBuild()){
//            build(interactables.getTelepatherParts(),interactables,camera, player);
//        }
    }
    public void updateActionStatus(Player player, GameHandler game, InteractablesHandler interactables){
//        if(player.isDidEquipMaterializer()) {
//            setBuild(game.switchBoolVal(isBuild()));
//        }
        if(isPickup()){
            if(checkIfIsInRangeOfGen(interactables)){
                return;
            }
        }
        setPickup(game.switchBoolVal(isPickup()));

        super.updateActionStatus(player, game, interactables);
    }
    private void pickUpItem(Player player) {
        setPosX(player.getPosX());
        setPosY(player.getPosY() - getSize());
        setPos(new Raylib.Vector2(new Jaylib.Vector2(getPosX(),getPosY())));
    }

    private boolean checkIfIsInRangeOfGen(InteractablesHandler interactables){
        for(int i = 0; i < interactables.getAllGens().size(); i++){
            PowerGenerator currentGen = interactables.getAllGens().get(i);
            if(getDistanceVector().distanceBetweenTwoObjects(getPos(), currentGen.getPos()) < interactables.getDefaultInteractRadius()){
                if((currentGen.getCurrentCharge() + 50) <= 100){
                    currentGen.setCurrentCharge(currentGen.getCurrentCharge() + chargeIncrease);
                }
                else{
                    currentGen.setCurrentCharge(100);
                }
//                setPickup(false);
                interactables.setInteractableToUpdate(currentGen);
                interactables.getInteractables().remove(this);
//                interactables.setInteractableToUpdate(null);
                return true;
            }
        }
        return false;
    }


}
