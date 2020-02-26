package gameObjects;

import gameObjects.enemy.EnemyObject;
import util.GameObject;
import util.GameResource;
import util.Point3f;

// Player weapon object: Laser, Single object
public class LaserObject extends GameObject {
    public boolean isOn = false;
    private int laserLv = 1;

    public LaserObject(GameResource gameResource, int width, int height, PlayerObject player) {
        super(gameResource, width, height, null);
        this.setCentre(player.getCentre());
    }

    // Weapon upgrade method
    public void laserUpgrade() {
        int maxLv = 10;
        if (laserLv < maxLv) {
            laserLv++;
            setWidth((int)(1.26 * getWidth()));
        }
    }

    public int getDamage() {
        // Per frame, get final laser damage
        return 6 * laserLv;
    }
}
