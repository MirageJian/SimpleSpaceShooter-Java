package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import util.CMath;
import util.GameResource;

import java.util.concurrent.CopyOnWriteArrayList;

public class Drone extends EnemyObject {
    private static GameResource resource = new GameResource("res/drone.png", 65, 67);
    private final static int defaultCd = 60;
    private int intensity;
    private int fireTimes;

    public Drone(int intensity) {
        super(resource,65, 67, 500 * intensity, CMath.vectorByXYZ(0, -200, 0), 3_000, defaultCd);
        this.intensity = intensity;
        fireTimes = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        int angle = (int)CMath.getAngle(this, player);

        if (cd <= 0 && fireTimes > 0)
            EBulletList.add(new BulletObject(energyBallG, 20, 20, CMath.vectorByAngle(-300*intensity,angle), this.getNewCentre()));

        if (cd > 0) cd -= intensity;
        else {
            cd = defaultCd;
            fireTimes --;
        }
    }
}
