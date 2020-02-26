package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import util.CMath;
import util.GameResource;

import java.util.concurrent.CopyOnWriteArrayList;

public class Hammer extends EnemyObject {
    private static GameResource resource = new GameResource("res/enemypodcharged.png", 127, 128);
    private static GameResource bulletRes = new GameResource("res/energyball.png", 44, 43);
    private int bulletAngle = 0;
    private final static int sDefaultCd = 1200;
    private int intensity;

    public Hammer(int intensity) {
        super(resource,127, 128, 5_000, CMath.vectorByXYZ(0, -10, 0), 10_000, sDefaultCd);
        setAngularV(Math.toRadians(30));
        this.intensity = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        if (cd < 800 && cd > 0) {
            if (bulletAngle == 0) bulletAngle = (int)CMath.getAngle(this, player);
            EBulletList.add(new BulletObject(bulletRes, 20, 20, CMath.vectorByAngle(-100, bulletAngle), this.getNewCentre()));
            bulletAngle += 10;
        }
        if (cd < 0) cd = sDefaultCd;
        cd -= 1;
    }
}
