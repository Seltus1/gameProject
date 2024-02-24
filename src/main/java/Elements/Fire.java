package Elements;
import Creatures.*;
import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.HealthHandler;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Fire {
    private String currency;
    private int numCoins;
    private int burnTime;
    private int burnDamage;
    private int burnCooldown;
    private int burnCountdown;
    private int burnCounter;
    private boolean isInRange;
    private boolean isPushFireCalled;
    private boolean isGettingMeleed;
    private int range;
    private int burnFPSCount;
    private int fireHexFPSCount;
    private CooldownHandler cooldown;
    private HealthHandler playerHp;

    public Fire() {
        numCoins = 10;
        burnDamage = 1;
        burnTime = 10;
        isInRange = false;
        range = 75;
        cooldown = new CooldownHandler();
        playerHp = new HealthHandler();
    }

    public void burn(Creature creature) {
        DrawText("" +isGettingMeleed,500,500,30,BLACK);
        if (creature.getBurnTicks() != 0) {
            if (cooldown.cooldown(250)) {
                dealDamage(burnDamage, creature);

                if (creature.isOnFire() && !creature.isFireHex() && !isGettingMeleed){
                    creature.setBurnTicks(creature.getBurnTicks() - burnDamage);
                }
            }
            return;
        }
        creature.setOnFire(false);
    }

    public void fireHex(Creature creature) {
        // checking if the fireHex is on
        if (creature.getFireHexCount() != 0) {
//            setting a cd that the fireHex last X amount of time
            if (cooldown.cooldown(150)) {
                creature.setFireHexCount(creature.getFireHexCount() - 1);
            }

//            checking if the creature has created a projectile
            if (creature.isShooting()) {

//                adding burnTicks to the creature but never over 10
                if (creature.getBurnTicks() + 3 > 10) {
                    creature.setBurnTicks(getBurnTime());
                } else {
                    creature.setBurnTicks(creature.getBurnTicks() + 3);
                }

//                setting the creature on fire so that the brun deals damage
                creature.setOnFire(true);
            }
            return;
        }

//      the hex is over
        creature.setFireHex(false);
    }

    public void meleeAttack(Player player) {
        burnCooldown++;
        if((burnCooldown + 1) % 16 == 0 && player.getBurnTicks() < burnTime) {
            player.setBurnTicks(player.getBurnTicks() + 1);
            player.setIntialBurn(this.burnTime);
            burnCooldown = 0;
        }
    }

    public void shootAttack(Player player){
        player.setOnFire(true);
        if(player.getBurnTicks() < player.getIntialBurn()) {
            if(player.getBurnTicks() + 5 >= player.getIntialBurn()){
                player.setBurnTicks(player.getIntialBurn());
            }
            else {
                player.setBurnTicks(player.getBurnTicks() + 5);
            }
        }

    }

    public void castInferno(Creature creature){
        creature.setFireHex(true);
        creature.setFireHexCount(10);
    }

    private void dealDamage(int damage, Creature creature) {
        if(!(creature instanceof Player)){
            creature.setHp(creature.getHp() - damage);
            return;
        }
        Player player = (Player) creature;
        playerHp.damagePlayer(player,damage);
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public int getNumCoins() {
        return numCoins;
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getBurnCountdown() {
        return burnCountdown;
    }

    public void setBurnCountdown(int burnCountdown) {
        this.burnCountdown = burnCountdown;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public int getBurnDamage() {
        return burnDamage;
    }

    public void setBurnDamage(int burnDamage) {
        this.burnDamage = burnDamage;
    }

    public int getBurnCooldown() {
        return burnCooldown;
    }

    public void setBurnCooldown(int burnCooldown) {
        this.burnCooldown = burnCooldown;
    }

    public int getBurnCounter() {
        return burnCounter;
    }

    public void setBurnCounter(int burnCounter) {
        this.burnCounter = burnCounter;
    }

    public boolean isPushFireCalled() {
        return isPushFireCalled;
    }

    public void setPushFireCalled(boolean pushFireCalled) {
        isPushFireCalled = pushFireCalled;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setBurnFPSCount(int burnFPSCount) {
        this.burnFPSCount = burnFPSCount;
    }
    public int getBurnFPSCount(){
        return burnFPSCount;
    }

    public boolean isGettingMeleed() {
        return isGettingMeleed;
    }

    public void setGettingMeleed(boolean gettingMeleed) {
        isGettingMeleed = gettingMeleed;
    }
}