package gameObjects;

import util.GameObject;
import util.GameResource;
import util.Point3f;

// Player weapon object: Laser, Single object
public class LaserObject extends GameObject {
    public int damage = 1; // Per frame
    public boolean isOn = true;
    private int laserLv = 1;

    private EnemyObject contactObject;
    public LaserObject(GameResource gameResource, int width, int height, Point3f centre) {
        super(gameResource, width, height, centre);
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    // Weapon upgrade method
    public void laserUpgrade() {
        if (laserLv > 3) return;
        damage *= 2;
        setWidth(getWidth() * 2);
        laserLv ++;
    }
    public EnemyObject getContactObject() {
        return contactObject;
    }

    public void setContactObject(EnemyObject contactObject) {
        this.contactObject = contactObject;
    }
}
