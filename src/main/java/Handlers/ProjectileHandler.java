package Handlers;

import Attacks.*;

public class ProjectileHandler extends ListHandler {
    private CooldownHandler cooldown;

    public ProjectileHandler(){
        super();
        cooldown = new CooldownHandler();

    }
    public void poolshot(Projectile projectile) {
        if (projectile.pastMaxDistanceTravelled()) {
            projectile.explodePoolSpell();
            if (projectile.isDraw()){
                if(cooldown.cooldown(1000)){
                    projectile.setDraw(false);
                }
            }
        }
    }
    public void updatePool(Projectile currProj, ProjectileHandler projList){
        if(currProj.isDraw()) {
            currProj.explodePoolSpell();
        }
        else{
            projList.removeObject(currProj);
        }
    }

    private void moveProjectilesOnScreen(Projectile projectile){
        projectile.checkProjIsOnScreen();
//        if the projectile is off screen stop drawing it
        if (!(projectile.isInBounds())) {
            projectile.setDraw(false);
        }
//        if the projectile is on screen update the position and the total distance travelled
        projectile.updateMove();
        projectile.setDistanceTravelled(projectile.getDistanceTravelled() + projectile.getShotSpeed());
    }

    private void removeProjectiles(Projectile projectile) {
        if (!projectile.isDraw() || projectile.pastMaxDistanceTravelled()){
            removeObject(projectile);
        }
    }

    public void update(){
        for (int i = 0; i < size(); i++) {
            Projectile projectile = (Projectile) get(i);
            moveProjectilesOnScreen(projectile);
            if(projectile.getShotTag().contains("Pool")) {
                poolshot(projectile);
                return;
            }
//            checking if the projectile should draw, if false removes it from the projlist
            removeProjectiles(projectile);
        }
    }
}