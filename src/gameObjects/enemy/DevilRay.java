package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import util.CMath;
import util.GameResource;

import java.util.concurrent.CopyOnWriteArrayList;

public class DevilRay extends EnemyObject {
    private static GameResource resource = new GameResource("res/devilray.png", 162, 128);
    private final static int sDefaultCd = 4800;
    private int intensity;

    public DevilRay(int intensity) {
        super(resource,162, 128, 50_000 * intensity, CMath.vectorByXYZ(-20, -10, 0), 100_000, sDefaultCd);
//        setAngularV(Math.toRadians(30));
        this.intensity = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        double angle = CMath.getAngle(this, player);
        if (cd < 300 && (cd + 75) % 60 == 0) {
            for (int i = 1; i < 2; i ++) createBullets(EBulletList, angle, i, 2);
        }
        if (cd < 600 && (cd + 60) % 120 == 0) {
            EBulletList.add(new BulletObject(energyBallB, 20, 20, CMath.vectorByAngle(-150 * intensity, angle), this.getNewCentre())
                    .setRotation(BulletObject.class, Math.toRadians(-angle + 180)));
            for (int i = 1; i < 2; i ++) createBullets(EBulletList, angle, i, 0);
        }
        if (cd < 1200 && (cd + 45) % 120 == 0) {
            for (int i = 1; i < 3; i ++) createBullets(EBulletList, angle, i, 2);
        }
        if (cd < 1800 && (cd + 30) % 120 == 0) {
            EBulletList.add(new BulletObject(energyBallB, 20, 20, CMath.vectorByAngle(-150 * intensity, angle), this.getNewCentre())
                    .setRotation(BulletObject.class, Math.toRadians(-angle + 180)));
            for (int i = 1; i < 3; i ++) createBullets(EBulletList, angle, i, 0);
        }
        if (cd < 2400 && (cd + 15) % 120 == 0) {
            for (int i = 1; i < 4; i ++) createBullets(EBulletList, angle, i, 2);
        }
        if (cd < 3600 && cd % 120 == 0) {
            EBulletList.add(new BulletObject(energyBallB, 20, 20, CMath.vectorByAngle(-150 * intensity, angle), this.getNewCentre())
                    .setRotation(BulletObject.class, Math.toRadians(-angle + 180)));
            for (int i = 1; i < 4; i ++) createBullets(EBulletList, angle, i, 0);
        }
        if (cd > 3600 && cd % 20 == 0) {
            EBulletList.add(new BulletObject(lBullet, 45, 15, CMath.vectorByAngle(-150 * intensity, angle), this.getNewCentre())
                    .setRotation(BulletObject.class, Math.toRadians(-angle + 180)));
        }
        if (cd < 0) cd = sDefaultCd;
        cd -= 1;
        // Part of control enemy move on x axis
        flyAndPause(angle);
    }
    // Create arc sector bullets
    private void createBullets(CopyOnWriteArrayList<BulletObject> EBulletList, double angle, int i, int offset) {
        EBulletList.add(new BulletObject(energyBallB, 20, 20, CMath.vectorByAngle(-150 * intensity, angle + 4 * i - offset), this.getNewCentre()));
        EBulletList.add(new BulletObject(energyBallB, 20, 20, CMath.vectorByAngle(-150 * intensity, angle - 4 * i + offset), this.getNewCentre()));
    }
}
