package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import util.CMath;
import util.GameResource;

import java.util.concurrent.CopyOnWriteArrayList;

public class Hammer extends EnemyObject {
    private static GameResource resource = new GameResource("res/hummer.png", 95, 96);
    private final static int sDefaultCd = 600;
    private int intensity;

    public Hammer(int intensity) {
        super(resource,95, 96, 1_000 * intensity, CMath.vectorByXYZ(0, -50, 0), 10_000* intensity, sDefaultCd);
        this.intensity = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        double angle = CMath.getAngle(this, player);
        if (cd > 200 && cd < 400)
            createLine(EBulletList, angle, cd);
//        if (cd < 800 && cd > 0) {
//            if (bulletAngle == 0) bulletAngle = (int)CMath.getAngle(this, player);
//            EBulletList.add(new BulletObject(energyBallB, 20, 20, CMath.vectorByAngle(-100, bulletAngle), this.getNewCentre()));
//            bulletAngle += 10;
//        }
        if (cd < 0) cd = sDefaultCd;
        cd -= 1;
    }

    protected void flyAndPause(double angle) {
        // Towards player when pausing
        setRotation(Math.toRadians(angle));
    }

    private void createLine(CopyOnWriteArrayList<BulletObject> EBulletList, double angle, int cd) {
        if (cd % 5 == 0) EBulletList.add(new BulletObject(energyBallG, 20, 20, CMath.vectorByAngle(-150 * intensity, angle), this.getNewCentre()));
    }
}
