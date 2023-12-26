import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
//TEST
public class Player extends Creature{
    private int projAngle;
    private static ArrayList<Projectile> projList = new ArrayList<>();
    private Melee sword = new Melee(5,100,posX, posY, posY);
    private boolean canMelee = true;
    private static final long MELEE_COOLDOWN = 1000;
    private long lastMeleeTime = 0;
    private boolean canShoot = true;
    private static final long SHOT_COOLDOWN = 500;
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
    public void createProjecitle() {
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT) && canShoot()) {
            if (canShoot) {
                DrawCircle(posX, posY, 10, PURPLE);
                setProjecitleDirection();
                projList.add(new Projectile(10, posX, posY, 10, projAngle));
                resetShotCooldown();
            }
        }
        checkProjectilesBounds();
    }

    private boolean canShoot() {
        return canShoot;
    }

    private void resetShotCooldown() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(SHOT_COOLDOWN);
            } catch (InterruptedException e) {
                System.out.println("got interrupted!");
            }
            canShoot = true;
            executor.shutdown();
        });

        // Update the last shot time immediately to prevent rapid clicks during cooldown
        lastShotTime = System.currentTimeMillis();
        canShoot = false;
    }


    public void melee() {
        if (IsKeyDown(KEY_SPACE) && canMelee()) {
            if (canMelee) {
                sword.setPosX(posX);
                sword.setPosY(posY);
                sword.attack();
                canMelee = false;
                lastMeleeTime = System.currentTimeMillis();
                resetMeleeCooldown();
            }
        }
    }
    private boolean canMelee() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastMeleeTime) >= MELEE_COOLDOWN;
    }
    private void resetMeleeCooldown() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(MELEE_COOLDOWN);
            } catch (InterruptedException e) {
                System.out.println("got interrupted!");
            }
            canMelee = true;
            executor.shutdown();
        });
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
    public void update(){
        move();
        createProjecitle();
        melee();
        sword.update();
        DrawCircle(posX, posY, size, RED);
    }

    public static ArrayList<Projectile> getProjList() {
        return projList;
    }
}

