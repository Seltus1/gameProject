package Creatures.Players.Warriors;

import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.EnemyHandler;
import Handlers.ProjectileHandler;
import com.raylib.Raylib;
import static com.raylib.Jaylib.*;

public class Paladin extends Player {
    private int maceCharge;
    private int intervalBetweenChargeUp;
    private CooldownHandler maceCharger;
    private boolean releaseMace;
    public Paladin(int hp, int damage, int meleeRange, int posX, int posY, int moveSpeed, int size, Raylib.Camera2D camera, Raylib.Color color) {
        super(hp, damage, meleeRange, posX, posY, moveSpeed, size, camera, color);
        maceCharger = new CooldownHandler();
        intervalBetweenChargeUp = 1000;
    }
    public void update(ProjectileHandler projList, Raylib.Camera2D camera, Raylib.Vector2 mousePos, EnemyHandler enemies){
        primary();

        //        Update this last
        super.update(projList,camera,mousePos,enemies);
    }
    public void primary(){
        if(canMelee() && !isUsingUtility() && !isUsingUltimate() && !isUsingSecondary()) {
            if(isMeleeing() && !didMelee()){
                setDidMelee(true);
            }
            if(didMelee()){
                if(IsMouseButtonDown(MOUSE_BUTTON_LEFT)){
                    if(maceCharger.cooldown(intervalBetweenChargeUp)){
                        maceCharge++;
                    }
                    if(maceCharge == 3){
                        releaseMace = true;
                    }
                }
                else if(maceCharge > 0){

                }
            }
        }
    }


}
