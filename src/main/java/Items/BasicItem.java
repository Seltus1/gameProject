package Items;

import Creatures.Players.Player;
import Handlers.CooldownHandler;

public abstract class BasicItem {
    protected static int numOfThisItemInInv;
    protected String name;
    protected int cost;
    protected int drawingTimeMilli;
    protected CooldownHandler drawingTime;
    protected boolean isInShop;

    public abstract void applyStatsOrEffect(Player player);

    public String getName() {
        return name;
    }

    public boolean isInShop() {
        return isInShop;
    }

    public void setInShop(boolean inShop) {
        isInShop = inShop;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
