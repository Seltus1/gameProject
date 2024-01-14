package Handlers;
import static com.raylib.Raylib.*;
public class CooldownHandler {
    int currentFrame;
    int frameRate;

    public CooldownHandler(){
        this.frameRate = 60;

    }

    public boolean cooldown(double milliSeconds){
        currentFrame++;
        double frames = milliSecondsToFrames(milliSeconds);
        if(currentFrame % frames == 0){
            currentFrame = 0;
            return true;
        }
        return false;
    }
    private double milliSecondsToFrames(double milliSeconds){
        return (milliSeconds/(1000.00)) * frameRate;
    }
}
