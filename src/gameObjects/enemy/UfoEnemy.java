package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import util.CMath;
import util.GameResource;

import java.util.concurrent.CopyOnWriteArrayList;

public class UfoEnemy extends EnemyObject {
    private static GameResource ufoResource = new GameResource("res/UFO.png", 32, 32);
    private int intensity;
    private static int defaultCd = 120;
    private int fireTimes;

    public UfoEnemy(int intensity) {
        super(ufoResource, 50, 50, 100 * intensity, CMath.vectorByXYZ(0,-80,0), 1000, defaultCd);
        setAngularV(Math.toRadians(90));
        this.intensity = intensity;
        fireTimes = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        int angle = (int)CMath.getAngle(this, player);
        if (cd <= 0 && fireTimes > 0) EBulletList.add(new BulletObject(energyBall, 15, 15, CMath.vectorByAngle(-100*intensity,angle), this.getNewCentre()));
        if (cd > 0) cd -= intensity;
        else {
            cd = defaultCd;
            fireTimes --;
        }
    }
}
