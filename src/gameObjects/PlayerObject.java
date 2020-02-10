package gameObjects;

import settings.GlobalConst;
import settings.WeaponTypes;
import util.GameObject;
import util.GameResource;
import util.Point3f;

import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerObject extends GameObject {
    public WeaponTypes currentWeapon = WeaponTypes.Laser;
    public int bulletDamage = 50; // Per Bullet
    private int bulletLv = 1;

    public PlayerObject(GameResource gameResource, int width, int height, Point3f centre) {
        super(gameResource, width, height, centre);
    }
    // Create Bullet method
    public void createBullet(CopyOnWriteArrayList<BulletObject> bullets, GameResource resource) {
        switch (bulletLv){
            case 1:
                bullets.add(new BulletObject(resource, 10, 18, this, GlobalConst.sBulletMov));
                break;
            case 2:
                bullets.add(new BulletObject(resource, 10, 18, this, GlobalConst.sBulletMov));
                bullets.add(new BulletObject(resource, 15, 18, this, GlobalConst.sBulletMov1L));
                bullets.add(new BulletObject(resource, 15, 18, this, GlobalConst.sBulletMov1R));
                break;
            case 3:
                bullets.add(new BulletObject(resource, 10, 18, this, GlobalConst.sBulletMov));
                bullets.add(new BulletObject(resource, 15, 18, this, GlobalConst.sBulletMov1L));
                bullets.add(new BulletObject(resource, 15, 18, this, GlobalConst.sBulletMov1R));
                bullets.add(new BulletObject(resource, 18, 18, this, GlobalConst.sBulletMov2L));
                bullets.add(new BulletObject(resource, 18, 18, this, GlobalConst.sBulletMov2R));
                break;
            default:
                break;
        }
    }

    public void bulletUpgrade() {
        bulletLv ++;
    }

}
