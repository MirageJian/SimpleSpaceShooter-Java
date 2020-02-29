package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import settings.GlobalConst;
import util.CMath;
import util.GameResource;
import util.Point3f;

import java.util.concurrent.CopyOnWriteArrayList;

public class MechaBody extends EnemyObject {
    private static GameResource resource = new GameResource("res/mecha_body.png", 118, 160);
    private int bulletAngle = 0;
    private final static int sDefaultCd = 1200;
    private int intensity;

    public MechaBody(int intensity) {
        super(resource,118, 160, 100_000 * intensity, CMath.vectorByXYZ(-10, -20, 0), 1_000_000, sDefaultCd);
        this.intensity = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        double angle = CMath.getAngle(this, player);

        if (cd > 600 && cd < 800)
            createCircle(EBulletList, cd);
        if (cd > 400 && cd < 600)
            createArcSector(EBulletList, angle);
        if (cd < 0) cd = sDefaultCd;
        cd -= 1;

        flyAndPause(angle);
    }

    private void createCircle(CopyOnWriteArrayList<BulletObject> EBulletList, int cd) {
        if (cd % 30 == 0)
            for (int i = 0; i < 360; i += 6) {
                EBulletList.add(new BulletObject(energyBallR, 20, 20, CMath.vectorByAngle(-150 * intensity, i), this.getNewCentre()));
            }
    }

    private void createArcSector(CopyOnWriteArrayList<BulletObject> EBulletList, double angle) {
        if (cd % 15 == 0) {
            EBulletList.add(new BulletObject(energyBallY, 20, 20, CMath.vectorByAngle(-100 * intensity, angle), this.getNewCentre()));
            for (int i = 1; i < 8; i++) {
                EBulletList.add(new BulletObject(energyBallY, 20, 20, CMath.vectorByAngle(-100 * intensity, angle + 3 * i), this.getNewCentre()));
                EBulletList.add(new BulletObject(energyBallY, 20, 20, CMath.vectorByAngle(-100 * intensity, angle - 3 * i), this.getNewCentre()));
            }
        }
    }

    public void createHammer(CopyOnWriteArrayList<EnemyObject> enemies) {
        Hammer hammer = new Hammer(intensity);
        hammer.setCentre(new Point3f(getCentre().getX(), hammer.getCentre().getY(), 0));
        enemies.add(hammer);
    }
    public void create8Hammer(CopyOnWriteArrayList<EnemyObject> enemies) {

    }


}
