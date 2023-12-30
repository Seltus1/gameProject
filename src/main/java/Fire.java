public class Fire {
    private String currency;
    private int numCoins;
    private int burnTime;

    private int burnDamage;
    private int burnCooldown;
    private int burnCountdown;
    private int the2ndcounterthe2ndeditionnumber2;
    private boolean isInRange;

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
        burnTime = 3;
        isInRange = false;
    }

    public void attack(Player player) {
        burnCooldown++;
        if((burnCooldown + 1) % 31 == 0 && burnCountdown == 0){
            burnCountdown = burnTime;
            player.setOnFire(true);
            burnCooldown = 0;
        }
//        System.out.println("attack" + burnCooldown);
    }

    public void pushFireDamage(Player player) {
        if (player.isOnFire()){
            the2ndcounterthe2ndeditionnumber2++;
            if((the2ndcounterthe2ndeditionnumber2 + 1) % 15 == 0) {
                player.setHp(player.getHp() - burnDamage);
                if(!isInRange) {
                    burnCountdown--;
                }
            }
        }
        if(burnCountdown == 0){
            player.setOnFire(false);
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
}