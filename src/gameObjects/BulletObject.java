package gameObjects;

import settings.GlobalConst;
import util.*;

// Player weapon object: Bullet
// Enemy bullet
public class BulletObject extends GameObject {
    public int bulletDamage = 20; // Per Bullet
    private Vector3f bulletVector;
    private int textureStart = 0;
    // Enemy bullets
    public BulletObject(GameResource gameResource, int width, int height, Vector3f vector, Point3f centre) {
        super(gameResource, width, height, null);
        setCentre(centre);
        this.bulletVector = vector;
    }
    // Player bullets instances
    public BulletObject(GameResource gameResource, int width, int height, PlayerObject player, double vectorAngle) {
        super(gameResource, width, height, null);
        setCentre(player.getNewCentre());
        this.bulletVector = CMath.vectorByAngle(300, vectorAngle);
        textureStart = 33;
        resource.drawWidth = 10;
    }

    public void applyVector() {
        getCentre().ApplyVector(bulletVector);
    }
    public boolean isOnBoundary() {
        return getCentre().getY() <= 0 || getCentre().getX() <= 0 || getCentre().getX() >= GlobalConst.LAYOUT_WIDTH || getCentre().getY() >= GlobalConst.LAYOUT_HEIGHT;
    }
    public int getTextureStart() {
        return textureStart;
    }
}
