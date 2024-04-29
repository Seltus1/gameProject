package Items.Damage;

import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Items.BasicItem;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class FireRateUp extends BasicItem {
    private int cooldownDecreasePercent;


    public FireRateUp(Player player){
        drawingTime = new CooldownHandler();
        cost = 100;
        name = "STIM";
    }
    @Override
    public void applyStatsOrEffect(Player player) {
        player.setItemTotal(player.getItemTotal() + 1);
        if (numOfThisItemInInv == 1) {
            cooldownDecreasePercent = 15;
        } else {
            cooldownDecreasePercent = 10;
        }
        int newCD = calculateNewShotCooldown(player);
        player.setShotCooldown(newCD);
        player.getAttackCooldown().resetCooldown();
    }

    private int calculateNewShotCooldown(Player player){
        return Math.round(player.getShotcooldown() * (1 - (float) cooldownDecreasePercent / 100));
    }

}
