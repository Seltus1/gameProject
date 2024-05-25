package Handlers;

import Creatures.Players.Player;
import Interactables.Interactable;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.Random;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.RED;
import static com.raylib.Raylib.*;
import static com.raylib.Raylib.DrawCircle;

public class InteractablesHandler {
    private ArrayList<Interactable> interactables;
    private Interactable interactableToUpdate;
    private VectorHandler distanceVector;
    private Interactable testInteractable;
    private Interactable testPower;
    private int defaultInteractableSize;
    private int defaultInteractRadius;
    private ArrayList<Interactable> telepatherParts;
    private Random rand;
    private boolean buildingQuestActive;
    private boolean alreadyPicked;
    private Raylib.Vector2 genPos;


    public InteractablesHandler(Camera2D camera){
        defaultInteractableSize = 20;
        defaultInteractRadius = 70;
        interactables = new ArrayList<>();
        distanceVector = new VectorHandler(0,0,0,camera);
        testInteractable = new Interactable(defaultInteractableSize,70,2,10,10, camera);
        testPower = new Interactable(defaultInteractableSize,70,4,100,100,camera);
        getInteractables().add(testInteractable);
        getInteractables().add(testPower);
        telepatherParts = new ArrayList<>();
        rand = new Random();
        startTelepatherTask(camera);
        alreadyPicked = false;
        genPos = new Raylib.Vector2();
    }

    public void update(Player player, GameHandler game, Camera2D camera){
//        first method also draws the interactables
        determineWhichInteractableToPreformActionOn(player, game);
        preformActionOnInteractable(player, game, camera);
        if(buildingQuestActive){
//            drawBuildingPartsDireciton(telepatherParts, player);
        }
    }
    private void drawInteractables(Interactable interactable){
        DrawCircle(interactable.getPosX(),interactable.getPosY(),interactable.getSize(),BLACK);
    }
    private void drawBuildingPartsDireciton(ArrayList<Interactable> partList, Player player){
        for(int i = 0; i < partList.size(); i ++){
//          gpt cooking lol (it did not cook)

//            int interactableY = partList.get(i).getPosY();
//            int interactableX = partList.get(i).getPosX();
//            double angle = Math.atan2(interactableY - GetScreenHeight() / 2, interactableX - GetScreenWidth() / 2);
//            double maxDistance = Math.min(GetScreenWidth(), GetScreenHeight()) / 2;
//            int triangleSize = 50;
//            int[] xPoints = {
//                    (int) (GetScreenWidth() / 2 + triangleSize * Math.cos(angle)),
//                    (int) (GetScreenWidth()  / 2 + triangleSize * Math.cos(angle + 2 * Math.PI / 3)),
//                    (int) (GetScreenWidth()  / 2 + triangleSize * Math.cos(angle + 4 * Math.PI / 3))
//            };
//            int[] yPoints = {
//                    (int) (GetScreenHeight() / 2 + triangleSize * Math.sin(angle)),
//                    (int) (GetScreenHeight()  / 2 + triangleSize * Math.sin(angle + 2 * Math.PI / 3)),
//                    (int) (GetScreenHeight()  / 2 + triangleSize * Math.sin(angle + 4 * Math.PI / 3))
//            };
//            Raylib.Vector2 point1 = new Raylib.Vector2(new Jaylib.Vector2(xPoints[0], yPoints[0]));
//            Raylib.Vector2 point2 = new Raylib.Vector2(new Jaylib.Vector2(xPoints[1], yPoints[1]));
//            Raylib.Vector2 point3 = new Raylib.Vector2(new Jaylib.Vector2(xPoints[2], yPoints[2]));
//            DrawTriangle(point1,point2,point3, RED);
//            DrawTriangle(point3,point2,point1, RED);




//            want to change this from drawing a line to the interactable
//            to drawing a triangle marking the direction towards the interactable
//            make the triangle bigger the further the distance.
//            if the interactable is the on the screen, don't draw the triangle
            if(distanceVector.distanceBetweenTwoObjects(player.getPosition(), partList.get(i).getPos()) > 800) {
                DrawLineV(player.getPosition(), partList.get(i).getPos(), BLACK);
            }
        }

    }
//    want to make this function be able to switch between differnt interactables.
//    for example if you are interacting with one thing, then get in range with another,
//    then press F it should revert the action of the first and then preform the action on the 2nd
    private void determineWhichInteractableToPreformActionOn(Player player, GameHandler game){
        for (int i = 0; i < getInteractables().size(); i++) {
            Interactable current = getInteractables().get(i);
            drawInteractables(current);
            if(current.isHasPower()){
                current.drawPowerLineBetweenPoweredInteractableAndGen(this);
            }
            DrawText(current.isHasPower() + "", current.getPosX(), current.getPosY() - 100, 30, BLACK);
            if (distanceVector.distanceBetweenTwoObjects(current.getPos(), player.getPosition()) < current.getInteractRadius()) {
                current.setInRange(true);
                if(IsKeyPressed(KEY_F)) {
                    current.updateActionStatus(player, game);
                    if(interactableToUpdate == null && !alreadyPicked){
                        interactableToUpdate = current;
                        alreadyPicked = true;
                    }
                    else if(alreadyPicked && interactableToUpdate != null){
                        int pos1 = distanceVector.distanceBetweenTwoObjects(player.getPosition(),current.getPos());
                        int pos2 = distanceVector.distanceBetweenTwoObjects(player.getPosition(),interactableToUpdate.getPos());
                        if(pos1 < pos2){
                            interactableToUpdate = current;
                            current.updateActionStatus(player,game);
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
            if(!interactableToUpdate.isInRange() && interactableToUpdate.getType() != 4){
//               set the values back to false/revert the actions that the interactable does
                interactableToUpdate.updateActionStatus(player,game);
//                if the interactable is not longer in range, dont update it anymore
                interactableToUpdate = null;
                alreadyPicked = false;
            }
        }
    }

    public void startTelepatherTask(Camera2D camera){
        buildingQuestActive = true;
        for(int i = 0; i < 3; i++){
//            int randPosX = rand.nextInt(4000) - 2000;
//            int randPosY = rand.nextInt(4000) - 2000;
            int randPosX = rand.nextInt(400) - 200;
            int randPosY = rand.nextInt(400) - 200;
            Raylib.Vector2 partPos = new Raylib.Vector2(new Jaylib.Vector2(randPosX,randPosY));
            Interactable telepatherPart = new Interactable(getDefaultInteractableSize(),getDefaultInteractRadius(),1,(int) partPos.x(), (int) partPos.y(), camera);
            telepatherParts.add(telepatherPart);
            getInteractables().add(telepatherPart);
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

    public ArrayList<Interactable> getTelepatherParts() {
        return telepatherParts;
    }

    public void setTelepatherParts(ArrayList<Interactable> telepatherParts) {
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

    public Vector2 getGenPos() {
        return genPos;
    }

    public void setGenPos(Vector2 genPos) {
        this.genPos = genPos;
    }
}
