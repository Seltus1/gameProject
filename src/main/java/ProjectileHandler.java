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
            projectile.updateMove();
            projectile.setDistanceTravelled(projectile.getDistanceTravelled() + projectile.getShotSpeed());
        }
    }

    public void checkMaxDistance(Projectile projectile){
        if (projectile.distanceTravelled()){
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