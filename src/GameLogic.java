import gameObjects.BulletObject;
import gameObjects.PickupObject;
import gameObjects.enemy.EnemyObject;
import gameObjects.PlayerObject;
import gameObjects.enemy.PodCharged;
import gameObjects.enemy.UfoEnemy;
import settings.WeaponTypes;
import settings.GlobalConst;
import util.CMath;

public class GameLogic {
    private Model world;
    private int frameCount = 0;

    public GameLogic(Model world) {
        this.world = world;
    }

    // This is the heart of the game , where the model takes in all the inputs ,decides the outcomes and then changes the model accordingly.
    void doLogic() {
        // Player Logic first
        playerLogic();
        // Pickup objects Logic
        pickupLogic();
        // Enemy Logic next
        enemyLogic();
        // Bullets move next
        bulletLogic();
        // interactions between objects
        gameLogic();
        // Game process
        gameProcess();
        frameCount += 1;
        world.addScore();
    }

    private void gameLogic() {
        // this is a way to increment across the array list data structure
        //see if they hit anything
        // using enhanced for-loop style as it makes it alot easier both code wise and reading wise too
        EnemyObject laserContact = null;
        for (EnemyObject temp : world.getEnemies()) {
            for (BulletObject bullet : world.getBullets()) {
                if (Math.abs(temp.getCentre().getX() - bullet.getCentre().getX()) < temp.getWidth()/2f
                        && Math.abs(temp.getCentre().getY() - bullet.getCentre().getY()) < temp.getHeight()/2f) {
                    // Damage part
                    temp.reduceHealth(bullet.bulletDamage);
                    world.getBullets().remove(bullet);
                }
            }
            // Laser logic
            if (world.laser.isOn){
                float height = world.laser.getCentre().getY() - temp.getCentre().getY();
                if (Math.abs(temp.getCentre().getX() - world.laser.getCentre().getX()) < temp.getWidth() / 2f
                        && height < world.laser.getHeight() && height > 0) {
                    // Set the contact object for laser
                    laserContact = temp;
                    temp.reduceHealth(world.laser.damage);
                    // laser can only attack a object
                }
            }
            // Judy enemies are dead or not
            if (temp.isDead()) {
                world.getEnemies().remove(temp);
                world.addScore(temp);
                world.pickupList.add(PickupObject.createPickup(temp.getNewCentre()));
            }
        }
        world.laser.setContactObject(laserContact);
    }
    private void pickupLogic() {
        for (PickupObject pickup : world.pickupList) {
            // Pickup moves
            pickup.applyVector();
            if (pickup.isContact(world.getPlayer())) {
                world.pickupList.remove(pickup);
            }
        }
        // Reduce shield time
        world.getPlayer().weakenShield();
    }


    private void enemyLogic() {
        for (EnemyObject enemy : world.getEnemies()) {
            // Move enemies
            enemy.applyVector();
            // Fire to player
            enemy.fire(world.eBulletList, world.getPlayer());
            // See if they get to the top of the screen ( remember 0 is the top
            if (enemy.isOnBoundary())  // current boundary need to pass value to model
                world.getEnemies().remove(enemy);
        }
        // Enemy bullets
        for (BulletObject eb : world.eBulletList) {
            eb.applyVector();
            // Boundary check
            if (eb.isOnBoundary()) world.eBulletList.remove(eb);
            float width = Math.abs(world.getPlayer().getCentre().getX() - eb.getCentre().getX());
            float height = Math.abs(world.getPlayer().getCentre().getY() - eb.getCentre().getY());
            if(world.getPlayer().isShield()) {
                // Remove bullets
                if ( width < world.getPlayer().getWidth()/1.5f && height < world.getPlayer().getHeight()/1.5f)
                    world.eBulletList.remove(eb);
            } else if (width < eb.getWidth() / 2f && height < eb.getHeight() / 2f)
                // The end of game
                world.isGameEnd = true;
        }
    }

    private void bulletLogic() {
        // move bullets
        for (BulletObject temp : world.getBullets()) {
            //check to move them
            temp.applyVector();
            //see if they hit anything
            //see if they get to the top of the screen ( remember 0 is the top
            if (temp.isOnBoundary()) {
                world.getBullets().remove(temp);
            }
        }
        // Reduce cool down time
        if (PlayerObject.bulletClodDown > 0) PlayerObject.bulletClodDown--;
    }

    private void playerLogic() {
        // Weapon switch logic
        if (Controller.getInstance().isKeyRPressed()) {
            if (world.getPlayer().currentWeapon == WeaponTypes.Laser)  world.getPlayer().currentWeapon = WeaponTypes.Bullet;
            else world.getPlayer().currentWeapon = WeaponTypes.Laser;
            Controller.getInstance().setKeyRPressed(false);
            world.laser.isOn = false;
        }
        // smoother animation is possible if we make a target position  // done but may try to change things for students
        // check for movement and if you fired a bullet
        if (Controller.getInstance().isKeyAPressed()) {
            world.getPlayer().getCentre().ApplyVector(GlobalConst.sFighterAMov);
        }
        if (Controller.getInstance().isKeyDPressed()) {
            world.getPlayer().getCentre().ApplyVector(GlobalConst.sFighterDMov);
        }
        if (Controller.getInstance().isKeyWPressed()) {
            world.getPlayer().getCentre().ApplyVector(GlobalConst.sFighterWMov);
        }
        if (Controller.getInstance().isKeySPressed()) {
            world.getPlayer().getCentre().ApplyVector(GlobalConst.sFighterSMov);
        }
        // Whether laser is on
        if (Controller.getInstance().isKeySpacePressed()) {
            // If current weapon is laser
            if (world.getPlayer().currentWeapon == WeaponTypes.Laser) world.laser.isOn = true;
            // Bullet part
            else {
                world.getPlayer().createBullet(world.getBullets(), world.bulletResource);
//                Controller.getInstance().setKeySpacePressed(false);
            }
        } else {
            if (world.getPlayer().currentWeapon == WeaponTypes.Laser) world.laser.isOn = false;
        }
    }
    private void gameProcess() {
        int intensity = frameCount / 18_000;
        if (CMath.timeTrigger(frameCount, 0, 10, 1)) {
            world.getEnemies().add(new UfoEnemy(intensity));
        }
        if (CMath.timeTrigger(frameCount, 12)) {
            world.getEnemies().add(new PodCharged(intensity).setRotating(PodCharged.class, true));
        }
        if (CMath.timeTrigger(frameCount, 20, 30, 1)) {
            world.getEnemies().add(new UfoEnemy(intensity));
        }
    }
}
