package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import util.CMath;
import util.GameResource;

import java.util.concurrent.CopyOnWriteArrayList;

public class DevilRay extends EnemyObject {
    private static GameResource resource = new GameResource("res/devilray.png", 127, 128);
    private int bulletAngle = 0;
    private final static int sDefaultCd = 1200;
    private int intensity;

    public DevilRay(int intensity) {
        super(resource,127, 128, 50_000, CMath.vectorByXYZ(0, -10, 0), 100_000, sDefaultCd);
        setAngularV(Math.toRadians(30));
        this.intensity = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        if (cd < 800 && cd > 0) {
            if (bulletAngle == 0) bulletAngle = (int)CMath.getAngle(this, player);
            EBulletList.add(new BulletObject(eBullet1, 20, 20, CMath.vectorByAngle(-100, bulletAngle), this.getNewCentre()));
            bulletAngle += 10;
        }
        if (cd < 0) cd = sDefaultCd;
        cd -= 1;
    }
}
