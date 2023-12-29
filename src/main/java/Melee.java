
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
//TEST
//TESTING AGAIN
public class Melee {
    private int attackSpeed;
    private int range;
    private int damage;
    private int posX;
    private int posY;
    private boolean draw;
    private int lifeTime;


    public Melee(int attackSpeed, int range, int damage, int posX, int posY){
        this.attackSpeed = attackSpeed;
        this.range = range;
        this.damage = damage;
        this.posX = posX;
        this.posY = posY;
        lifeTime = 10000;
        draw = false;
    }
    public void attack(){
        draw = true;
        drawingTime();
    }
    public void setPosX(int posX){
        this.posX = posX;
    }
    public void setPosY(int posY){
        this.posY = posY;
    }
    public void drawingTime() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(lifeTime);
            } catch (InterruptedException e) {
                System.out.println("got interrupted!");
            }
            draw = false;
            executor.shutdown();
        });
    }

    public void update(){
        if(draw) {
            DrawRectangle(this.posX, this.posY, 10, range, BLACK);
        }
    }
}