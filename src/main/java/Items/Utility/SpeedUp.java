package Items.Utility;

import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Items.BasicItem;

public class SpeedUp extends BasicItem {
    private int speedIncrease;


    public SpeedUp(Player player){
        drawingTime = new CooldownHandler();
        cost = 100;
        name = "MOVE";
    }
    @Override
    public void applyStatsOrEffect(Player player) {
        player.setItemTotal(player.getItemTotal() + 1);
        numOfThisItemInInv++;
        if (numOfThisItemInInv == 1) {
             speedIncrease = 15;
        } else {
            speedIncrease = 10;
        }
        int newSpeed = calculateNewSpeed(player);
        player.setInitialMoveSpeed(newSpeed);
        player.setMoveSpeed(newSpeed);
        System.out.println(newSpeed);
    }

    private int calculateNewSpeed(Player player){
        return Math.round(player.getMoveSpeed() * (1 + (float) speedIncrease / 100));
    }

}
