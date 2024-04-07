package Shops;
import Attacks.Shield;
import Attacks.Sword;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import Handlers.*;
import Items.BasicItem;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.Random;

import static com.raylib.Jaylib.*;

public class Shop {
    private int numItems;
    private boolean didRotateItems;
    private ArrayList<String> itemsInShop;
    private Random rand;

    public Shop(){
        numItems = 3;
        itemsInShop = new ArrayList<>();
        rand = new Random();
        didRotateItems = false;
    }

    public void update(GameHandler game){
        if(!didRotateItems){
            rollForItems(game);
        }
        for(int i = 0; i < numItems; i++){
            DrawText(itemsInShop.get(i).toString(),i * -100,-25,20,BLACK);
            DrawRectangleLines( i * -100,-25,75,75,BLACK);

        }
    }
    private void rollForItems(GameHandler game){
        for(int i = 0; i < numItems;i++){
            int randIndex = rand.nextInt(1) + 1;
//            BasicItem itemToAdd = game.getAllItems();
//            itemsInShop.add(itemToAdd);
        }
        didRotateItems = true;
    }

    public boolean isDidRotateItems() {
        return didRotateItems;
    }

    public void setDidRotateItems(boolean didRotateItems) {
        this.didRotateItems = didRotateItems;
    }
}
