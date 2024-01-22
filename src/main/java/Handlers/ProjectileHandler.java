package Handlers;

import Attacks.*;
import Creatures.Creature;
import Creatures.Enemies.Enemy;
import Creatures.Player;
import Elements.Fire;
import com.raylib.Jaylib;
import com.raylib.Raylib;

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

    public void update(EnemyHandler enemies, Player player, Camera2D camera){
        for (int i = 0; i < size(); i++) {
            Projectile projectile = (Projectile) get(i);
            projectileCollision(projectile, enemies, player, camera);
            moveProjectilesOnScreen(projectile, camera);
            if (projectile.getShotTag().equals("Enemy_Pool")) {
                Pool pool = (Pool) projectile;
                pool.update(player);
            }
//            checking if the projectile should draw, if false removes it from the projlist
            removeProjectiles(projectile, camera);
        }
    }

    private void projectileCollision(Projectile projectile, EnemyHandler enemies, Player player, Camera2D camera) {
//        checking to see if the projectile was shot by an enemy or player
//        (player can't hit players and enemies cant hit enemies)
        if (projectile.getShotTag().contains("Player")){
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);
                Raylib.Vector2 fixedEnemy = GetScreenToWorld2D(enemy.getPos(), camera);
                Raylib.Vector2 fixedProj = GetScreenToWorld2D(projectile.getPosition(), camera);
                DrawText("X " + enemy.getPosX() + "    Y " + enemy.getPosY(),400,400,30,BLACK);
                DrawText("pojX " + projectile.getPosition().x() + "    Y " + projectile.getPosition().y(),500,500,30,BLACK);
                if (CheckCollisionCircles(fixedProj, projectile.getShotRad(), fixedEnemy, enemy.getSize())){
//                    updating enemies when collided
                    collidedWithEnemy(enemy, projectile);
                }
            }
            return;
        }
        Raylib.Vector2 fixedPlayer = GetScreenToWorld2D(player.getPosition(), camera);
        Raylib.Vector2 fixedProj = GetScreenToWorld2D(projectile.getPosition(), camera);

        if (CheckCollisionCircles(fixedProj, projectile.getShotRad(),fixedPlayer, player.getSize())){
//          updating players when collided
            colliededWithPlayer(player, projectile, camera);
        }
    }

    private void collidedWithEnemy(Enemy enemy, Projectile projectile) {
        enemy.setHp(enemy.getHp() - projectile.getDamage());
        if (enemy.getHp() <= 0){
            enemy.setAlive(false);
        }
        projectile.setDraw(false);
    }

    private void colliededWithPlayer(Player player, Projectile projectile, Camera2D camera){
        if(!projectile.getShotTag().equals("Enemy_Pool")) {
            player.setHp(player.getHp() - projectile.getDamage());
        }
        applySpecialShot(projectile,player, camera);
        if (player.getHp() <= 0) {
            player.setAlive(false);
        }
//        reseting the regen cooldown to 0
        player.getRegenCooldown().setCurrentFrame(0);
        if(!(projectile instanceof  Pool)) {
            projectile.setDraw(false);
        }
    }

    private void applySpecialShot(Projectile currProj, Player player, Camera2D camera) {
        if(currProj.getShotTag().equals("Enemy_Pool_Shot")) {
            poolShot(currProj.getPosX(),currProj.getPosY(), camera);
        }
        else if (currProj.getShotTag().contains("Fire")) {
            burn(player);
        }
        else if (currProj.getShotTag().contains("Inferno")) {
            inferno(player);
        }
    }

    private void poolShot(int xPos, int yPos, Camera2D camera) {
        Pool pool = new Pool(xPos,yPos,50,BLACK,5,2500,"Enemy_Pool", camera);
        add(pool);
    }

    public void burn(Player player) {
        fire.shootAttack(player);
    }

    public void inferno(Player player) {
        fire.castInferno(player);
    }
    private void moveProjectilesOnScreen(Projectile projectile, Camera2D camera){
        projectile.checkProjIsOnScreen();
//        if the projectile is off screen stop drawing it
//        if (!(projectile.isInBounds())) {
//            projectile.setDraw(false);
//        }
//        if the projectile is on screen update the position and the total distance travelled
        projectile.updateMove(camera);
        projectile.setDistanceTravelled(projectile.getDistanceTravelled() + projectile.getShotSpeed());
    }

    private void removeProjectiles(Projectile projectile, Camera2D camera) {
//        checks if the porjectile has it its max distance
        if(projectile.pastMaxDistanceTravelled()){
            if(projectile.getShotTag().equals("Enemy_Pool_Shot")){
                int xPos = projectile.getPosX();
                int yPos = projectile.getPosY();
                removeObject(projectile);
                poolShot(xPos,yPos, camera);
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