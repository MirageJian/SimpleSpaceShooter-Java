import gameObjects.*;
import gameObjects.enemy.*;
import settings.WeaponTypes;
import settings.GlobalConst;
import util.CMath;
import util.Vector3f;

public class GameLogic {
    private Model world;
    public int frameCount = 0;

    public GameLogic(Model world) {
        this.world = world;
    }

    // This is the heart of the game , where the model takes in all the inputs ,decides the outcomes and then changes the model accordingly.
    void doLogic() {
        // Player 1 Logic first
        if (!world.getPlayer().isDying() && !world.getPlayer().isDead()) playerLogic();
        if (world.getP2() != null) {
            // Player 2 Logic
            if (!world.getP2().isDying() && !world.getP2().isDead()) p2Logic();
            // Game Over
            if (world.getP2().isDead() && world.getPlayer().isDead()) world.setGameEnd();
        } // Game Over
        else if (world.getPlayer().isDead()) world.setGameEnd();
        if (world.isGameEnd()) return;
        // Pickup objects Logic
        pickupLogic();
        // Enemy Logic next
        enemyLogic();
        enemyBulletLogic();
        // Bullets move next
        bulletLogic();
        // interactions between objects
        gameLogic();
        effectLogic();
        // Game process
        gameProcess();
        frameCount += 1;
        // Survival to get score
        world.addScore();
        // Update info every 0.5s
        if (frameCount % 30 == 0 && world.getCallback() != null)
            world.getCallback().updateUI(world.getPlayer(), world.getP2());
    }

    private void gameLogic() {
        // this is a way to increment across the array list data structure
        //see if they hit anything
        // using enhanced for-loop style as it makes it alot easier both code wise and reading wise too
        for (EnemyObject temp : world.getEnemies()) {
            for (BulletObject bullet : world.getBullets()) {
                if (Math.abs(temp.getCentre().getX() - bullet.getCentre().getX()) < temp.getWidth() / 2f
                        && Math.abs(temp.getCentre().getY() - bullet.getCentre().getY()) < temp.getHeight() / 2f) {
                    // Damage part
                    temp.reduceHealth(bullet.bulletDamage);
                    world.getBullets().remove(bullet);
                    world.getEffectList().add(new EffectObject(world.bulletEf, bullet, 1, 8) {{
                        setWidth(20);
                        setHeight(20);
                    }});
                }
            }
            // Player 1 Laser logic
            laserLogic(world.getPlayer(), temp);
            // Player 2 Laser
            if (world.getP2() != null) laserLogic(world.getP2(), temp);
            // Enemies are dead or not
            if (temp.isDead()) {
                world.getEnemies().remove(temp);
                world.addScore(temp);
                world.getEffectList().add(new EffectObject(world.explosionEnemy, temp, 10, 6));
                // There is a possibility to generate pickup
                if (CMath.lowChanceRandom())
                    world.getPickupList().add(PickupObject.createPickup(temp.getNewCentre()));
                // Statistic
                world.setEnemiesEliminatedNum();
            }
        }
    }
    /** This method is for game Logic*/
    private void laserLogic(PlayerObject player, EnemyObject enemy) {
        if (player.laser.isOn) {
            float height = player.laser.getCentre().getY() - enemy.getCentre().getY();
            float widthMax = Math.max(player.laser.getWidth(), enemy.getWidth());
            if (Math.abs(enemy.getCentre().getX() - player.laser.getCentre().getX()) < widthMax / 2f
                    && height < player.laser.getHeight() && height > 0) {
                // Set the contact object for laser
                enemy.reduceHealth(player.laser.getDamage());
                LaserObject.createEf(world.getEffectList(), world.laserEf, enemy, frameCount);
            }
        }
    }

    private void pickupLogic() {
        for (PickupObject pickup : world.getPickupList()) {
            // Pickup moves
            pickup.applyVector();
            if (pickup.isContact(world.getPlayer())) {
                world.getPickupList().remove(pickup);
                pickup.resource.startSound();
            }
            else if (world.getP2() != null && pickup.isContact(world.getP2())) {
                world.getPickupList().remove(pickup);
                pickup.resource.startSound();
            }
        }
        // Reduce shield time
        world.getPlayer().weakenShield();
        if (world.getP2() != null) world.getP2().weakenShield();
    }

