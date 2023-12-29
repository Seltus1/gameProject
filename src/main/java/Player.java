import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.raylib.Jaylib.DARKGREEN;
import static com.raylib.Jaylib.PURPLE;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Player implements Creature{
    private int hp;
    private int damage;
    private int range;
    private int posX;
    private int posY;
    private int moveSpeed;
    private int size;
    private int projAngle;

    private int initalHp;
    private boolean isAlive;
    private boolean canShoot;
    private boolean canMelee;
    private Raylib.Color color;
    private Jaylib.Vector2 pos;
    private final int SHOT_COOLDOWN = 500;
    private static final int MELEE_COOLDOWN = 1000;
    private Melee sword = new Melee(5,100,posX, posY, posY);



    public Player(int hp, int damage, int range, int posX, int posY, int moveSpeed, int size, Raylib.Color color) {
        this.hp = hp;
        initalHp = hp;
        this.damage = damage;
        this.range = range;
        this.posX = posX;
        this.posY = posY;
        this.moveSpeed = moveSpeed;
        this.size = size;
        this.isAlive = true;
        this.color = color;
        this.pos = new Jaylib.Vector2(posX,posY);;
        this.canShoot = true;
        this.canMelee = true;
    }

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
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT) && canShoot()) {
            DrawCircle(posX, posY, 10, PURPLE);
            setProjecitleDirection();
            Projectile proj = new Projectile(10, posX, posY, 10, projAngle);
            proj.setShotTage("Player");
            projList.add(proj);
            canShoot = false;
            cooldown(SHOT_COOLDOWN, "shot");


        }
        // Check the bounds of projectiles in the ProjectileHandler (likely to handle removal of out-of-bounds projectiles)
        projList.checkProjectilesBounds();
    }

    public void melee() {
        if (IsKeyDown(KEY_SPACE) && canMelee()) {
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

    public void setProjecitleDirection() {
        if (IsKeyDown(KEY_W) && IsKeyDown(KEY_A)) {
            projAngle = 315;
        } else if (IsKeyDown(KEY_W) && IsKeyDown(KEY_D)) {
            projAngle = 45;
        } else if (IsKeyDown(KEY_D) && IsKeyDown(KEY_S)) {
            projAngle = 135;
        } else if (IsKeyDown(KEY_A) && IsKeyDown(KEY_S)) {
            projAngle = 225;
        } else if (IsKeyDown(KEY_W)) {
            projAngle = 0;
        } else if (IsKeyDown(KEY_D)) {
            projAngle = 90;
        } else if (IsKeyDown(KEY_S)) {
            projAngle = 180;
        } else if (IsKeyDown(KEY_A)) {
            projAngle = 270;
        }
    }

        public void update(ProjectileHandler projList){
            move();
            createProjectile(projList);
            melee();
            sword.update();
            DrawCircle(posX, posY, size, color);
        }

    public boolean canShoot(){return canShoot;}

    public boolean canMelee() {return canMelee;}

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int getMoveSpeed() {
        return moveSpeed;
    }

    @Override
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public Raylib.Color getColor() {
        return color;
    }

    @Override
    public void setColor(Raylib.Color color) {
        this.color = color;
    }

    @Override
    public Jaylib.Vector2 getPos() {
        return pos;
    }

    @Override
    public void setPos(Jaylib.Vector2 pos) {
        this.pos = pos;
    }

    public int getProjAngle() {
        return projAngle;
    }

    public void setProjAngle(int projAngle) {
        this.projAngle = projAngle;
    }

    public int getInitalHp() {
        return initalHp;
    }

    public void setInitalHp(int initalHp) {
        this.initalHp = initalHp;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean isCanMelee() {
        return canMelee;
    }

    public void setCanMelee(boolean canMelee) {
        this.canMelee = canMelee;
    }

    public int getSHOT_COOLDOWN() {
        return SHOT_COOLDOWN;
    }

    public Melee getSword() {
        return sword;
    }

    public void setSword(Melee sword) {
        this.sword = sword;
    }
}
