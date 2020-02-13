package gameObjects;

import settings.BulletDirection;
import settings.GlobalConst;
import util.*;

// Player weapon object: Bullet
// Enemy bullet
public class BulletObject extends GameObject {
    public int bulletDamage = 20; // Per Bullet
    private Vector3f bulletVector;
    // Enemy bullets
    public BulletObject(GameResource gameResource, int width, int height, Vector3f vector, Point3f centre) {
        super(gameResource, width, height, null);
        setCentre(centre);
        this.bulletVector = vector;
    }
    // Player bullets instances
    public BulletObject(GameResource gameResource, int width, int height, PlayerObject player, BulletDirection bd) {
        super(gameResource, width, height, null);
        setCentre(player.getNewCentre());
        this.bulletVector = getVector(bd);
        switch (bd) {
            case Left:
                resource.textureStart = 0;
                resource.drawWidth = 18;
                break;
            case LeftMiddle:
                resource.textureStart = 18;
                resource.drawWidth = 15;
                break;
            case Middle:
                resource.textureStart = 33;
                resource.drawWidth = 10;
                break;
            case RightMiddle:
                resource.textureStart = 43;
                resource.drawWidth = 15;
                break;
            case Right:
                resource.textureStart = 58;
                resource.drawWidth = 18;
                break;
        }
    }

    public void applyVector() {
        getCentre().ApplyVector(bulletVector);
    }
    public boolean isOnBoundary() {
        return getCentre().getY() <= 0 || getCentre().getX() <= 0 || getCentre().getX() >= GlobalConst.LAYOUT_WIDTH || getCentre().getX() >= GlobalConst.LAYOUT_HEIGHT;
    }
    // Bullets vector
    private static Vector3f getVector(BulletDirection bd) {
        int speed = 300;
        switch (bd) {
            case Left:
                return CMath.vectorByAngle(speed, 135);
            case LeftMiddle:
                return CMath.vectorByAngle(speed, 112.5);
            case RightMiddle:
                return CMath.vectorByAngle(speed, 67.5);
            case Right:
                return CMath.vectorByAngle(speed, 45);
            case Middle:
            default:
                return GlobalConst.getVectorPerFrame(new Vector3f(0, speed, 0));
        }
    }
}
