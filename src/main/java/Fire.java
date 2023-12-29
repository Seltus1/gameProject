import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Fire {
    private String currency;
    private int numCoins;
    private int burnTime;
    private int burnTickTime;
    private boolean isBurning;
    private boolean ShortTickTimer;
    private int burnDamage;

    public Fire(){
        currency = "Flame";
        numCoins = 10;
        burnTickTime = 500;
    }

    public void attack(Player player){
        if(!isBurning) {
            isBurning = true;
            cooldown(burnTime, "total");
        }
        if (ShortTickTimer && isBurning) {
            player.setHp(player.getHp() - burnDamage);
            cooldown(burnTickTime, "else");
        }

    }

    private void cooldown(int cooldown, String type){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(cooldown);
            } catch (InterruptedException e) {
                System.out.println("Woah, something went wrong! (check cooldown method)");
            }
            if(type.equals("total")){
                isBurning = false;
            }
            else{
                isBurning = false;
            }

            executor.shutdown();
        });
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
