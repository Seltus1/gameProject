package Handlers;

import Creatures.Players.Player;

public class HealthHandler {
    private CooldownHandler regenTimer;
    public HealthHandler(){
        regenTimer = new CooldownHandler();
    }

    public int regenHp(int currHp, int regenAmt, int timeBetweenRegenMilli){
        if(regenTimer.cooldown(timeBetweenRegenMilli)){
            currHp += regenAmt;
        }
        return currHp;
    }
    public void damagePlayer(Player player, int damage){
        player.setHp(player.getHp() - damage);
        player.setCanRegen(false);
        player.getRegenCooldown().setCurrentFrame(0);
    }
}
