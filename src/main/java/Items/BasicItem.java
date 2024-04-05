package Items;

import Creatures.Players.Player;
import Handlers.CooldownHandler;

public abstract class BasicItem {
    protected static int numOfThisItemInInv;
    protected String name;
    protected int cost;
    protected int drawingTimeMilli;
    protected CooldownHandler drawingTime;

    public abstract void applyStatsOrEffect(Player player);
}