    // How enemies move
    private void enemyLogic() {
        for (EnemyObject enemy : world.getEnemies()) {
            // Move enemies
            enemy.applyVector();
            // Fire to player
            enemy.fire(world.getEBulletList(), world.getP2() != null ? CMath.binaryRandom() ? world.getPlayer() : world.getP2() : world.getPlayer());
            // See if they get to the top of the screen ( remember 0 is the top
            if (enemy.isOnBoundary())  // current boundary need to pass value to model
                world.getEnemies().remove(enemy);
        }
    }

    // Enemy bullets
    private void enemyBulletLogic() {
        for (BulletObject eb : world.getEBulletList()) {
            eb.applyVector();
            // Boundary check
            if (eb.isOnBoundary()) world.getEBulletList().remove(eb);
            // Death detection
            deathDetection(world.getPlayer(), eb);
            if (world.getP2() != null) deathDetection(world.getP2(), eb);
        }
    }
    /**This method is for enemy Bullet logic*/
    private void deathDetection(PlayerObject player, BulletObject eb) {
        if (!player.isDying() && !player.isDead()) {
            float width = Math.abs(player.getCentre().getX() - eb.getCentre().getX());
            float height = Math.abs(player.getCentre().getY() - eb.getCentre().getY());
            // Shield immune enemy's bullets
            if (player.isShield()) {
                // Remove bullets
                if (width < player.getWidth() / 1.5f && height < player.getHeight() / 1.5f){
                    world.getEBulletList().remove(eb);
                    // shield will lose time when damaged.
                    player.damageShield();
                }
            } else if (width < eb.getWidth() / 2f && height < eb.getHeight() / 2f) {
                // Then die, if there is no shield. Dying state will not remove bullets
                world.getEBulletList().remove(eb);
                player.laser.isOn = false;
                // Set death time
                player.setDyingTime();
            }
        }
    }

