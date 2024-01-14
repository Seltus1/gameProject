package Handlers;

import Creatures.*;
import Handlers.*;
import Attacks.*;
import Elements.*;

public class ProjectileHandler extends ListHandler {
    private int cooldown;

    public ProjectileHandler(){
        super();
    }

    public void checkProjectilesBounds(Projectile projectile){
        projectile.boundsCheck();
        if (!(projectile.isInBounds())) {
            removeObject(projectile);
        }
        else{
            if (projectile.getShotTag().contains("Fire_Wall")){
//                projectile.drawWall();
            }

            else{
                projectile.updateMove();
            }
            projectile.setDistanceTravelled(projectile.getDistanceTravelled() + projectile.getShotSpeed());
        }
    }

    public void poolshot(Projectile projectile){
        if (projectile.pastMaxDistanceTravelled()) {
            projectile.explodePoolSpell();
            if (projectile.isDraw()){
                cooldown++;
                if((cooldown + 1) % 61 == 0){
                    projectile.setDraw(false);
                    cooldown = 0;
                }
            }
        }
    }


    public void update(){
        for (int i = 0; i < size(); i++) {
            Projectile projectile = (Projectile) get(i);
            checkProjectilesBounds(projectile);
            if(projectile.getShotTag().contains("Pool")) {
                poolshot(projectile);
            }
            else if(projectile.pastMaxDistanceTravelled()) {
                removeObject(projectile);
            }
        }
    }
}