package Attacks;

import Creatures.Creature;
import Creatures.Players.Player;
import Handlers.CooldownHandler;
import Handlers.VectorHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;

public class Sword {
    private int damage;
    private int drawCounter;
    private boolean isDrawingVertical;
    private boolean isDrawingHoriztonal;
    private CooldownHandler drawCooldown;
    private VectorHandler vector;

    public Sword(int damage, Camera2D camera) {
        this.damage = damage;
        isDrawingVertical = false;
        isDrawingHoriztonal = false;
        drawCounter = 0;
        drawCooldown = new CooldownHandler();
        vector = new VectorHandler(0,0,0, camera);
    }

    public void attack(Creature creature, Player player) {
        creature.setHp(creature.getHp() - damage);
        if (creature instanceof Player){
            Player player1 = (Player) creature;
            player1.getRegenCooldown().setCurrentFrame(0);
        }
    }


    public void drawSword(Player player, Raylib.Vector2 mousePos){
        if(player.canMelee()) {
            Raylib.Vector2[] trianglePos = calculateTriangle(player,mousePos);
            DrawTriangle(trianglePos[0], trianglePos[1], trianglePos[2], BLACK);
            DrawTriangle(trianglePos[0], trianglePos[2], trianglePos[1], BLACK);
        }
    }
    public Raylib.Vector2[] calculateTriangle(Player player, Raylib.Vector2 mousePos){
        Raylib.Vector2 endPoint = vector.findEndPointOfLine(player.getPosition(),player.getRange(),mousePos);
        double[] poses = vector.findIntersectingPoints(player.getPosition(),endPoint,player.getRange(),50, mousePos);
        Raylib.Vector2 p1 = new Jaylib.Vector2((int)poses[0],(int)poses[1]);
        Raylib.Vector2 p2 = new Jaylib.Vector2((int)poses[2],(int)poses[3]);
        return new Raylib.Vector2[] {player.getPosition(), p1,p2};
    }
}
