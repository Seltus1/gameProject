package Handlers;
import Creatures.Enemies.Brawler.BrawlerEnemy;
import Creatures.Enemies.Brawler.FireBrawlerEnemy;
import Creatures.Enemies.Enemy;
import Creatures.Enemies.Magic.FireMagicEnemy;
import Creatures.Enemies.Magic.MagicEnemy;
import Creatures.Enemies.Sniper.FireSniperEnemy;
import Creatures.Enemies.Sniper.SniperEnemy;
import Creatures.Enemies.Stealth.StealthEnemy;
import Creatures.Players.Player;
import Debuffs.Poison;
import Elements.Fire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.GetScreenWidth;

//UNFINISHED make it so handles all enemies
//TEST
public class EnemyHandler extends ListHandler {

    private Random rand = new Random();
    private int funcCounter;
    private int counter;
    private int falseCounter;
    private HashMap<String, Integer> enemies;

    public EnemyHandler() {
        super();
        enemies = new HashMap<>();
        enemies.put("Sniper", 1);
        enemies.put("Brawler", 2);
        enemies.put("FireBrawler", 3);
        enemies.put("Magic", 4);
        enemies.put("FireSniper", 5);
        enemies.put("Stealth", 6);
        enemies.put("FireMagic", 7);
    }

    public void addMultipleEnemies(int amount, Camera2D camera, GameHandler game){
        for (int i = 0; i < amount; i++) {
            ArrayList<String> keyList = new ArrayList<>(enemies.keySet());
            int listIndex = rand.nextInt(enemies.size());
            int randEnemy = enemies.get(keyList.get(listIndex));
//            int randEnemy = enemies.get("Stealth");
            int size = 25;
            int[] enemyPos = enemySpawnPosition(game);
            spawnEnemy(randEnemy, enemyPos[0], enemyPos[1], size, camera);
        }
    }
    private int[] enemySpawnPosition(GameHandler game){
        int Xpos = rand.nextInt(game.getAreaSize()) - game.getAreaSize() / 2;
        int Ypos = rand.nextInt(game.getAreaSize()) - game.getAreaSize() / 2;
        return new int[]{Xpos, Ypos};
    }
    private void spawnEnemy(int randEnemy, int Xpos, int Ypos, int size, Camera2D camera){
        switch(randEnemy){
            case 1:
                SniperEnemy sniperEnemy = new SniperEnemy(1, 0, Xpos, Ypos, 0, size, 1300, 35, 10, GREEN, camera);
                add(sniperEnemy);
                break;
            case 2:
                BrawlerEnemy brawlerEnemy = new BrawlerEnemy(1, 70, Xpos, Ypos, 3, size, 150, 10,BLUE, camera);
                add(brawlerEnemy);
                break;
            case 3:
                FireBrawlerEnemy fireBrawlerEnemy = new FireBrawlerEnemy(1, 3, Xpos, Ypos,3, size, 150, 15, ORANGE, camera);
                add(fireBrawlerEnemy);
                break;
            case 4:
                MagicEnemy magicEnemy = new MagicEnemy(1, 4, Xpos, Ypos, 3, size, 800, 550, 15, 10, PURPLE, camera);
                add(magicEnemy);
                break;
            case 5:
                FireSniperEnemy fireSniperEnemy = new FireSniperEnemy(1, 10, Xpos, Ypos, 0, size, 1300, 35, 15, ColorFromHSV(29,1,1),camera);
                add(fireSniperEnemy);
                break;
            case 6:
                StealthEnemy stealthEnemy = new StealthEnemy(1, 2, Xpos, Ypos, 10, (int) (size / 1.5), 400, 20, 10, GRAY, camera);
                add(stealthEnemy);
                break;
            case 7:
                FireMagicEnemy fireMagicEnemy = new FireMagicEnemy(1, 2, Xpos, Ypos, 3, size, 800,500,20, 15, ORANGE, camera);
                add(fireMagicEnemy);
                break;
        }
    }
    public void update(ProjectileHandler projList, Player player, Camera2D camera, Fire fire, Poison poison) {
        for (int i = 0; i < size(); i++) {
            sniperUpdates(player, projList,get(i),camera);
            if(funcCounter != 1){
                brawlerUpdates(player,projList, get(i), fire, camera);
            }
            if(funcCounter != 1){
                magicUpdates(player,projList,get(i), camera);
            }
            if(funcCounter != 1) {
                stealthUpdates(player,projList,get(i),camera,poison);
            }
            generalEnemyUpdates(player,get(i));
            funcCounter = 0;
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
                fireBrawlerEnemy.attack(player, fire, camera);
                if (fireBrawlerEnemy.getVector().distanceToOtherObject(player.getPosX(),player.getPosY()) >= fireBrawlerEnemy.getRange()){
                    falseCounter++;
                }
            }
            BrawlerEnemy brawlerEnemy = (BrawlerEnemy) enemy;
            brawlerEnemy.update(player,camera);
        }
    }
    private void magicUpdates(Player player, ProjectileHandler projList, Object enemy, Camera2D camera){
            if (enemy instanceof MagicEnemy){
                funcCounter++;
                if(enemy instanceof FireMagicEnemy){
                    FireMagicEnemy fireMagicenemy = (FireMagicEnemy) enemy;
                    fireMagicenemy.update(player, projList, BLACK, camera);
                }
                else {
                    MagicEnemy magicEnemy = (MagicEnemy) enemy;
                    magicEnemy.update(player, projList, DARKPURPLE, camera);
                }
            }
        }
    private void stealthUpdates(Player player, ProjectileHandler projList, Object enemy, Camera2D camera, Poison poison) {
        if (enemy instanceof StealthEnemy) {
            funcCounter++;
            StealthEnemy stealthEnemy = (StealthEnemy) enemy;
            stealthEnemy.update(player, projList, camera, poison);
        }
    }
    private void generalEnemyUpdates(Player player, Object enemy){
        Enemy enemy1 = (Enemy) enemy;
//            DrawText("" + enemy.isShouldDraw(), 200,200,20,BLACK);
        if (enemy1.getHp() <= 0){
            player.setNumCoins(player.getNumCoins() + enemy1.getNumCoinsOnDeath());
            removeObject(enemy1);
        }
        else if(enemy1.isShouldDraw()){
            DrawCircle(enemy1.getPosX(), enemy1.getPosY(), enemy1.getSize(), enemy1.getColor());
        }
        if (falseCounter == counter){
            player.setFireInRange(false);
        }
        else {
            player.setFireInRange(true);
        }
    }
    }
