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

    public void checkMaxDistance(Projectile projectile){
        if (projectile.distanceTravelled()){
            if(projectile.getShotTag().contains("Pool")){
                projectile.explodePoolSpell();
                projectile.setxMoveSpeed(0);
                projectile.setyMoveSpeed(0);
                if(!projectile.isDraw()){
                    removeObject(projectile);
                }
            }
            if(projectile.getShotTag().contains("Wall")){
                projectile.setxMoveSpeed(0);
                projectile.setyMoveSpeed(0);
            }
            removeObject(projectile);
        }
    }

    public void update(){
        for (int i = 0; i < size(); i++) {
            Projectile projectile = (Projectile) get(i);
            checkMaxDistance(projectile);
            checkProjectilesBounds(projectile);

        }
    }
}