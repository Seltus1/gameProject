public class ProjectileHandler extends ListHandler{

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
                projectile.drawWall();
            }

            else{
                projectile.updateMove();
            }
            projectile.setDistanceTravelled(projectile.getDistanceTravelled() + projectile.getShotSpeed());
        }
    }

    public void poolshot(Projectile projectile){
        if (projectile.pastMaxDistanceTravelled()) {
                projectile.setxMoveSpeed(0);
                projectile.setyMoveSpeed(0);
                projectile.explodePoolSpell();
                if(!projectile.isDraw()){
                    removeObject(projectile);
                }
            }

        }


    public void update(){
        for (int i = 0; i < size(); i++) {
            Projectile projectile = (Projectile) get(i);
            if(projectile.getShotTag().contains("Pool")) {
                poolshot(projectile);
            }
            else if(projectile.pastMaxDistanceTravelled()) {
                removeObject(projectile);
            }
            checkProjectilesBounds(projectile);
        }
    }
}