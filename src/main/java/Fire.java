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

    public boolean isInRange() {
        return isInRange;
    }

    public void setInRange(boolean inRange) {
        isInRange = inRange;
    }

    public Fire(){
        currency = "Flame";
        numCoins = 10;
        burnDamage = 1;
        burnTime = 10;
        isInRange = false;
        range = 75;
    }

    public void meleeAttack(Player player) {
        burnCooldown++;
        if((burnCooldown + 1) % 16 == 0 && player.getBurnTicks() < burnTime) {
            player.setBurnTicks(player.getBurnTicks() + 1);
            player.setIntialBurn(this.burnTime);
            burnCooldown = 0;
        }
    }

    public void shootAttack(Player player, Projectile proj){
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
}