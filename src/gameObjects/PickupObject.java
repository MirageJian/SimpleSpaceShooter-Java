package gameObjects;

import settings.GlobalConst;
import util.*;

public class PickupObject extends GameObject {
    private static GameResource shieldRes = new GameResource("res/shield_pickup.png", 100 ,100, "sound/item_pickup.wav");
    private static GameResource weaponRes = new GameResource("res/weapon_pickup.png", 100, 100, "sound/item_pickup.wav");
    private Vector3f pickupVector;
    private boolean isShield;

    private PickupObject(GameResource res, Point3f centre, boolean isShield) {
        super(res, 40, 40, null);
        setCentre(centre);
        pickupVector = CMath.vectorByAngle(-100, Math.random() * 360);
        this.isShield = isShield;
    }

    public static PickupObject createPickup(Point3f centre) {
        if (CMath.binaryRandom()) return new PickupObject(shieldRes, centre, true);
        else return new PickupObject(weaponRes, centre, false);
    }

    public void applyVector() {
        getCentre().ApplyVector(pickupVector);
        if (getCentre().getX() <= 0)
            pickupVector = CMath.vectorByAngle(100, Math.random() * 180 - 90);
        else if (getCentre().getX() >= GlobalConst.LAYOUT_WIDTH)
            pickupVector = CMath.vectorByAngle(100, Math.random() * 180 + 90);
        else if (getCentre().getY() <= 0)
            pickupVector = CMath.vectorByAngle(100, Math.random() * 180 + 180);
        else if (getCentre().getY() >= GlobalConst.LAYOUT_HEIGHT)
            pickupVector = CMath.vectorByAngle(100, Math.random() * 180);
    }

    public boolean isContact(PlayerObject player) {
        boolean isContact = Math.abs(player.getCentre().getX() - getCentre().getX()) < player.getWidth() / 2f
                && Math.abs(player.getCentre().getY() - getCentre().getY()) < player.getHeight() / 2f;
        if (isContact) {
            // Shield
            if (isShield) player.setShieldTime(CMath.getFrames(10));
                // Laser upgrade
            else if (player.laser.isOn) player.laser.laserUpgrade();
                // Bullet upgrade
            else player.bulletUpgrade();
        }
        return isContact;
    }
}
