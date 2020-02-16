package gameObjects;

import gameObjects.enemy.EnemyObject;
import util.GameObject;
import util.GameResource;
import util.Point3f;

// Player weapon object: Laser, Single object
public class LaserObject extends GameObject {
    public boolean isOn = true;
    private int laserLv = 1;

    private EnemyObject contactObject;

    public LaserObject(GameResource gameResource, int width, int height, PlayerObject player) {
        super(gameResource, width, height, null);
        this.setCentre(player.getCentre());
        player.laserObject = this;
    }

    // Weapon upgrade method
    public void laserUpgrade() {
        int maxLv = 10;
        if (laserLv < maxLv) {
            laserLv++;
            setWidth((int)(1.26 * getWidth()));
        }
    }

    public EnemyObject getContactObject() {
        return contactObject;
    }

    public void setContactObject(EnemyObject contactObject) {
        this.contactObject = contactObject;
    }

    public int getDamage() {
        // Per frame, get final laser damage
        return 6 * laserLv;
    }
}
