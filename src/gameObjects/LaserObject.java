package gameObjects;

import gameObjects.enemy.EnemyObject;
import util.GameObject;
import util.GameResource;
import util.Point3f;

// Player weapon object: Laser, Single object
public class LaserObject extends GameObject {
    public boolean isOn = false;
    private int laserLv = 1;
    private final static int MAX_LV = 16;

    public LaserObject(GameResource gameResource, int width, int height, PlayerObject player) {
        super(gameResource, width, height, null);
        this.setCentre(player.getCentre());
    }

    // Weapon upgrade method
    public void laserUpgrade() {

        if (laserLv < MAX_LV) {
            laserLv++;
            setWidth((int)(1.2 * getWidth()));
        }
    }

    public int getDamage() {
        // Per frame, get final laser damage
        return 6 * laserLv;
    }
}
