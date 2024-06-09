package Handlers;

import Creatures.Players.Player;
import Interactables.*;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.Random;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.Vector2Scale;

public class InteractablesHandler {
    private ArrayList<Interactable> interactables;
    private Interactable interactableToUpdate;
    private VectorHandler distanceVector;
    private AtmosphereAmplifier testAtmo;
    private PowerGenerator testPower;
    private int defaultInteractableSize;
    private int defaultInteractRadius;
    private ArrayList<TelepatherPart> telepatherParts;
    private Random rand;
    private boolean buildingQuestActive;
    private boolean alreadyPicked;
    private ArrayList<PowerGenerator> activeGens;
    private ArrayList<PowerGenerator> allGens;
    private PowerGenerator genLastInteractedWith;
    private CooldownHandler decreaseGenPowerLevelCH;
    private int decreaseGenPowerLevelCD;
    private boolean didSetInteractsToFalse;
    private SolariumDeposit testSolarium;


    public InteractablesHandler(Camera2D camera){
        defaultInteractableSize = 20;
        defaultInteractRadius = 70;
        interactables = new ArrayList<>();
        activeGens = new ArrayList<>();
        allGens = new ArrayList<>();
        distanceVector = new VectorHandler(0,0,0,camera);
        decreaseGenPowerLevelCH = new CooldownHandler();
        decreaseGenPowerLevelCD = 1800;
        testAtmo = new AtmosphereAmplifier(defaultInteractableSize,70,10,10, camera, this);
        testPower = new PowerGenerator(defaultInteractableSize,70,100,100,camera, this);
        testSolarium = new SolariumDeposit(defaultInteractableSize,70,200,200,camera, this);
        telepatherParts = new ArrayList<>();
        rand = new Random();
        startTelepatherTask(camera);
        alreadyPicked = false;
    }

    public void update(Player player, GameHandler game, Camera2D camera){
//        first method also draws the interactables
        determineWhichInteractableToPreformActionOn(player, game);
        preformActionOnInteractable(player, game, camera);
        if(!activeGens.isEmpty()){
            for(int i = 0; i < activeGens.size(); i++){
                decreaseGenPowerLevel(activeGens.get(i));
//                DrawText(genList.get(i).getCurrentCharge() + "", genList.get(i).getPosX(), genList.get(i).getPosY() - 100, 30, BLACK);
            }
        }
        if(buildingQuestActive){
            drawBuildingPartsDirection(telepatherParts, player);
        }
    }
    private void drawInteractables(Interactable interactable){
        DrawCircle(interactable.getPosX(),interactable.getPosY(),interactable.getSize(),interactable.getColor());
    }

    private void drawBuildingPartsDirection(ArrayList<TelepatherPart> partList, Player player){
        for(int i = 0; i < partList.size();i++){
//            find the direction of the vector from the players position to the position of the part
            Raylib.Vector2 direction = Vector2Subtract(partList.get(i).getPos(), player.getPosition());
//            normalize that vector to make it a unit vector
            Raylib.Vector2 normalizedDir = Vector2Normalize(direction);
//            multiply that vector by a scalar of 100
            Raylib.Vector2 scaledDir = Vector2Scale(normalizedDir, 200);
//            add that vector to the players position so it follows the players position
            Raylib.Vector2 endPoint = Vector2Add(scaledDir,player.getPosition());



//            draw a line from the players position to the endpoint
            DrawLineV(player.getPosition(),endPoint,BLACK);

        }
    }










    //    want to make this function be able to switch between differnt interactables.
//    for example if you are interacting with one thing, then get in range with another,
//    then press F it should revert the action of the first and then preform the action on the 2nd
    private void determineWhichInteractableToPreformActionOn(Player player, GameHandler game){
        for (int i = 0; i < getInteractables().size(); i++) {
            Interactable current = getInteractables().get(i);
            if(current.isHasPower()){
                current.drawPowerLineBetweenPoweredInteractableAndGen(this);
            }
            drawInteractables(current);
            if (distanceVector.distanceBetweenTwoObjects(current.getPos(), player.getPosition()) < current.getInteractRadius()) {
                current.setInRange(true);

                if(IsKeyPressed(KEY_F)) {
                    current.updateActionStatus(player, game, this);
                    if(interactableToUpdate == null && !alreadyPicked){
                        interactableToUpdate = current;
                        alreadyPicked = true;
                    }

                    else if(alreadyPicked && interactableToUpdate != null){
                        int pos1 = distanceVector.distanceBetweenTwoObjects(player.getPosition(),current.getPos());
                        int pos2 = distanceVector.distanceBetweenTwoObjects(player.getPosition(),interactableToUpdate.getPos());
                        if(pos1 < pos2){
                            current.updateActionStatus(player,game, this);
                            interactableToUpdate = current;
                            current.updateActionStatus(player,game, this);
                        }
                    }
                    else {
                        interactableToUpdate = null;
                    }
                }
            }
            else {
                current.setInRange(false);
            }
        }
//        alreadyPicked = false;
    }

