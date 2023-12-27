import java.util.ArrayList;

public class ProjectileHandler extends ListHandler{

    public ProjectileHandler(){
        super();
    }

    public void checkProjectilesBounds(){
        // Loop through the list of projectiles
        for (int i = 0; i < size(); i++) {
            // Get the current projectile at index 'i'
            Projectile current = (Projectile) get(i);

            // Check the boundaries for the current projectile
            current.boundsCheck();

            // Check if the current projectile is not within the bounds
            if (!(current.isInBounds())) {
                // If not in bounds, remove it from the list
                removeIndex(i);
            }

            // Move the current projectile
            current.move();
        }
    }
}
