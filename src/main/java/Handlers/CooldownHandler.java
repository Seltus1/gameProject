package Handlers;
import static com.raylib.Raylib.*;
import java.lang.Math;
public class CooldownHandler {
    int currentFrame;
    int frameRate;

    public CooldownHandler(){
        this.frameRate = 60;
    }

    public boolean cooldown(double milliSeconds){
        currentFrame++;
        double frames = milliSecondsToFrames(milliSeconds);
        int framesRounded = (int) Math.round(frames);
        if(framesRounded <= 0){
            currentFrame = 0;
            return true;
        }
        if(currentFrame % framesRounded == 0){
            currentFrame = 0;
            return true;
        }
        return false;
    }
        public void resetCooldown(){
            currentFrame = 0;
        }
    private double milliSecondsToFrames(double milliSeconds){
        return (milliSeconds/(1000.00)) * frameRate;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }
    public double getCurrentFrameInMilliSeconds(){
        return ((double) currentFrame / 60) * 1000;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }
}


