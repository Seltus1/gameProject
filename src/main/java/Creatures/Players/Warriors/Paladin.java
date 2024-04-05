package Creatures.Players.Warriors;

import Attacks.Mace;
import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.EnemyHandler;
import Handlers.GameHandler;
import Handlers.ProjectileHandler;
import com.raylib.Raylib;
import static com.raylib.Jaylib.*;

public class Paladin extends Player {
    private Mace mace;
    private int intervalBetweenChargeUp;
    private CooldownHandler maceCharger;
    private boolean releaseMace;
    public Paladin(int hp, int damage, int meleeRange, int posX, int posY, int moveSpeed, int size, Raylib.Camera2D camera, Raylib.Color color) {
        super(hp, damage, meleeRange, posX, posY, moveSpeed, size, camera, color);
        maceCharger = new CooldownHandler();
        intervalBetweenChargeUp = 500;
        mace = new Mace(this,camera);
        setDefence(60);

    }
    public void update(ProjectileHandler projList, Raylib.Camera2D camera, Raylib.Vector2 mousePos, EnemyHandler enemies, GameHandler game){
        mace.update(mousePos,camera, this, enemies);
        primary();
        secondary();
        DrawText("" + mace.getCharge(),getPosX(),getPosY(),30,BLACK);


        //        Update this last
        super.update(projList,camera,mousePos,enemies, game);
    }
    public void primary(){
        if(canMelee() && !isUsingUtility() && !isUsingUltimate() && !isUsingSecondary()) {
            if(isMeleeing() && !didMelee() && !mace.isExtended()){
                setDidMelee(true);
            }
            if(didMelee() && !mace.isExtended()){
                if(IsMouseButtonDown(MOUSE_BUTTON_LEFT)){
                    if(maceCharger.cooldown(intervalBetweenChargeUp)){
                        mace.setCharge(mace.getCharge()+ 1);
                    }
                    if(mace.getCharge() == 3){
                        mace.setExtendMace(true);

                    }
                }
                else if(mace.getCharge() > 0){
                    mace.setExtendMace(true);
                }
            }
        }
    }
    public void secondary(){
        if(mace.isExtended() && !mace.isDidStartExtending() && IsMouseButtonPressed(MOUSE_BUTTON_RIGHT)){
            mace.setRetractMace(true);
        }
    }


}
