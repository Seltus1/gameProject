package Handlers;

import Attacks.Projectile;
import Attacks.Shield;
import Creatures.Enemies.Enemy;
import Creatures.Players.Player;

public class HealthHandler {
    private CooldownHandler regenTimer;
    public HealthHandler(){
        regenTimer = new CooldownHandler();
    }

    public int regenHp(int currHp, int regenAmt, int maxHp, int timeBetweenRegenMilli){
        if(regenTimer.cooldown(timeBetweenRegenMilli)){
            if(regenAmt + currHp >= maxHp){
                return maxHp;
            }
            currHp += regenAmt;
        }
        return currHp;
    }
    public void damagePlayer(Player player, int damage){
        int damageToDeal = calculateDamageToDeal(player.getDefenceOrArmorForEthan(),damage);
        player.setHp(player.getHp() - damageToDeal);
        player.setCanRegen(false);
        player.getRegenCooldown().resetCooldown();
    }

    public void regenShield(Player player, Shield shield){
        if(player.getShieldHp() < player.getShieldMaxHp()) {
            if (shield.getShieldRegenCD().cooldown(shield.getTotalTimeNeededSinceLastHitToRegen())){
                shield.setRegenShield(true);
            }
            if (shield.isRegenShield()) {
                int amtToRegen = shield.getRegenTimer().regenHp(player.getShieldHp(), shield.getShieldRegen(),  player.getShieldMaxHp(), 500);
                player.setShieldHp(amtToRegen);
                if (player.getShieldHp() >= player.getShieldMaxHp()){
                    shield.setRegenShield(false);
                }
            }
            if(player.getShieldHp() > 0){
                player.setCanUseSecondary(true);
            }
            return;
        }
        shield.setRegenShield(false);
    }
    public void damageShield(Player player, int damage, Shield shield){
        int damageToDeal = calculateDamageToDeal(shield.getShieldDefense(),damage);
        player.setShieldHp(player.getShieldHp() - damageToDeal);
        shield.getShieldRegenCD().resetCooldown();
        shield.setRegenShield(false);
    }
    public void dealDamageToEnemyFromProjectile(Enemy enemy, Projectile projectile) {
        enemy.setHp(enemy.getHp() - projectile.getDamage());
        if (enemy.getHp() <= 0){
            enemy.setAlive(false);
        }
        projectile.setDraw(false);
    }
    public void dealDamageToEnemy(Enemy enemy, int damage) {
        enemy.setHp(enemy.getHp() - damage);
        if (enemy.getHp() <= 0){
            enemy.setAlive(false);
        }
    }
    private int calculateDamageToDeal(int defense, int damage){
        int damageToDeal;
        if(defense < damage){
            damageToDeal = damage - defense;
        }
        else{
            damageToDeal = 1;
        }
        return damageToDeal;
    }


}