    // Player bullets
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
        if (world.getPlayer().bulletClodDown > 0) world.getPlayer().bulletClodDown--;
        if (world.getP2() != null && world.getP2().bulletClodDown > 0) world.getP2().bulletClodDown--;
    }

    private void effectLogic() {
        world.getEffectList().removeIf(EffectObject::frameCheck);
    }

    private void playerLogic() {
        PlayerObject player = world.getPlayer();
        // Weapon switch logic
        if (Controller.getInstance().isKeyRPressed()) {
            if (player.currentWeapon == WeaponTypes.Laser)
                player.currentWeapon = WeaponTypes.Bullet;
            else player.currentWeapon = WeaponTypes.Laser;
            Controller.getInstance().setKeyRPressed(false);
            player.laser.isOn = false;
        }
        // smoother animation is possible if we make a target position  // done but may try to change things for students
        // check for movement and if you fired a bullet
        if (Controller.getInstance().isKeyAPressed()) {
            player.getCentre().ApplyVector(GlobalConst.sFighterAMov);
        }
        if (Controller.getInstance().isKeyDPressed()) {
            player.getCentre().ApplyVector(GlobalConst.sFighterDMov);
        }
        if (Controller.getInstance().isKeyWPressed()) {
            player.getCentre().ApplyVector(GlobalConst.sFighterWMov);
        }
        if (Controller.getInstance().isKeySPressed()) {
            player.getCentre().ApplyVector(GlobalConst.sFighterSMov);
        }
        // Whether laser is on
        if (Controller.getInstance().isKeySpacePressed()) {
            // If current weapon is laser
            if (player.currentWeapon == WeaponTypes.Laser) {
                player.laser.isOn = true;
//                world.laserEf.loopSoundNonRest();
            }
                // Bullet part
            else {
                player.createBullet(world.getBullets(), world.bulletResource);
//                Controller.getInstance().setKeySpacePressed(false);
            }
        } else {
            if (player.currentWeapon == WeaponTypes.Laser) {
                player.laser.isOn = false;
//                world.laserEf.stopSound();
            }
        }
    }
    private void p2Logic() {
        PlayerObject player = world.getP2();
        MouseControl control = MouseControl.getInstance();
        control.poll();
        // Movement
        double width = control.getPosition().getX() - player.getCentre().getX();
        double height = control.getPosition().getY() - player.getCentre().getY();
        double x = width > 0 ? width < GlobalConst.sFighterDMov.getX() ? width : GlobalConst.sFighterDMov.getX()
                : width > GlobalConst.sFighterAMov.getX() ? width : GlobalConst.sFighterAMov.getX();
        double y = height > 0 ? height < GlobalConst.sFighterWMov.getY() ? height : GlobalConst.sFighterWMov.getY()
                : height > GlobalConst.sFighterSMov.getY() ? height : GlobalConst.sFighterSMov.getY();
        // Make movement normal
        if (Math.abs(height) > Math.abs(width) && Math.abs(height) != 0) {
            x = x * Math.abs(width / height);
        }
        if (Math.abs(height) <= Math.abs(width) && Math.abs(width) != 0) {
            y = y * Math.abs(height / width);
        }
        player.getCentre().ApplyVector(new Vector3f((float) x, (float) -y, 0));
        // Change weapon
        if (control.buttonDownOnce(3)) {
            if (player.currentWeapon == WeaponTypes.Laser)
                player.currentWeapon = WeaponTypes.Bullet;
            else player.currentWeapon = WeaponTypes.Laser;
            player.laser.isOn = false;
        }
        // Fire
        if(control.buttonDown(1)) {
            // If current weapon is laser
            if (player.currentWeapon == WeaponTypes.Laser) player.laser.isOn = true;
                // Bullet part
            else player.createBullet(world.getBullets(), world.bulletResource);
        } else {
            if (player.currentWeapon == WeaponTypes.Laser) player.laser.isOn = false;
        }
    }

    private void gameProcess() {
        // Intensity logic
        world.setIntensity(frameCount / CMath.getFrames(180) + 1);
        int intensity = world.getIntensity();
        // Frames and times part
        int cuFrame = frameCount % CMath.getFrames(180);
        // 1st raid
        if (CMath.timeTrigger(cuFrame, 0, 10, 1)) {
            world.getEnemies().add(new UfoEnemy(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 10)) {
            world.getEnemies().add(new PodCharged(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 10, 20, 1)) {
            world.getEnemies().add(new UfoEnemy(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 20)) {
            world.getEnemies().add(new PodCharged(intensity));
        }
        // 2nd raid
        if (CMath.timeTrigger(cuFrame, 20, 30, 2)) {
            world.getEnemies().add(new Fighter1(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 30)) {
            world.getEnemies().add(new PodCharged(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 30, 40, 1)) {
            world.getEnemies().add(new UfoEnemy(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 40)) {
            world.getEnemies().add(new PodCharged(intensity));
        }
        // 3rd raid
        if (CMath.timeTrigger(cuFrame, 40, 60, 2)) {
            world.getEnemies().add(new UfoEnemy(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 55)) {
            world.getEnemies().add(new PodCharged(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 60, 80, 1)) {
            world.getEnemies().add(new Fighter1(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 80, 90, 0.5)) {
            world.getEnemies().add(new UfoEnemy(intensity));
        }
        // Second boss
        if (CMath.timeTrigger(cuFrame, 90)) {
            world.getEnemies().add(new DevilRay(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 90, 120, 1)) {
            world.getEnemies().add(new Drone(intensity));
        }
        // Final boss
        MechaBody mecha = new MechaBody(intensity);
        if (CMath.timeTrigger(cuFrame, 120)) {
            world.getEnemies().add(mecha);
        }
        if (CMath.timeTrigger(cuFrame, 120, 125, 0.5)) {
            world.getEnemies().add(new Drone(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 125, 130, 0.5)) {
            world.getEnemies().add(new Fighter1(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 130, 150 , 2)) {
            mecha.createHammer(world.getEnemies());
        }
        if (CMath.timeTrigger(cuFrame, 150)) {
            mecha.create8Hammer(world.getEnemies());
        }
        // Kinds of Enemies
        if (CMath.timeTrigger(cuFrame, 150, 180 , 5)) {
            mecha.createHammer(world.getEnemies());
        }
        if (CMath.timeTrigger(cuFrame, 150, 180, 4)) {
            world.getEnemies().add(new Drone(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 150, 180, 3)) {
            world.getEnemies().add(new Fighter1(intensity));
        }
        if (CMath.timeTrigger(cuFrame, 150, 180, 2)) {
            world.getEnemies().add(new UfoEnemy(intensity));
        }

    }
}
