package gameObjects;

import util.GameObject;
import util.GameResource;
import util.Point3f;

public class WeaponObject extends GameObject {
    public int damage = 1;
    public boolean isOn = true;
    public WeaponObject(GameResource gameResource, int width, int height, Point3f centre) {
        super(gameResource, width, height, centre);
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
