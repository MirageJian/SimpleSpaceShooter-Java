package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import util.CMath;
import util.GameResource;

import java.util.concurrent.CopyOnWriteArrayList;

public class PodCharged extends EnemyObject {
    private static GameResource resource = new GameResource("res/enemypodcharged.png", 127, 128);
    private int bulletAngle = 0;
    private final static int sDefaultCd = 1200;
    private int intensity;

    public PodCharged(int intensity) {
        super(resource,127, 128, 5_000 * intensity, CMath.vectorByXYZ(0, -10, 0), 5_000, sDefaultCd);
        setAngularV(Math.toRadians(30));
        this.intensity = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        if (cd < 800 && cd > 0 && cd % 4 == 0) {
            if (bulletAngle == 0) bulletAngle = (int)CMath.getAngle(this, player);
            EBulletList.add(new BulletObject(energyBallY, 20, 20, CMath.vectorByAngle(-100 * intensity, bulletAngle), this.getNewCentre()));
            bulletAngle += 20;
        }
        if (cd > 860 && cd % 90 == 0) {
            bulletAngle = (int)CMath.getAngle(this, player);
            EBulletList.add(new BulletObject(energyBallY, 20, 20, CMath.vectorByAngle(-100 * intensity, bulletAngle), this.getNewCentre()));
        }
        if (cd < 0) cd = sDefaultCd;
        cd -= 1;
    }
}
