package Interactables;

import Creatures.Players.Player;
import Handlers.GameHandler;
import Handlers.InteractablesHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;

import static com.raylib.Jaylib.*;

public class TelepatherPart extends Interactable{

    public TelepatherPart(int size, int interactRadius, int posX, int posY, Raylib.Camera2D camera, InteractablesHandler interactables) {
        super(size, interactRadius, posX, posY, camera, interactables);
        canBePowered = false;
        setColor(GRAY);
    }
    public void update(Player player, GameHandler game, InteractablesHandler interactables, Raylib.Camera2D camera){
        if(isPickup()){
            pickUpItem(player);
        }
        if(isBuild()){
            build(interactables.getTelepatherParts(),interactables,camera, player);
        }
    }
    public void updateActionStatus(Player player, GameHandler game, InteractablesHandler interactables){

        if(player.isDidEquipMaterializer()) {
            setBuild(game.switchBoolVal(isBuild()));
        }
        else{
            setPickup(game.switchBoolVal(isPickup()));
        }
        super.updateActionStatus(player, game, interactables);
    }
    private void pickUpItem(Player player) {
        setPosX(player.getPosX());
        setPosY(player.getPosY() - getSize());
        setPos(new Raylib.Vector2(new Jaylib.Vector2(getPosX(),getPosY())));
    }
    private void build(ArrayList<TelepatherPart> partList, InteractablesHandler interactables, Raylib.Camera2D camera, Player player){
        for(int i = 0; i < partList.size(); i++){
            if(!(getDistanceVector().distanceBetweenTwoObjects(player.getPosition(), partList.get(i).getPos()) < getBuildingRadius())){
                return;
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
        Telepather telepather = new Telepather(interactables.getDefaultInteractableSize(),interactables.getDefaultInteractRadius(),posX,posY,camera, interactables);
        setBuild(false);
        interactables.setBuildingQuestActive(false);
    }
}
