package Handlers;

import Creatures.Players.Player;

public class HealthHandler {
    private CooldownHandler regenTimer;
    public HealthHandler(){
        regenTimer = new CooldownHandler();
    }

    public int regenHp(int currHp, int regenAmt, int maxHp, int timeBetweenRegenMilli){
        if(regenTimer.cooldown(timeBetweenRegenMilli)){
            if(regenAmt + currHp >= maxHp){
                return maxHp;
            }
            currHp += regenAmt;
        }
        return currHp;
    }
    public void damagePlayer(Player player, int damage){
        int damageToDeal;
        if(player.getDefenceOrArmorForEthan() < damage) {
             damageToDeal = damage - player.getDefenceOrArmorForEthan();
        }
        else{
            damageToDeal = 1;
        }
        player.setHp(player.getHp() - damageToDeal);
        player.setCanRegen(false);
        player.getRegenCooldown().setCurrentFrame(0);
    }
}
