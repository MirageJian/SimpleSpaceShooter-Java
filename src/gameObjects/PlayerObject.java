package gameObjects;

import settings.WeaponTypes;
import util.GameObject;
import util.GameResource;
import util.Point3f;

public class PlayerObject extends GameObject {
    public WeaponTypes currentWeapon = WeaponTypes.Laser;

    public PlayerObject(GameResource gameResource, int width, int height, Point3f centre) {
        super(gameResource, width, height, centre);
    }
}
