package Shops;
import Attacks.Shield;
import Attacks.Sword;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import Handlers.*;
import Items.BasicItem;
import Items.Damage.FireRateUp;
import Items.Icon;
import Items.Utility.SpeedUp;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.raylib.Jaylib.*;

public class Shop {
    private int numItems;
    private Random rand;
    private ArrayList<BasicItem> allPossibleItems;
    private Icon[] currentShopItems;
    private FireRateUp shopFireRate;
    private SpeedUp shopSpeed;
    private Icon icon;
    private boolean isInBox;
    private int inBoxNum;
    private boolean checkedToAddItem;
    private HashMap<String, BasicItem> iconToItem;
    private CooldownHandler drawCD;
    private boolean drawBroke;
    private int missingCoins;

    public Shop(Player player) {
        numItems = 3;
        rand = new Random();
        allPossibleItems = new ArrayList<>();
        currentShopItems = new Icon[numItems];
        iconToItem = new HashMap<>();
        drawCD = new CooldownHandler();



        shopFireRate = new FireRateUp(player);
        shopSpeed = new SpeedUp(player);
        iconToItem.put(shopFireRate.getName(), shopFireRate);
        iconToItem.put(shopSpeed.getName(),shopSpeed);


        allPossibleItems.add(shopFireRate);
        allPossibleItems.add(shopSpeed);
    }

    public void update(Player player) {
        for(int i = 0; i < numItems; i++){
            displayItems();
            purchaseItem(player);
        }
    }

    public void reroll() {
        for (int i = 0; i < numItems; i++) {
            int randItem = rand.nextInt(allPossibleItems.size()) + 1;
//            int randItem = 2;
            switch(randItem){
                case 1:
                    createIcons(shopFireRate.getName(), shopFireRate.getCost(), i);
                    break;
                case 2:
                    createIcons(shopSpeed.getName(), shopFireRate.getCost(), i);
                    break;
            }
            currentShopItems[i] = icon;
        }
    }

    private void displayItems() {
        for (int i = 0; i < numItems; i++) {
            if(!currentShopItems[i].isAddedItem()) {
                DrawRectangleLines(currentShopItems[i].getPosX(), currentShopItems[i].getPosY(), currentShopItems[i].getBoxSize(), currentShopItems[i].getBoxSize(), BLACK);
                DrawText(currentShopItems[i].getName(), currentShopItems[i].getPosX(), currentShopItems[i].getPosY(), 30, BLACK);
            }
        }
    }

    private void purchaseItem(Player player) {
        for (int i = 0; i < numItems; i++) {
            if(checkInBox(i, player) && !currentShopItems[inBoxNum].isAddedItem()){
                if(!drawBroke) {
                    DrawText("Purchase? Cost: " + currentShopItems[inBoxNum].getCost(), player.getPosX() - 30, player.getPosY() + (GetScreenHeight() / 2) - 300, 30, BLACK);
                }
                if (IsKeyPressed(KEY_F)) {
                    if(player.getNumCoins() >= currentShopItems[inBoxNum].getCost()){
                        BasicItem itemToAdd = iconToItem.get(currentShopItems[inBoxNum].getName());
                        itemToAdd.applyStatsOrEffect(player);
                        player.setNumCoins(player.getNumCoins() - currentShopItems[inBoxNum].getCost());
                        currentShopItems[inBoxNum].setAddedItem(true);
                        System.out.println(player.getItemTotal());
                    }
                    else{
                        missingCoins = currentShopItems[inBoxNum].getCost() - player.getNumCoins();
                        drawBroke = true;
                    }
                }
            }
        }
        if(drawBroke){
            if(drawCD.cooldown(4000)){
                drawBroke = false;
            }

            DrawText("TOO BROKE MISSING " + missingCoins  + " COINS", player.getPosX() - 30, player.getPosY() + (GetScreenHeight() / 2) - 300, 30, BLACK);
        }
    }

    private void createIcons(String name, int cost, int iteration) {
        icon = new Icon(name, iteration * -100, -25, cost);
    }

    private boolean checkInBox(int i, Player player) {
        int xMin, yMin, xMax, yMax;
        xMin = currentShopItems[i].getPosX();
        yMin = currentShopItems[i].getPosY();
        xMax = currentShopItems[i].getPosX() + currentShopItems[i].getBoxSize();
        yMax = currentShopItems[i].getPosY() + currentShopItems[i].getBoxSize();
        if (player.getPosX() > xMin && player.getPosX() < xMax) {
            if (player.getPosY() > yMin && player.getPosY() < yMax) {
                inBoxNum = i;
                return true;
            }
        }
        return false;
    }
}
