package gameObjects;

import settings.WeaponTypes;
import util.GameObject;
import util.GameResource;
import util.Point3f;

import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerObject extends GameObject {
    public LaserObject laserObject;
    public WeaponTypes currentWeapon = WeaponTypes.Laser;
    public static int bulletClodDown = 0;
    private int bulletLv = 1;
    // Shield lasting time, Extra width with shield
    private int shieldTime = 0;
    public final static int S_EXTRA_WIDTH = 40;
    // Death states: -1 is alive, >0 is dying, ==0 is dead
    public int dyingTime = -1;

    public PlayerObject(GameResource gameResource, int width, int height, Point3f centre) {
        super(gameResource, width, height, centre);
    }

    // Create Bullet method
    public void createBullet(CopyOnWriteArrayList<BulletObject> bullets, GameResource resource) {
        if (bulletClodDown == 0) {
            for (int i = 0; i < bulletLv; i++) {
                int angle = i * 5; // Vector angle is the direction of firing, angle is the direction of drawing
                bullets.add(new BulletObject(resource, 10, 18, this, 90 + angle)
                        .setRotation(BulletObject.class, Math.toRadians(-angle)));
                if (i != 0) {

                    bullets.add(new BulletObject(resource, 10, 18, this, 90 - angle)
                            .setRotation(BulletObject.class, Math.toRadians(angle)));
                }
            }
//            switch (bulletLv) {
//                case 1:
//                    bullets.add(new BulletObject(resource, 10, 18, this, BulletDirection.Middle));
//                    break;
//                case 2:
//                    bullets.add(new BulletObject(resource, 10, 18, this, BulletDirection.Middle));
//                    bullets.add(new BulletObject(resource, 15, 18, this, BulletDirection.LeftMiddle));
//                    bullets.add(new BulletObject(resource, 15, 18, this, BulletDirection.RightMiddle));
//                    break;
//                case 3:
//                    bullets.add(new BulletObject(resource, 10, 18, this, BulletDirection.Middle));
//                    bullets.add(new BulletObject(resource, 15, 18, this, BulletDirection.LeftMiddle));
//                    bullets.add(new BulletObject(resource, 15, 18, this, BulletDirection.RightMiddle));
//                    bullets.add(new BulletObject(resource, 18, 18, this, BulletDirection.Left));
//                    bullets.add(new BulletObject(resource, 18, 18, this, BulletDirection.Right));
//                    break;
//                default:
//                    break;
//            }
            bulletClodDown = 10;
        }
    }
    // There is max level for bullets
    public void bulletUpgrade() {
        if (bulletLv < 10) bulletLv++;
    }

    public boolean isShield() {
        return shieldTime > 0;
    }

    public void setShieldTime(int shieldTime) {
        this.shieldTime = shieldTime;
    }

    public void weakenShield() {
        if (shieldTime > 0) shieldTime--;
    }

    // Death methods
    public boolean isDying() {
        return dyingTime > 0;
    }

    public boolean isDead() {
        return dyingTime == 0;
    }

    public void minusDyingTime() {
        if (this.dyingTime > 0) dyingTime--;
    }
}
