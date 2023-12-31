import com.raylib.Raylib;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class StationaryEnemy extends Enemy{
    private boolean canShoot;
    private Random rand = new Random();
    private int shotSpeed;
    private final int SHOOT_COOLDOWN = rand.nextInt(1000) + 2000;
    public StationaryEnemy(int hp, int dps, int posX, int posY, int moveSpeed, int size, int range, int shotSpeed, Raylib.Color color) {
        super(hp, dps, posX, posY, moveSpeed, size, range, color);
        this.shotSpeed = shotSpeed;
        canShoot = false;
        cooldown(SHOOT_COOLDOWN, "shot");
    }
    public void shootPlayer(Player player, ProjectileHandler projList){
        if (canShoot){
            int playerXPos = player.getPosX();
            int playerYPos = player.getPosY();
            Projectile shot = new Projectile(shotSpeed, getPosX(), getPosY(), 7, playerXPos, playerYPos);
            shot.setShotTag("Enemy");
            shot.vectorCalculations();
            projList.add(shot);
            canShoot = false;
            cooldown(SHOOT_COOLDOWN, "shot");
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
            if(type.equals("shot")){
                canShoot = true;
            }
            executor.shutdown();
        });
    }
}