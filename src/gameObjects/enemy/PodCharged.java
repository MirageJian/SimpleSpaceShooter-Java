package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import util.CMath;
import util.GameResource;

import java.util.concurrent.CopyOnWriteArrayList;

public class PodCharged extends EnemyObject {
    private static GameResource resource = new GameResource("res/enemypodcharged.png", 127, 128);
    private static GameResource bulletRes = new GameResource("res/energyball.png", 44, 43);
    private static int bulletAngle = 0;

    public PodCharged(int intensity) {
        super(resource,127, 128, 5_000, CMath.getPerFrame(0, -10, 0), 10_000, 1_200);
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        if (cd < 1000 && cd > 0) {
            EBulletList.add(new BulletObject(bulletRes, 20, 20, CMath.vectorByAngle(-100, bulletAngle), this.getNewCentre()));
            bulletAngle += 10;
        }
        cd -= 1;
    }
}
