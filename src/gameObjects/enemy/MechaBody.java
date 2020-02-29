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
    private final static int sDefaultCd = 2400;
    private int intensity;

    public MechaBody(int intensity) {
        super(resource,118, 160, 100_000 * intensity, CMath.vectorByXYZ(-10, -20, 0), 1_000_000, sDefaultCd);
        this.intensity = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        double angle = CMath.getAngle(this, player);
        if (cd > 1800 && cd < 2100)
            createCircle(EBulletList, cd);
        if (cd > 1400 && cd < 1700)
            createParallel(EBulletList, cd, angle);
        if (cd > 1000 && cd < 1300)
            createTriangle(EBulletList, cd, angle);
        if (cd > 0 && cd < 900)
            createStar(EBulletList, cd);
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

    private void createParallel(CopyOnWriteArrayList<BulletObject> EBulletList, int cd, double angle) {
        if (cd % 10 == 0) {
            for (int i = 0; i < 4; i++) {
                Point3f newP = this.getNewCentre();
                newP.setX(newP.getX() - 60 + 40 * i);
                EBulletList.add(new BulletObject(energyBallG, 6, 20, CMath.vectorByAngle(-200 * intensity, angle), newP));
            }
        }
    }
    private void createTriangle(CopyOnWriteArrayList<BulletObject> EBulletList, int cd, double angle) {
        if (cd % 10 == 0) {
            for (int i = 0; i < 3; i++) {
                EBulletList.add(new BulletObject(energyBallY, 10, 20, CMath.vectorByAngle(-100 * intensity, angle - 20 + 20 * i), this.getNewCentre()));
            }
        }

    }
    private void createStar(CopyOnWriteArrayList<BulletObject> EBulletList, int cd) {
            if (cd % 10 == 0) {
            for (int i = 0; i < 8; i++) {
                EBulletList.add(new BulletObject(energyBall, 20, 20, CMath.vectorByAngle(-100 * intensity, 45 * i + bulletAngle), this.getNewCentre()));
            }
            bulletAngle += 10;
        }
    }

    // Boss will create hammers
    public void createHammer(CopyOnWriteArrayList<EnemyObject> enemies) {
        Hammer hammer = new Hammer(intensity);
        hammer.setCentre(new Point3f(getCentre().getX(), hammer.getCentre().getY(), 0));
        enemies.add(hammer);
    }
    // Create 8 hammer a time
    public void create8Hammer(CopyOnWriteArrayList<EnemyObject> enemies) {
        for (int i = 0; i < 8; i++) {
            Hammer hammer = new Hammer(intensity);
            hammer.setCentre(new Point3f(GlobalConst.BOUNDARY_X_START + (GlobalConst.BOUNDARY_WIDTH / 7f) * i , 0, 0));
            enemies.add(hammer);
        }
    }
}
