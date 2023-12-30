import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Timer;
import java.util.TimerTask;

public class Fire {
    private String currency;
    private int numCoins;
    private int burnTime;
    private int burnTickTime;
    private boolean shortTickTimer;
    private int burnDamage;
    private long lastAttackTime = 0;
    private boolean isBurning = false;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private Timer timer = new Timer();

    public Fire(){
        currency = "Flame";
        numCoins = 10;
        burnTickTime = 500;
        burnDamage = 10;
        burnTime = 2000;
        shortTickTimer = true;
    }

    public void attack(Player player) {
        long currentTime = System.currentTimeMillis();

        if (!isBurning && (currentTime - lastAttackTime) >= burnTime) {
            isBurning = true;
            lastAttackTime = currentTime;

            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(burnTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    isBurning = false;
                }
            });
        }

        if (shortTickTimer && isBurning && (currentTime - lastAttackTime) >= burnTickTime) {
            player.setHp(player.getHp() - burnDamage);
            lastAttackTime = currentTime;
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
