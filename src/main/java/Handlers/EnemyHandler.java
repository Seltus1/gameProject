package Handlers;
import Creatures.Enemies.Brawler.BrawlerEnemy;
import Creatures.Enemies.Brawler.FireBrawlerEnemy;
import Creatures.Enemies.Enemy;
import Creatures.Enemies.Magic.FireMagicEnemy;
import Creatures.Enemies.Magic.MagicEnemy;
import Creatures.Enemies.Sniper.FireSniperEnemy;
import Creatures.Enemies.Sniper.SniperEnemy;
import Creatures.Enemies.Stealth.StealthEnemy;
import Creatures.Player;
import Debuffs.Poison;
import Elements.Fire;

import java.util.*;
import java.util.Random;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

//UNFINISHED make it so handles all enemies
//TEST
public class EnemyHandler extends ListHandler {

    private Random rand = new Random();
    private int funcCounter;
    private int counter;
    private int falseCounter;

    public EnemyHandler() {
        super();
    }

    public void addMultipleEnemies(int amount, Camera2D camera, Player player){
        for (int i = 0; i < amount; i++) {
            int randEnemy = rand.nextInt(7) + 1;
//            int randEnemy = 2;
            int Xpos = rand.nextInt(player.getPosX() + GetScreenWidth());
            int Ypos = rand.nextInt(player.getPosY() + GetScreenHeight());
            int size = 25;
            if(randEnemy == 1){
                SniperEnemy enemy = new SniperEnemy(1, 0, Xpos, Ypos, 0, size, 1300, 35, GREEN, camera);
                add(enemy);
            }
            else if(randEnemy == 2){
                BrawlerEnemy enemy = new BrawlerEnemy(1, 6, Xpos, Ypos, 3, size, 150, BLUE, camera);
                add(enemy);
            }
            else if(randEnemy == 3){
                FireBrawlerEnemy enemy = new FireBrawlerEnemy(1, 3, Xpos, Ypos,3, size, 75, ORANGE, camera);
                add(enemy);
            }
            else if (randEnemy == 4){
                MagicEnemy enemy = new MagicEnemy(1, 4, Xpos, Ypos, 3, size, 800, 550,
                        15, PURPLE, camera);
                add(enemy);
            }
            else if(randEnemy == 5){
                FireSniperEnemy enemy = new FireSniperEnemy(1, 10, Xpos, Ypos, 0, size, 1300,
                        35, ColorFromHSV(29,1,1),camera);
                add(enemy);
            }
            else if (randEnemy == 6){
                StealthEnemy enemy = new StealthEnemy(1, 2, Xpos, Ypos, 10, (int) (size / 1.5), 400,
                        20, GRAY, camera);
                add(enemy);
            }
            else if (randEnemy == 7){
                FireMagicEnemy enemy = new FireMagicEnemy(1, 2, Xpos, Ypos, 3, size,
                        800,500,20, ORANGE, camera);
                add(enemy);
            }
        }
    }

    public ArrayList<Enemy> getEnemyList(){
        return getList();
    }

    public void update(ProjectileHandler projList, Player player, Camera2D camera, Fire fire, Poison poison) {
        for (int i = 0; i < size(); i++) {
            sniperUpdates(player, projList,get(i),camera);
            if(funcCounter != 0){
                brawlerUpdates(player,projList, get(i), fire, camera);
            }
            if(funcCounter != 0){

            }

            if (get(i) instanceof MagicEnemy){
                if(get(i) instanceof FireMagicEnemy){
                    FireMagicEnemy enemy = (FireMagicEnemy) get(i);
                    enemy.update(player,projList,BLACK, camera);
                }
                else {
                    MagicEnemy enemy = (MagicEnemy) get(i);
                    enemy.update(player, projList, DARKPURPLE, camera);
                }
            }
            if( get(i) instanceof  StealthEnemy){
                StealthEnemy enemy = (StealthEnemy) get(i);
                enemy.update(player, projList, camera, poison);
            }
            Enemy enemy = (Enemy) get(i);
//            DrawText("" + enemy.isShouldDraw(), 200,200,20,BLACK);
            if (!enemy.isAlive()){
                removeIndex(i);
            }
            else if(enemy.isShouldDraw()){
                DrawCircle(enemy.getPosX(), enemy.getPosY(), enemy.getSize(), enemy.getColor());
            }
            if (falseCounter == counter){
                player.setFireInRange(false);
            }
            else {
                player.setFireInRange(true);
            }
        }
    }
        private void sniperUpdates(Player player, ProjectileHandler projList, Object enemy, Camera2D camera){
            if (enemy instanceof SniperEnemy){
                funcCounter++;
                if(enemy instanceof  FireSniperEnemy){
                    FireSniperEnemy sniperEnemy = (FireSniperEnemy) enemy;
                    sniperEnemy.shoot(player,projList, camera);
                }
                else {
                    SniperEnemy sniperEnemy = (SniperEnemy) enemy;
                    sniperEnemy.update(player, projList, "Enemy", BLACK, camera);
                }
            }
        }
        private void brawlerUpdates(Player player, ProjectileHandler projList, Object enemy, Fire fire, Camera2D camera){
            if (enemy instanceof BrawlerEnemy){
                funcCounter++;
                if (enemy instanceof  FireBrawlerEnemy) {
                    counter++;
                    FireBrawlerEnemy fireBrawlerEnemy = (FireBrawlerEnemy) enemy;
                    fireBrawlerEnemy.attack(player, fire);
                    if (fireBrawlerEnemy.getVector().distanceToOtherObject(player.getPosX(),player.getPosY()) >= fireBrawlerEnemy.getRange()){
                        falseCounter++;
                    }
                }
                BrawlerEnemy brawlerEnemy = (BrawlerEnemy) enemy;
                brawlerEnemy.update(player,camera);
            }
        }
        private void magicUpdates(){

        }
}