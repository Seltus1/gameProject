package Attacks;
import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.EnemyHandler;
import Handlers.ProjectileHandler;
import Handlers.VectorHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Shield {
    private VectorHandler vector;
    private int threshold;
    private int damageAbsorbed;
    private boolean canShield;
    private Raylib.Vector2 linePoint1;
    private Raylib.Vector2 linePoint2;
    private CooldownHandler shieldCD;
    public Shield(Camera2D camera) {
        vector = new VectorHandler(0, 0, 0, camera);
        canShield = true;
        shieldCD = new CooldownHandler();
        threshold = 150;
    }

    public void update(Player player, Raylib.Vector2 mousePos, ProjectileHandler projList){
        defend(player, mousePos, projList);
        updateMovingSpeed(player);
        if(!canShield){
            shieldCD();
        }
    }

    public void defend(Player player, Raylib.Vector2 mousePos, ProjectileHandler projList){
        if (IsMouseButtonDown(MOUSE_BUTTON_RIGHT) && !player.isMeleeing() && canShield){
            double[] poses = calculateShieldLocation(player,mousePos);
            drawShield(poses);
            for (int j = 0; j < projList.size() ; j++) {
                Projectile projectile = (Projectile) projList.get(j);
                if (!projectile.isDraw()) {
                    continue;
                }
                if (vector.CheckCollisionBetweenLineAndCircle(linePoint1, linePoint2, projectile.getPosition(), projectile.getShotRad())) {
                    damageAbsorbed += projectile.getDamage();
                    projList.removeObject(projectile);
                    if(damageAbsorbed >= threshold){
                        canShield = false;
                        damageAbsorbed = 0;
                    }
                }
            }
        }
    }
    public double[] calculateShieldLocation(Player player, Raylib.Vector2 mousePos){
        Raylib.Vector2 endPoint = vector.findIntersectingPointOnCircleAndMousePos(player.getPosition(),player.getRange() / 2, mousePos);
        double[] poses = vector.findIntersectingPoints(player.getPosition(),endPoint,player.getRange() / 2,35, mousePos);
        linePoint1 = new Raylib.Vector2(new Jaylib.Vector2((float) poses[0], (float) poses[1]));
        linePoint2 = new Raylib.Vector2(new Jaylib.Vector2((float) poses[2], (float) poses[3]));
        return poses;
    }
    public void drawShield(double[] poses){
        DrawLine((int)poses[0],(int)poses[1],(int)poses[2],(int)poses[3], BLACK);
    }

    private void shieldCD(){
        if(shieldCD.cooldown(5000)){
            canShield = true;
        }
    }
    private void updateMovingSpeed(Player player){
        if(IsMouseButtonDown(MOUSE_BUTTON_RIGHT) && canShield){
            player.setMoveSpeed(player.getShieldingSpeed());
            return;
        }
        player.setMoveSpeed(player.getInitialMoveSpeed());

    }

}
