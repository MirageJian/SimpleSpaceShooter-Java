package gameObjects;

import gameObjects.enemy.EnemyObject;
import util.GameObject;
import util.GameResource;
import util.Point3f;

// Player weapon object: Laser, Single object
public class LaserObject extends GameObject {
    public int damage = 5; // Per frame
    public boolean isOn = true;
    private int laserLv = 1;

    private EnemyObject contactObject;
    public LaserObject(GameResource gameResource, int width, int height, PlayerObject player) {
        super(gameResource, width, height, null);
        this.setCentre(player.getCentre());
        player.laserObject = this;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    // Weapon upgrade method
    public void laserUpgrade() {
        if (laserLv < 3) {
            damage = damage * 2;
            setWidth(getWidth() * 2);
            laserLv ++;
        }
    }
    public EnemyObject getContactObject() {
        return contactObject;
    }

    public void setContactObject(EnemyObject contactObject) {
        this.contactObject = contactObject;
    }
}
