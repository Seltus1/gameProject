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
    private CooldownHandler cooldown;



    public MeleeAttack(int damage, int posX, int posY){
        this.damage = damage;
        this.posX = posX;
        this.posY = posY;
        lifeTime = 1000;
        draw = false;
        cooldown = new CooldownHandler();
    }
    public void attack(Player player, int time){
        if(cooldown.cooldown(time)){
            player.setHp(player.getHp() - damage);
            player.setTimeSinceHit(System.currentTimeMillis());
        }
    }
    public void setPosX(int posX){
        this.posX = posX;
    }
    public void setPosY(int posY){
        this.posY = posY;
    }

    public void update(){
    }
}