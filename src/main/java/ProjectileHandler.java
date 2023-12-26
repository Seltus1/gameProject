import java.util.ArrayList;

public class ProjectileHandler extends ListHandler{
    private ArrayList<Projectile> projList = getList();

    public ProjectileHandler(){
        super();
    }

    public void checkProjectilesBounds(){
        for(int i = 0; i < projList.size(); i++){
            Projectile current = projList.get(i);
            current.boundsCheck();
            if(!(current.isInBounds())){
                projList.remove(i);
            }
            current.move();
        }
    }
}
