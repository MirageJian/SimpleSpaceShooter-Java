package gameObjects;

import util.GameObject;
import util.GameResource;
import util.Point3f;
import util.Vector3f;

import java.util.concurrent.CopyOnWriteArrayList;

// Player weapon object: Bullet
// Enemy bullet
public class BulletObject extends GameObject {
    private Vector3f bulletVector;

    public BulletObject(GameResource gameResource, int width, int height, PlayerObject player, Vector3f bulletVector) {
        super(gameResource, width, height, new Point3f(player.getCentre().getX(), player.getCentre().getY(), 0.0f));
        this.bulletVector = bulletVector;
    }

    public void applyVector() {
        getCentre().ApplyVector(bulletVector);
    }

}
