import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;
public class Footstep {
    private int posX;
    private int posY;
    private int lifeTime;
    private long timeFirstDrawn;

    public Footstep(int posX, int posY, int lifeTime){
        this.posX = posX;
        this.posY = posY;
        this.lifeTime = lifeTime;
        timeFirstDrawn = System.currentTimeMillis();
    }

    public void draw(){
//        DrawText(""+ (System.currentTimeMillis() - timeFirstDrawn), 200,200,20,BLACK);
        if(System.currentTimeMillis() - timeFirstDrawn <= lifeTime){
            DrawEllipse(posX,posY,5,10,GRAY);
        }
    }
}
