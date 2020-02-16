package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import util.CMath;
import util.GameResource;

import java.util.concurrent.CopyOnWriteArrayList;

public class UfoEnemy extends EnemyObject {
    private static GameResource ufoResource = new GameResource("res/UFO.png", 32, 32);
    private static GameResource bulletRes = new GameResource("res/energyball.png", 44, 43);
    private int intensity;

    public UfoEnemy(int intensity) {
        super(ufoResource, 50, 50, 100, CMath.vectorByXYZ(0,-100,0), 1000, 120);
        setAngularV(Math.toRadians(90));
        this.intensity = intensity;
    }

    @Override
    public void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player) {
        switch (cd) {
            case 0:
                EBulletList.add(new BulletObject(bulletRes, 20, 20, CMath.vectorByXYZ(0,-200,0), this.getNewCentre()));
        }
        cd -= 1;
    }
}