    private void preformActionOnInteractable(Player player, GameHandler game, Camera2D camera){
        if(interactableToUpdate != null){
//          if there is an interactable to update, update it.
            interactableToUpdate.update(player, game, this, camera);
            if(!interactableToUpdate.isInRange() && !player.isConnectingPowerLine() && !interactableToUpdate.isExpandBorder()){
//               set the values back to false/revert the actions that the interactable does
                if(interactableToUpdate.isBeingUpdated()){
                    interactableToUpdate.updateActionStatus(player, game, this);
                }
//                if the interactable is not longer in range, dont update it anymore
                interactableToUpdate = null;
                alreadyPicked = false;
            }
        }
    }

    public void startTelepatherTask(Camera2D camera){
        buildingQuestActive = true;
        for(int i = 0; i < 3; i++){
            int randPosX = rand.nextInt(4000) - 2000;
            int randPosY = rand.nextInt(4000) - 2000;
//            int randPosX = rand.nextInt(400) - 200;
//            int randPosY = rand.nextInt(400) - 200;
            Raylib.Vector2 partPos = new Raylib.Vector2(new Jaylib.Vector2(randPosX,randPosY));
            TelepatherPart telepatherPart = new TelepatherPart(getDefaultInteractableSize(),getDefaultInteractRadius(),(int) partPos.x(), (int) partPos.y(), camera, this);
            telepatherParts.add(telepatherPart);
        }
    }
    private void decreaseGenPowerLevel(PowerGenerator gen){
        if(gen.getCurrentCharge() > 0){
            didSetInteractsToFalse = false;
            if(decreaseGenPowerLevelCH.cooldown(decreaseGenPowerLevelCD)){
                gen.setCurrentCharge(gen.getCurrentCharge() - 1);
            }
        }
        else if(!didSetInteractsToFalse){
            didSetInteractsToFalse = true;
            for(int i = 0; i < gen.getPoweringList().size(); i++){
                gen.getPoweringList().get(i).setHasPower(false);

            }
        }
    }


    public ArrayList<Interactable> getInteractables() {
        return interactables;
    }

    public void setInteractables(ArrayList<Interactable> interactables) {
        this.interactables = interactables;
    }

    public Interactable getInteractableToUpdate() {
        return interactableToUpdate;
    }

    public void setInteractableToUpdate(Interactable interactableToUpdate) {
        this.interactableToUpdate = interactableToUpdate;
    }

    public int getDefaultInteractableSize() {
        return defaultInteractableSize;
    }

    public void setDefaultInteractableSize(int defaultInteractableSize) {
        this.defaultInteractableSize = defaultInteractableSize;
    }

    public int getDefaultInteractRadius() {
        return defaultInteractRadius;
    }

    public void setDefaultInteractRadius(int defaultInteractRadius) {
        this.defaultInteractRadius = defaultInteractRadius;
    }

    public ArrayList<TelepatherPart> getTelepatherParts() {
        return telepatherParts;
    }

    public void setTelepatherParts(ArrayList<TelepatherPart> telepatherParts) {
        this.telepatherParts = telepatherParts;
    }

    public boolean isBuildingQuestActive() {
        return buildingQuestActive;
    }

    public void setBuildingQuestActive(boolean buildingQuestActive) {
        this.buildingQuestActive = buildingQuestActive;
    }

    public boolean isAlreadyPicked() {
        return alreadyPicked;
    }

    public void setAlreadyPicked(boolean alreadyPicked) {
        this.alreadyPicked = alreadyPicked;
    }

    public ArrayList<PowerGenerator> getActiveGens() {
        return activeGens;
    }

    public PowerGenerator getGenLastInteractedWith() {
        return genLastInteractedWith;
    }

    public void setGenLastInteractedWith(PowerGenerator genLastInteractedWith) {
        this.genLastInteractedWith = genLastInteractedWith;
    }

    public ArrayList<PowerGenerator> getAllGens() {
        return allGens;
    }

    public void setAllGens(ArrayList<PowerGenerator> allGens) {
        this.allGens = allGens;
    }
}
