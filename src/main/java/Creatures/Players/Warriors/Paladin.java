package Creatures.Players.Warriors;

import Creatures.Players.Player;
import com.raylib.Raylib;

public class Paladin extends Player {
    public Paladin(int hp, int damage, int meleeRange, int posX, int posY, int moveSpeed, int size, Raylib.Camera2D camera, Raylib.Color color) {
        super(hp, damage, meleeRange, posX, posY, moveSpeed, size, camera, color);
    }


}
