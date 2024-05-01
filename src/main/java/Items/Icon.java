package Items;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;

import java.util.Random;

public class Icon {
    private String name;
    private int posX;
    private int posY;
    private int boxSize;
    private boolean addedItem;
    private int cost;
    private Raylib.Color color;
    private int rarity;
    private int commonChance;
    private int commonRandValue;
    private int uncommonChance;
    private int uncommonRandValue;
    private int rareChance;
    private int rareRandValue;
    private int legendaryChance;
    private int legendaryRandValue;
    private int mythicalChance;
    private int mythicalRandValue;
    public Icon(String name, int posX, int posY, int cost){
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        boxSize = 75;
        addedItem = false;
        this.cost = cost;
//        default rarity chances
        commonChance = 45;
        uncommonChance = 32;
        rareChance = 19;
        legendaryChance = 3;
        mythicalChance = 1;
    }
    public void rarityToColor(){
        switch(rarity){
            case 1:
                color = GRAY;
                break;
            case 2:
                color = GREEN;
                break;
            case 3:
                color = BLUE;
                break;
            case 4:
                color = ORANGE;
                break;
            case 5:
                color = RED;
                break;
        }
    }

    public void determineRarity(int randRarity){
//      for now ensure that all chances add up to 100
//        want to add logic to ensure that chances that dont add up to 100 work as well (for luck modifiers and other stuff that impacts drop chance)
        rarityChanceToRandValues();
        if(randRarity <= commonRandValue){
            rarity = 1;
        }
        else if(randRarity <= uncommonRandValue){
            rarity = 2;
        }
        else if(randRarity <= rareRandValue){
            rarity = 3;
        }
        else if(randRarity <= legendaryRandValue){
            rarity = 4;
        }
        else if(randRarity <= mythicalRandValue){
            rarity = 5;
        }
    }
    private void rarityChanceToRandValues(){
        commonRandValue = commonChance - 1;
        uncommonRandValue = commonRandValue + uncommonChance;
        rareRandValue = uncommonRandValue + rareChance;
        legendaryRandValue = rareRandValue + legendaryChance;
        mythicalRandValue = legendaryRandValue + mythicalChance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(int boxSize) {
        this.boxSize = boxSize;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isAddedItem() {
        return addedItem;
    }

    public void setAddedItem(boolean addedItem) {
        this.addedItem = addedItem;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Raylib.Color getColor() {
        return color;
    }

    public void setColor(Raylib.Color color) {
        this.color = color;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }
}
