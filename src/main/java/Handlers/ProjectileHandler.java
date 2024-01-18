package Handlers;

import Attacks.*;
import Creatures.Creature;
import Creatures.Enemies.Enemy;
import Creatures.Player;
import Elements.Fire;
import com.raylib.Jaylib;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;

import static com.raylib.Raylib.CheckCollisionCircles;

public class ProjectileHandler extends ListHandler {
    private CooldownHandler cooldown;
    private Fire fire;

    public ProjectileHandler(){
        super();
        cooldown = new CooldownHandler();
        fire = new Fire();
    }

    public void update(EnemyHandler enemies, Player player){
        for (int i = 0; i < size(); i++) {
            Projectile projectile = (Projectile) get(i);
            projectileCollision(projectile, enemies, player);
            moveProjectilesOnScreen(projectile);
            if (projectile.getShotTag().equals("Enemy_Pool")) {
                Pool pool = (Pool) projectile;
                pool.update(player);
            }
//            checking if the projectile should draw, if false removes it from the projlist
            removeProjectiles(projectile);
        }
    }

    private void projectileCollision(Projectile projectile, EnemyHandler enemies, Player player) {
//        checking to see if the projectile was shot by an enemy or player
//        (player can't hit players and enemies cant hit enemies)
        if (projectile.getShotTag().contains("Player")){
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);
                if (CheckCollisionCircles(projectile.getPosition(), projectile.getShotRad(), enemy.getPos(), enemy.getSize())){
//                    updating enemies when collided
                    collidedWithEnemy(enemy, projectile);
                }
            }
            return;
        }
        if (CheckCollisionCircles(projectile.getPosition(), projectile.getShotRad(), player.getPosition(), player.getSize())) {
//          updating players when collided
            colliededWithPlayer(player, projectile);
        }
    }

    private void collidedWithEnemy(Enemy enemy, Projectile projectile) {
        enemy.setHp(enemy.getHp() - projectile.getDamage());
        if (enemy.getHp() <= 0){
            enemy.setAlive(false);
        }
        projectile.setDraw(false);
    }

    private void colliededWithPlayer(Player player, Projectile projectile){
        if(!projectile.getShotTag().equals("Enemy_Pool")) {
            player.setHp(player.getHp() - projectile.getDamage());
        }
        applySpecialShot(projectile,player);
        if (player.getHp() <= 0) {
            player.setAlive(false);
        }
//        reseting the regen cooldown to 0
        player.getRegenCooldown().setCurrentFrame(0);
        if(!(projectile instanceof  Pool)) {
            projectile.setDraw(false);
        }
    }

    private void applySpecialShot(Projectile currProj, Player player) {
        if(currProj.getShotTag().equals("Enemy_Pool_Shot")) {
            poolShot(currProj.getPosX(),currProj.getPosY());
        }
        else if (currProj.getShotTag().contains("Fire")) {
            burn(player);
        }
        else if (currProj.getShotTag().contains("Inferno")) {
            inferno(player);
        }
    }

    private void poolShot(int xPos, int yPos) {
        Pool pool = new Pool(xPos,yPos,50,BLACK,5,2500,"Enemy_Pool");
        add(pool);
    }

    public void burn(Player player) {
        fire.shootAttack(player);
    }

    public void inferno(Player player) {
        fire.castInferno(player);
    }
    private void moveProjectilesOnScreen(Projectile projectile){
        projectile.checkProjIsOnScreen();
//        if the projectile is off screen stop drawing it
//        if (!(projectile.isInBounds())) {
//            projectile.setDraw(false);
//        }
//        if the projectile is on screen update the position and the total distance travelled
        projectile.updateMove();
        projectile.setDistanceTravelled(projectile.getDistanceTravelled() + projectile.getShotSpeed());
    }

    private void removeProjectiles(Projectile projectile) {
//        checks if the porjectile has it its max distance
        if(projectile.pastMaxDistanceTravelled()){
            if(projectile.getShotTag().equals("Enemy_Pool_Shot")){
                int xPos = projectile.getPosX();
                int yPos = projectile.getPosY();
                removeObject(projectile);
                poolShot(xPos,yPos);
                return;
            }
            projectile.setDraw(false);
        }
//        checks if a projectile hit an enemy and removes them
        if (!projectile.isDraw()){
            removeObject(projectile);
        }
    }
}