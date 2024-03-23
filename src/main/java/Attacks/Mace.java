package Attacks;

import Creatures.Enemies.Enemy;
import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.EnemyHandler;
import Handlers.HealthHandler;
import Handlers.VectorHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import static com.raylib.Jaylib.*;


public class Mace {
    private CooldownHandler attackingTime;

    private int ammtOfTimeExtending;
    private VectorHandler maceVectorHandler;
    private int speed;
    private boolean extendMace;
    private boolean retractMace;
    private int charge;
    private int damage;
    private boolean didStartExtending;
//    private boolean
    private int posX;
    private int posY;
    private Raylib.Vector2 position;
    private Raylib.Vector2 currMousePos;
    private float maceExtendX;
    private float maceExtendY;
    private boolean isExtended;
    private int size;
    private HealthHandler enemyHp;
    private int initialSpeed;
    private int retractionSpeed;

    public Mace(Player player, Raylib.Camera2D camera){
        attackingTime = new CooldownHandler();
        maceVectorHandler = new VectorHandler(player.getPosX(),player.getPosY(),speed,camera);
        position = new Raylib.Vector2(new Jaylib.Vector2(posX,posY));
        size = 20;
        enemyHp = new HealthHandler();
        retractionSpeed = 10;
    }
    public void update(Raylib.Vector2 mousePos, Raylib.Camera2D camera, Player player, EnemyHandler enemies){
        if(extendMace){
            extend(mousePos, camera);
        }
        if(retractMace){
            retract(player, camera);
        }
        if(!isExtended()){
            circlePlayerWhileRetracted(player);
        }
        else{
            dealingDamage(enemies);

        }
        DrawCircle(maceVectorHandler.getPosX(),maceVectorHandler.getPosY(),size,BLACK);
    }
    public void extend(Raylib.Vector2 mousePos, Raylib.Camera2D camera){

        if(!didStartExtending) {
            chargeAmmtToStats(charge);
            initialSpeed = speed;
            currMousePos = mousePos;
//            endOfChargeLocation = getVector().findIntersectingPointOnCircleAndMousePos(getPosition(), 1000000, mousePos);
            maceExtendX = mousePos.x();
            maceExtendY = mousePos.y();
            maceVectorHandler.setShotPosition(position);
            didStartExtending = true;
            maceVectorHandler.setShotPosition(new Jaylib.Vector2(maceExtendX,maceExtendY));
            maceVectorHandler.setStraightLine(camera);
            isExtended = true;

        }
        else {
            maceVectorHandler.setMoveSpeed(speed);
            DrawText("" + speed,maceVectorHandler.getPosX(), maceVectorHandler.getPosY() - 100,30,GREEN);
//            currMousePos = maceVectorHandler.findEndPointOfLine(player.getPosition(),100000,currMousePos);
            maceVectorHandler.updateShootLinePosition(camera);
            if (attackingTime.cooldown(ammtOfTimeExtending) || speed == 0) {
                setExtendMace(false);
                didStartExtending = false;
                setCharge(0);
//                maceVectorHandler.setMoveSpeed(0);
                attackingTime.resetCooldown();
            }

        }
    }
    public void retract(Player player, Camera2D camera){
        maceVectorHandler.setMoveSpeed(retractionSpeed);
        maceVectorHandler.moveObject(player.getPosition(),"to", camera);
        if(CheckCollisionCircles(maceVectorHandler.getPosition(),size,player.getPosition(),player.getSize())){
            isExtended = false;
            retractMace = false;
        }
//        System.out.println(maceVectorHandler.getPosX() );
    }
    public void circlePlayerWhileRetracted(Player player){
        maceVectorHandler.circlePlayer(player,10);
    }
    private void chargeAmmtToStats(int charge){
        if(charge == 1){
            ammtOfTimeExtending = 1000;
            speed = 5;
            damage = 10;
        }
        else if(charge == 2){
            ammtOfTimeExtending = 700;
            speed = 7;
            damage = 20;
        }
        else{
            ammtOfTimeExtending = 500;
            speed = 10;
            damage = 30;
        }
    }
    private void dealingDamage(EnemyHandler enemies){
        for(int i = 0; i < enemies.size(); i++){
            Enemy enemy = (Enemy) enemies.get(i);
            if(CheckCollisionCircles(maceVectorHandler.getPosition(),size,enemy.getPos(), enemy.getSize())){
                enemyHp.dealDamageToEnemy(enemy, damage);
                if(charge == 1){
                    speed = 0;
                }
                else if(charge == 2){
                    speed = speed / 2;
                }
            }
        }
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public boolean isExtendMace() {
        return extendMace;
    }

    public void setExtendMace(boolean extendMace) {
        this.extendMace = extendMace;
    }

    public boolean isRetractMace() {
        return retractMace;
    }

    public void setRetractMace(boolean retractMace) {
        this.retractMace = retractMace;
    }

    public boolean isExtended() {
        return isExtended;
    }

    public void setExtended(boolean extended) {
        isExtended = extended;
    }

    public boolean isDidStartExtending() {
        return didStartExtending;
    }

    public void setDidStartExtending(boolean didStartExtending) {
        this.didStartExtending = didStartExtending;
    }
}
