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
    private Raylib.Vector2 linePoint1;
    private Raylib.Vector2 linePoint2;
    private CooldownHandler shieldCD;
    private int maxDamageToDeal;
    private int currentDamageDealt;
    public Shield(Camera2D camera, Player player) {
        vector = new VectorHandler(0, 0, 0, camera);
         player.setCanShield(true);
        shieldCD = new CooldownHandler();
        maxDamageToDeal = 300;
    }

    public void update(Player player, Raylib.Vector2 mousePos, ProjectileHandler projList){
        defend(player, mousePos, projList);
        updateMovingSpeed(player);
        if(!player.isCanShield()){
            shieldCD(player);
        }
    }

    public void defend(Player player, Raylib.Vector2 mousePos, ProjectileHandler projList){
        if (IsMouseButtonDown(MOUSE_BUTTON_RIGHT) && player.isCanShield() && !player.isMeleeing() && !player.isCharging()){
            double[] poses = calculateShieldLocation(player,mousePos);
            drawShield(poses);
            for (int j = 0; j < projList.size() ; j++){
                Projectile projectile = (Projectile) projList.get(j);
                if (!projectile.isDraw()) {
                    continue;
                }
                float centerX = (linePoint1.x() + linePoint2.x()) / 2;
                float centerY = (linePoint1.y() + linePoint2.y()) / 2;
                Raylib.Vector2 shieldCenterPoint = new Raylib.Vector2(new Jaylib.Vector2(centerX, centerY));
                if (projectile.getVector().distanceToOtherObject((int) centerX,(int) centerY) <= projectile.getShotSpeed()){
                    System.out.println("Hello");
                }
                if (CheckCollisionCircles(shieldCenterPoint, projectile.getShotSpeed(), projectile.getPosition(), projectile.getShotRad())){
                    if (vector.canTheProjectileHitThePlayerCircle(projectile, player, linePoint1, linePoint2)){
                        player.setShieldDamageAbsorbed(player.getShieldDamageAbsorbed() + projectile.getDamage());
                        projList.removeObject(projectile);
                        if(player.getShieldDamageAbsorbed() >= player.getShieldThreshold()){
                            player.setCanShield(false);
                        }
                    }
                }
//                else{
//                    if (vector.CheckCollisionBetweenLineAndCircle(linePoint1, linePoint2, projectile.getPosition(), projectile.getShotRad())) {
//                        player.setShieldDamageAbsorbed(player.getShieldDamageAbsorbed() + projectile.getDamage());
//                        projList.removeObject(projectile);
//                        if(player.getShieldDamageAbsorbed() >= player.getShieldThreshold()){
//                            player.setCanShield(false);
//                        }
//                    }
//                }
            }
        }
    }
    public double[] calculateShieldLocation(Player player, Raylib.Vector2 mousePos){
        Raylib.Vector2 endPoint = vector.findIntersectingPointOnCircleAndMousePos(player.getPosition(), player.getRange() / 2, mousePos);
        double[] poses = vector.findIntersectingPoints(player.getPosition(),endPoint, player.getRange() / 2,35, mousePos);
        linePoint1 = new Raylib.Vector2(new Jaylib.Vector2((float) poses[0], (float) poses[1]));
        linePoint2 = new Raylib.Vector2(new Jaylib.Vector2((float) poses[2], (float) poses[3]));
        return poses;
    }
    public void drawShield(double[] poses){
        DrawLine((int)poses[0],(int)poses[1],(int)poses[2],(int)poses[3], BLACK);
    }

    private void shieldCD(Player player){
        if(player.getShieldCD().cooldown(player.getTotalShieldCD())){
            player.setCanShield(true);
            player.setShieldDamageAbsorbed(0);
        }
    }
    private void updateMovingSpeed(Player player){
        if(IsMouseButtonDown(MOUSE_BUTTON_RIGHT) && player.isCanShield() && !player.isCharging()){
            player.setMoveSpeed(player.getShieldingSpeed());
            player.setShielding(true);
            return;
        }
        player.setMoveSpeed(player.getInitialMoveSpeed());
        player.setShielding(false);
    }

    public Raylib.Vector2 getLinePoint1() {
        return linePoint1;
    }

    public void setLinePoint1(Raylib.Vector2 linePoint1) {
        this.linePoint1 = linePoint1;
    }

    public Raylib.Vector2 getLinePoint2() {
        return linePoint2;
    }

    public void setLinePoint2(Raylib.Vector2 linePoint2) {
        this.linePoint2 = linePoint2;
    }

    public int getMaxDamageToDeal() {
        return maxDamageToDeal;
    }

    public void setMaxDamageToDeal(int maxDamageToDeal) {
        this.maxDamageToDeal = maxDamageToDeal;
    }

    public int getCurrentDamageDealt() {
        return currentDamageDealt;
    }

    public void setCurrentDamageDealt(int currentDamageDealt) {
        this.currentDamageDealt = currentDamageDealt;
    }
}
