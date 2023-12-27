import java.util.ArrayList;

public class ProjectileHandler extends ListHandler{
    private ArrayList<Projectile> projList = getList();

    public ProjectileHandler(){
        super();
    }

    public void checkProjectilesBounds(){
        // Loop through the list of projectiles
        for (int i = 0; i < projList.size(); i++) {
            // Get the current projectile at index 'i'
            Projectile current = projList.get(i);

            // Check the boundaries for the current projectile
            current.boundsCheck();

            // Check if the current projectile is not within the bounds
            if (!(current.isInBounds())) {
                // If not in bounds, remove it from the list
                projList.remove(i);
            }

            // Move the current projectile
            current.move();
        }
    }
}
