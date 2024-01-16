package Attacks;

import Creatures.Creature;
import Creatures.Player;
import Handlers.CooldownHandler;
import Handlers.Vector2D;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import static com.raylib.Jaylib.*;

import java.awt.*;

public class PoolShot extends Projectile{
    private int damage;
    private int duration;
    private boolean isDraw;
    private CooldownHandler drawingDuration;
    private CooldownHandler damageCooldown;

    public PoolShot(int posX, int posY, int size, Raylib.Color color, int damage, int duration, String shotTag) {
        super(0, posX, posY, size, 0, 0, shotTag, 1000, true, color);
        this.damage = damage;
        this.duration = duration;
        drawingDuration = new CooldownHandler();
        damageCooldown = new CooldownHandler();
        setShotSpeed(0);
    }

    public void update(Creature creature){
        if(drawingDuration.cooldown(2500)){
            setDraw(false);
            return;
        }
        else{
            setDraw(true);
        }
            dealDamage(creature);
    }

    private void dealDamage(Creature creature){
        if(collidedWithPlayer(creature)){
            if(damageCooldown.cooldown(350)){
                creature.setHp(creature.getHp() - damage);
                isPlayer(creature);
            }
        }
    }

    private boolean collidedWithPlayer(Creature creature){
        Jaylib.Vector2 creaturePos = new Jaylib.Vector2(creature.getPosX(), creature.getPosY());
        return CheckCollisionCircles(creaturePos, creature.getSize(), getPosition(), getShotRad());
    }

    private void isPlayer(Creature creature){
        if(creature instanceof Player) {
            Player player = (Player) creature;
            player.getRegenCooldown().setCurrentFrame(0);
        }
    }

}
