package Items;

public class Icon {
    private String name;
    private int posX;
    private int posY;
    private int boxSize;
    private boolean addedItem;
    private int cost;
    public Icon(String name, int posX, int posY, int cost){
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        boxSize = 75;
        addedItem = false;
        this.cost = cost;
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
}
