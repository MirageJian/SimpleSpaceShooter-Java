package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import util.CMath;
import util.GameResource;
import util.Vector3f;

import java.util.concurrent.CopyOnWriteArrayList;

public class Fighter1 extends EnemyObject {
    private static GameResource resource = new GameResource("res/fighter1.png", 92, 66);
    private int bulletAngle = 0;
    private final static int sDefaultCd = 60;
    private int fireTimes;
    private int intensity;

    public Fighter1(int intensity) {
        super(resource,92, 66, 1_000, CMath.vectorByXYZ(0, -100, 0), 2000, sDefaultCd);
        setRotation(Math.toRadians(90));
        this.intensity = intensity;
        this.fireTimes = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        double angle = CMath.getAngle(this, player);
        if (cd <= 0 && fireTimes > 0) {
            EBulletList.add(new BulletObject(eBullet2, 45, 15, CMath.vectorByAngle(-150, angle), this.getNewCentre())
                    .setRotation(BulletObject.class, Math.toRadians(-angle + 180)));
            EBulletList.add(new BulletObject(eBullet2, 45, 15, CMath.vectorByAngle(-150, angle + 15), this.getNewCentre())
                    .setRotation(BulletObject.class, Math.toRadians(-angle + 180 - 15)));
            EBulletList.add(new BulletObject(eBullet2, 45, 15, CMath.vectorByAngle(-150, angle - 15), this.getNewCentre())
                    .setRotation(BulletObject.class, Math.toRadians(-angle + 180 + 15)));
        }
        if (cd > 0) cd -= intensity;
        else {
            cd = sDefaultCd;
            fireTimes --;
        }

    }
}
