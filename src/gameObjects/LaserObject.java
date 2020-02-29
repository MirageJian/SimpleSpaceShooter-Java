package gameObjects;

import gameObjects.enemy.EnemyObject;
import util.GameObject;
import util.GameResource;
import util.Point3f;

import java.util.concurrent.CopyOnWriteArrayList;

// Player weapon object: Laser, Single object
public class LaserObject extends GameObject {
    public boolean isOn = false;
    private int laserLv = 1;
    private final static int MAX_LV = 100;

    public LaserObject(GameResource gameResource, int width, int height, PlayerObject player) {
        super(gameResource, width, height, null);
        this.setCentre(player.getCentre());
    }

    // Weapon upgrade method
    public void laserUpgrade() {
        if (laserLv < MAX_LV) {
            laserLv++;
            if (laserLv < 20)
                setWidth((int)(1.08 * getWidth()));
        }
    }

    public int getDamage() {
        // Per frame, get final laser damage
        return 6 * laserLv;
    }

    public static void createEf(CopyOnWriteArrayList<EffectObject> ef, GameResource laserEf, EnemyObject em, int frames) {
        double minX = em.getCentre().getX() - em.getWidth() / 2.0;
        double minY = em.getCentre().getY() - em.getHeight() / 2.0;
        if (frames % 2 == 0) {
            float x = (float) (Math.random() * em.getWidth() + minX);
            float y = (float) (Math.random() * em.getHeight() + minY);
            ef.add(new EffectObject(laserEf, em, 1, 8,false) {{
                setCentre(new Point3f(x, y, 0));
                setWidth(30);
                setHeight(30);
            }});
        }
    }

    public int getLaserLv() {
        return laserLv;
    }
}
