package Interactables;

import Creatures.Players.Player;
import Handlers.GameHandler;
import Handlers.InteractablesHandler;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class AtmosphereAmplifier extends TelepatherPart{
    public AtmosphereAmplifier(int size, int interactRadius, int posX, int posY, Raylib.Camera2D camera, InteractablesHandler interactables) {
        super(size, interactRadius, posX, posY, camera, interactables);
        canBePowered = true;
        setColor(BLUE);
    }
    public void update(Player player, GameHandler game, InteractablesHandler interactables, Raylib.Camera2D camera){
        if(isRefillSpaceSuit()){
            refillSpaceSuit(player, game, interactables);
        }
        if(isExpandBorder()){
            expandBorder(game);
        }
    }
    public void updateActionStatus(Player player, GameHandler game, InteractablesHandler interactables){
        if(isHasPower()) {
            game.setShrinkBorder(false);
            if (!player.isUsingSpaceSuit() && !(player.getSpaceSuitOxygen() == 100)) {
                setRefillSpaceSuit(game.switchBoolVal(isRefillSpaceSuit()));
            }
            if(game.getAreaSize() < game.getInitialAreaSize()){
                setExpandBorder(game.switchBoolVal(isExpandBorder()));
            }
        }
        else{
            game.setShrinkBorder(true);
        }
        super.updateActionStatus(player, game, interactables);

    }
    private void refillSpaceSuit(Player player, GameHandler game, InteractablesHandler interactables){
        if(player.getSpaceSuitOxygen() < 100) {
            if (getSpaceSuitOxygenCH().cooldown(getSpaceSuitOxygenIncreaseCD())) {
                player.setSpaceSuitOxygen(player.getSpaceSuitOxygen() + 1);
                if(player.getSpaceSuitOxygen() == 100 || player.isUsingSpaceSuit()){
//                   set refillSpaceSuit to false
                    setRefillSpaceSuit(game.switchBoolVal(isRefillSpaceSuit()));
                    updateActionStatus(player, game, interactables);
                }
            }
        }
    }
    private void expandBorder(GameHandler game){
        if(getExpandBorderCH().cooldown(getExpandBorderCD())){
            game.setAreaSize(game.getAreaSize()+1);
            if(game.getInitialAreaSize() == game.getAreaSize()) {
                setExpandBorder(false);
            }
        }
    }
}
