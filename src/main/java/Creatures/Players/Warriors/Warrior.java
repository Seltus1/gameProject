package Creatures.Players.Warriors;
import Attacks.Shield;
import Attacks.Sword;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.EnemyHandler;
import Handlers.ProjectileHandler;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class Warrior extends Player {

    private Sword sword;
    private Shield shield;
    private boolean canAttack;
    private CooldownHandler attackCooldown;
    public Warrior(int hp, int damage, int meleeRange, int posX, int posY, int moveSpeed, int size, Raylib.Camera2D camera, Raylib.Color color) {
        super(hp, damage, meleeRange, posX, posY, moveSpeed, size, camera, color);
        sword = new Sword(damage, camera);
        shield = new Shield(camera);
        attackCooldown = new CooldownHandler();
    }

    public void update(ProjectileHandler projList, Camera2D camera, Raylib.Vector2 mousePos, EnemyHandler enemies){
        attack(enemies, mousePos);
        shield.update(this, mousePos, projList);
//        this needs to update last so that the camera doesn't jiggle
        super.update(projList, camera, mousePos, enemies);
    }

    public void attack(EnemyHandler enemies, Raylib.Vector2 mousePos) {
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)){
            if(attackCooldown.cooldown(500)){
                if(!canMelee()) {
                    setCanMelee(true);
                }
                else{
                    setCanMelee(false);
                }
            }
            if(canMelee()){
                dealingDamage(enemies, mousePos);
                sword.drawSword(this,mousePos);
            }
        }
    }

    private void dealingDamage(EnemyHandler enemies, Raylib.Vector2 mousePos){
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = (Enemy) enemies.get(i);
            Raylib.Vector2[] trianglePoints = sword.calculateTriangle(this,mousePos);
            if(CheckCollisionPointTriangle(enemy.getPos(), trianglePoints[0], trianglePoints[1], trianglePoints[2])){
                sword.attack(enemy,this);
            }
            if(CheckCollisionPointTriangle(enemy.getPos(), trianglePoints[0], trianglePoints[2], trianglePoints[1])) {
                sword.attack(enemy,this);
            }
        }
    }

}
