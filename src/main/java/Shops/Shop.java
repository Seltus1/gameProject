package Shops;
import Attacks.Shield;
import Attacks.Sword;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import Handlers.*;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class Shop {
    private int numItems;

    public Shop(){
        numItems = 3;
    }

    public void update(){
        for(int i = 0; i < numItems; i++){
            DrawRectangleLines( i * -100,-25,75,75,BLACK);
        }
    }
}
