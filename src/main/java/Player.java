import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
//TEST
// ethan test commit
public class Player extends Creature{
    private int projAngle;
//    private static ArrayList<Projectile> projList = new ArrayList<>();
    private Melee sword = new Melee(5,100,posX, posY, posY);
    private boolean canMelee = true;
    private static final long MELEE_COOLDOWN = 1000;
    private long lastMeleeTime = 0;
    private boolean canShoot = true;
    private static final long SHOT_COOLDOWN = 250;
    private long lastShotTime = 0;



    public Player() {
        super(100,10, 10, 100, 100, 5, 20);
        DrawCircle(posX, posY, size, RED);
    }
//move function that updates player posistions and redraws the position.
    public void move() {
        if (IsKeyDown(KEY_W) && posY > 3 + size) {
            posY -= moveSpeed;

        }
        if (IsKeyDown(KEY_S) && posY < GetScreenHeight() - size) {
            posY += moveSpeed;
        }
        if (IsKeyDown(KEY_D) && posX < GetScreenWidth() - size) {
            posX += moveSpeed;
        }
        if (IsKeyDown(KEY_A) && posX > 3 + size) {
            posX -= moveSpeed;
        }
    }

    public void createProjectile(ProjectileHandler projList) {
        // Check if the left mouse button is down and if the player can currently shoot
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT) && canShoot()) {
            // Check if the player can shoot again (additional check, might be redundant)
            if (canShoot) {
                // Draw a visual representation (circle) of the projectile at the player's position
                DrawCircle(posX, posY, 10, PURPLE);

                // Set the direction of the projectile
                setProjecitleDirection();

                // Create a new Projectile object and add it to the ProjectileHandler
                projList.add(new Projectile(10, posX, posY, 10, projAngle));

                // Reset the cooldown for shooting
                resetShotCooldown();
            }
        }
        // Check the bounds of projectiles in the ProjectileHandler (likely to handle removal of out-of-bounds projectiles)
        projList.checkProjectilesBounds();
    }

    private boolean canShoot() {
        return canShoot;
    }

    private void resetShotCooldown() {
        // Create a new single-threaded ExecutorService
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                // Sleep for the specified cooldown duration
                TimeUnit.MILLISECONDS.sleep(SHOT_COOLDOWN);
            } catch (InterruptedException e) {
                // Handle interruption if it occurs
                System.out.println("Got interrupted!");
            }

            // Once the cooldown is over, allow shooting again
            canShoot = true;

            // Shutdown the executor service (as it's no longer needed)
            executor.shutdown();
        });

        // Update the last shot time immediately to prevent rapid clicks during cooldown
        lastShotTime = System.currentTimeMillis();

        // Set the flag to disallow shooting until the cooldown is finished
        canShoot = false;
    }


    public void melee() {
        // Check if the space key is pressed and the player can perform a melee attack
        if (IsKeyDown(KEY_SPACE) && canMelee()) {
            // Check if the player can currently perform a melee attack
            if (canMelee) {
                // Set the position of the sword to the player's position
                sword.setPosX(posX);
                sword.setPosY(posY);

                // Initiate the sword attack
                sword.attack();

                // Disable further melee attacks until cooldown is over
                canMelee = false;

                // Record the time of the last melee attack
                lastMeleeTime = System.currentTimeMillis();

                // Reset the cooldown for the melee attack
                resetMeleeCooldown();
            }
        }
    }
    private boolean canMelee() {
        // Get the current time
        long currentTime = System.currentTimeMillis();

        // Check if enough time has passed since the last melee attack
        return (currentTime - lastMeleeTime) >= MELEE_COOLDOWN;
    }
    private void resetMeleeCooldown() {
        // Create a new single-threaded ExecutorService
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                // Sleep for the specified melee cooldown duration
                TimeUnit.MILLISECONDS.sleep(MELEE_COOLDOWN);
            } catch (InterruptedException e) {
                // Handle interruption if it occurs
                System.out.println("Got interrupted!");
            }

            // Once the cooldown is over, allow melee attacks again
            canMelee = true;

            // Shutdown the executor service (as it's no longer needed)
            executor.shutdown();
        });
    }
    public void setProjecitleDirection(){
        if (IsKeyDown(KEY_W) && IsKeyDown(KEY_A)){
            projAngle = 315;
        }
        else if (IsKeyDown(KEY_W) && IsKeyDown(KEY_D)){
            projAngle = 45;
        }
        else if (IsKeyDown(KEY_D) && IsKeyDown(KEY_S)){
            projAngle = 135;
        }
        else if (IsKeyDown(KEY_A) && IsKeyDown(KEY_S)){
            projAngle = 225;
        }
        else if (IsKeyDown(KEY_W)){
            projAngle = 0;
        }
        else if (IsKeyDown(KEY_D)) {
            projAngle = 90;
        }
        else if (IsKeyDown(KEY_S)){
            projAngle = 180;
        }

        else if (IsKeyDown(KEY_A)){
            projAngle = 270;
        }

    }
//    redraws the players position
    public void update(ProjectileHandler projList){
        // Move the character or player
        move();

        // Create projectiles if conditions are met
        createProjectile(projList);

        // Perform a melee attack if conditions are met
        melee();

        // Update the sword's state
        sword.update();

        // Draw a circle representing the character at its position with a size and color
        DrawCircle(posX, posY, size, RED);
    }
}

