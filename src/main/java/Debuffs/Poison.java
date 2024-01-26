package Debuffs;

import Creatures.Creature;
import com.raylib.Raylib;

import static com.raylib.Raylib.ColorFromHSV;

public class Poison {
    private int damage;
    private float slowMultiplyer;
    private float fireRateMultiplyer;
    private int lifetime;
    private Raylib.Color color;

    public Poison(int damage, float slowMultiplyer, float fireRateMultiplyer, int lifetime) {
        this.damage = damage;
        this.slowMultiplyer = slowMultiplyer;
        this.fireRateMultiplyer = fireRateMultiplyer;
        this.lifetime = lifetime;
        color = ColorFromHSV(103f,.37f,.81f);
    }
    public void applyDebuffs(Creature creature){
        creature.setMoveSpeed((int) (creature.getMoveSpeed() - (creature.getMoveSpeed() * slowMultiplyer)));
        creature.setShotCooldown((int)((creature.getShotcooldown()) * fireRateMultiplyer) + creature.getShotcooldown());
        creature.setPoisoned(true);
    }

    public Raylib.Color getColor() {
        return color;
    }

    public void setColor(Raylib.Color color) {
        this.color = color;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getSlowMultiplyer() {
        return slowMultiplyer;
    }

    public void setSlowMultiplyer(float slowMultiplyer) {
        this.slowMultiplyer = slowMultiplyer;
    }

    public float getFireRateMultiplyer() {
        return fireRateMultiplyer;
    }

    public void setFireRateMultiplyer(float fireRateMultiplyer) {
        this.fireRateMultiplyer = fireRateMultiplyer;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }
}
