package Interactables;

import Handlers.InteractablesHandler;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class Telepather extends Interactable{
    public Telepather(int size, int interactRadius, int posX, int posY, Raylib.Camera2D camera, InteractablesHandler interactables) {
        super(size, interactRadius, posX, posY, camera, interactables);
        canBePowered = true;
        setColor(BLACK);
    }
    public void update(){

    }
}
