package Attacks;

import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.VectorHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class Mace {
    private CooldownHandler attackingTime;
    private int ammtOfTimeExtending;
    private VectorHandler maceVectorHandler;
    private int speed;
    private boolean extendMace;
    private boolean retractMace;
    private boolean didStartExtending;
//    private boolean
    private int posX;
    private int posY;
    private Raylib.Vector2 position;

    public Mace(Player player, Raylib.Camera2D camera){
        attackingTime = new CooldownHandler();
        maceVectorHandler = new VectorHandler(player.getPosX(),player.getPosY(),speed,camera);
        position = new Raylib.Vector2(new Jaylib.Vector2(posX,posY));
    }
    public void update(int charge, Raylib.Vector2 mousePos, Raylib.Camera2D camera){
        if(extendMace){
            extend(charge, mousePos, camera);
        }
        if(retractMace){
//            retract code here
        }
    }
    public void extend(int charge, Raylib.Vector2 mousePos, Raylib.Camera2D camera){
        if(!didStartExtending) {
            chargeAmmtToStats(charge);
            maceVectorHandler.setShotPosition(position);
            didStartExtending = true;
        }
        else{
            maceVectorHandler.updateShootLinePosition(camera);
        }


    }
    private void chargeAmmtToStats(int charge){
        if(charge == 1){
            ammtOfTimeExtending = 1000;
            speed = 5;
        }
        else if(charge == 2){
            ammtOfTimeExtending = 1500;
            speed = 10;
        }
        else{
            ammtOfTimeExtending = 2000;
            speed = 15;
        }
    }

}
