

public class ProjectileHandler extends ListHandler{

    public ProjectileHandler(){
        super();
    }

    public void checkProjectilesBounds(){
        for (int i = 0; i < size(); i++) {
            Projectile current = (Projectile) get(i);
            current.boundsCheck();
            if (!(current.isInBounds())) {
                removeIndex(i);
            }
            if (current.getShotTag().equals("Player")){
                current.move();
            }
            else{
                current.updateMove();
            }
        }

    }
}
