package Attacks;
import Creatures.Players.Player;
import Handlers.EnemyHandler;
import Handlers.ProjectileHandler;
import Handlers.VectorHandler;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Shield {
    private VectorHandler vector;
    public Shield(Camera2D camera) {
        vector = new VectorHandler(0, 0, 0, camera);
    }

    public void update(Player player, Raylib.Vector2 mousePos, ProjectileHandler projList){
        defend(player, mousePos, projList);
    }

    public void defend(Player player, Raylib.Vector2 mousePos, ProjectileHandler projList){
        if (IsMouseButtonDown(MOUSE_BUTTON_RIGHT)){
            Raylib.Vector2 endPoint = vector.findIntersectingPointOnCircleAndMousePos(player.getPosition(),player.getRange() / 2, mousePos);
            double[] poses = vector.findIntersectingPoints(player.getPosition(),endPoint,player.getRange() / 2,35, mousePos);
            Raylib.Vector2 linePoint1 = new Raylib.Vector2(new Jaylib.Vector2((float) poses[0], (float) poses[1]));
            Raylib.Vector2 linePoint2 = new Raylib.Vector2(new Jaylib.Vector2((float) poses[2], (float) poses[3]));
            DrawLine((int)poses[0],(int)poses[1],(int)poses[2],(int)poses[3], BLACK);
            for (int j = 0; j < projList.size() ; j++) {
                Projectile projectile = (Projectile) projList.get(j);
                if (!projectile.isDraw()) {
                    continue;
                }
                if (vector.CheckCollisionBetweenLineAndCircle(linePoint1, linePoint2, projectile.getPosition(), projectile.getShotRad())) {
                    projList.removeObject(projectile);
                }
            }
        }
    }
}
