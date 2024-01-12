package Attacks;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import Creatures.*;
import Handlers.*;
import Attacks.*;
import Elements.*;

public class MeleeAttack {
    private int damage;
    private int posX;
    private int posY;
    private boolean draw;
    private int lifeTime;
    private int cooldown = 0;


    public MeleeAttack(int damage, int posX, int posY){
        this.damage = damage;
        this.posX = posX;
        this.posY = posY;
        lifeTime = 1000;
        draw = false;
        cooldown = 0;
    }
    public void attack(Player player, int time){
        cooldown++;
        if(cooldown  % time == 0){
            player.setHp(player.getHp() - damage);
            player.setTimeSinceHit(System.currentTimeMillis());
            cooldown = 0;
        }

        update();

    }
    public void setPosX(int posX){
        this.posX = posX;
    }
    public void setPosY(int posY){
        this.posY = posY;
    }

    public void cooldown(int cooldown, String type) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(cooldown);
            } catch (InterruptedException e) {
                System.out.println("got interrupted!");
            }

            draw = false;
            executor.shutdown();
        });
    }

    public void update(){
        if(draw) {
            DrawRectangle(this.posX, this.posY, 10, 100, BLACK);
        }
    }
}