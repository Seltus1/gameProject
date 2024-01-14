package Elements;
import Creatures.*;
import Handlers.CooldownHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;
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
    private int range;
    private int burnFPSCount;
    private int fireHexFPSCount;

    public Fire(){
        currency = "Flame";
        numCoins = 10;
        burnDamage = 1;
        burnTime = 10;
        isInRange = false;
        range = 75;
    }

    public void burn(Creature creature){
        if (cooldown.cooldown(250)){
            if (creature.getBurnTicks() != 0) {
                creature.setHp(creature.getHp() - burnDamage);
                if (creature.isOnFire()) {
                    creature.setBurnTicks(creature.getBurnTicks() - burnDamage);
                }
            }
            return;
        }
        creature.setOnFire(false);
    }

    public void fireHex(Creature creature) {
        if (creature.getFireHexCount() != 0) {
            if (cooldown.cooldown(500)) {
                creature.setFireHexCount(creature.getFireHexCount() - 1);
                if (creature.isShooting()) {
                    if (creature.getBurnTicks() + 3 > 10) {
                        creature.setBurnTicks(getBurnTime());
                    } else {
                        creature.setBurnTicks(creature.getBurnTicks() + 3);
                    }
                    creature.setOnFire(true);
                }
                return;
            }
            creature.setFireHex(false);
        }
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

    public void magicLongShoot(Creature creature){
        creature.setFireHex(true);
        creature.setFireHexCount(10);
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
}