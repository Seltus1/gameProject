
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
//TEST
//TESTING AGAIN
public class MeleeAttack {
    private int damage;
    private int posX;
    private int posY;
    private boolean draw;
    private int lifeTime;
    private int cooldown;
    private boolean isMelee;
    private boolean shortTick;


    public MeleeAttack(int damage, int posX, int posY){
        this.damage = damage;
        this.posX = posX;
        this.posY = posY;
        lifeTime = 1000;
        draw = false;
        isMelee = false;
        cooldown = 10000;
        shortTick = true;
    }
    public void attack(Player player){
        if(!(isMelee)) {
            cooldown(cooldown, "else");
            if (shortTick) {
                shortTick = false;
                draw = true;
                cooldown(lifeTime, "draw");
            }
        }
            update();

    }
    public void setPosX(int posX){
        this.posX = posX;
    }
    public void setPosY(int posY){
        this.posY = posY;
    }
    public void cooldown(int cooldown, String type) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                isMelee = true;
                TimeUnit.MILLISECONDS.sleep(cooldown);
            } catch (InterruptedException e) {
                System.out.println("got interrupted!");
            }
            if(type.equals("draw")){
                draw = false;
                shortTick = true;
            }
            else{
                isMelee = false;
            }

            executor.shutdown();
        });
    }

    public void update(){
        if(draw) {
            DrawRectangle(this.posX, this.posY, 10, 100, BLACK);
        }
    }
}