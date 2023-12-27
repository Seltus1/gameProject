import com.raylib.Raylib;

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
    private Raylib.Color color;
    private boolean canMelee = true;
    private boolean canShoot = true;
    private static final int MELEE_COOLDOWN = 1000;
    private static final int SHOT_COOLDOWN = 500;
    private Melee sword = new Melee(5,100,posX, posY, posY);



    public Player() {
        super(100,10, 10, 100, 100, 5, 20);
        color = RED;
        DrawCircle(posX, posY, size, color);
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
                // Draw a visual representation (circle) of the projectile at the player's position
                DrawCircle(posX, posY, 10, PURPLE);

                // Set the direction of the projectile
                setProjecitleDirection();

                // Create a new Projectile object and add it to the ProjectileHandler
                projList.add(new Projectile(10, posX, posY, 10, projAngle));

                // Reset the cooldown for shooting
                canShoot = false;
                cooldown(SHOT_COOLDOWN, "shot");


        }
        // Check the bounds of projectiles in the ProjectileHandler (likely to handle removal of out-of-bounds projectiles)
        projList.checkProjectilesBounds();
    }

    public void melee() {
        // Check if the space key is pressed and the player can perform a melee attack
        if (IsKeyDown(KEY_SPACE) && canMelee()) {
            // Check if the player can currently perform a melee attack
                // Set the position of the sword to the player's position
                sword.setPosX(posX);
                sword.setPosY(posY);

                // Initiate the sword attack
                sword.attack();

                // Disable further melee attacks until cooldown is over
                canMelee = false;

                // Record the time of the last melee attack

                // Reset the cooldown for the melee attack
                cooldown(MELEE_COOLDOWN, "melee");


        }
    }
    private void cooldown(int cooldown, String type){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(cooldown);
            } catch (InterruptedException e) {
                System.out.println("Woah, something went wrong! (check cooldown method)");
            }
            if(type.equals("shot")){
                canShoot = true;
            }
            if(type.equals("melee")){
                canMelee = true;
            }
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
        DrawCircle(posX, posY, size, color);
    }
    private boolean canShoot() {
        return canShoot;
    }
    private boolean canMelee() {
        return canMelee;
    }
}

