package gameObjects;

import settings.BulletDirection;
import settings.WeaponTypes;
import util.GameObject;
import util.GameResource;
import util.Point3f;

import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerObject extends GameObject {
    public LaserObject laserObject;
    public static int bulletClodDown = 0;
    public final static int S_EXTRA_WIDTH = 40;
    public WeaponTypes currentWeapon = WeaponTypes.Laser;
    private int bulletLv = 1;
    private int shieldTime = 0;

    public PlayerObject(GameResource gameResource, int width, int height, Point3f centre) {
        super(gameResource, width, height, centre);
    }

    // Create Bullet method
    public void createBullet(CopyOnWriteArrayList<BulletObject> bullets, GameResource resource) {
        if (bulletClodDown == 0) {
            switch (bulletLv) {
                case 1:
                    bullets.add(new BulletObject(resource, 10, 18, this, BulletDirection.Middle));
                    break;
                case 2:
                    bullets.add(new BulletObject(resource, 10, 18, this, BulletDirection.Middle));
                    bullets.add(new BulletObject(resource, 15, 18, this, BulletDirection.LeftMiddle));
                    bullets.add(new BulletObject(resource, 15, 18, this, BulletDirection.RightMiddle));
                    break;
                case 3:
                    bullets.add(new BulletObject(resource, 10, 18, this, BulletDirection.Middle));
                    bullets.add(new BulletObject(resource, 15, 18, this, BulletDirection.LeftMiddle));
                    bullets.add(new BulletObject(resource, 15, 18, this, BulletDirection.RightMiddle));
                    bullets.add(new BulletObject(resource, 18, 18, this, BulletDirection.Left));
                    bullets.add(new BulletObject(resource, 18, 18, this, BulletDirection.Right));
                    break;
                default:
                    break;
            }
            bulletClodDown = 10;
        }
    }

    public void bulletUpgrade() {
        if (bulletLv < 3) bulletLv++;
    }
    public boolean isShield() {
        return shieldTime > 0;
    }
    public void setShieldTime(int shieldTime) {
        this.shieldTime = shieldTime;
    }
    public void weakenShield () {
        if (shieldTime > 0) shieldTime --;
    }
}
