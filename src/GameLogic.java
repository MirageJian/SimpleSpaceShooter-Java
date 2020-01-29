import gameObjects.EnemyObject;
import util.GameObject;
import util.GlobalConst;
import util.Point3f;

public class GameLogic {
    private Model world;
    public GameLogic(Model world) {
        this.world = world;
    }

    // This is the heart of the game , where the model takes in all the inputs ,decides the outcomes and then changes the model accordingly.
    void doLogic() {
        // Player Logic first
        playerLogic();
        // Enemy Logic next
        enemyLogic();
        // Bullets move next
        bulletLogic();
        // interactions between objects
        gameLogic();

    }

    private void gameLogic() {
        // this is a way to increment across the array list data structure
        //see if they hit anything
        // using enhanced for-loop style as it makes it alot easier both code wise and reading wise too
        for (EnemyObject temp : world.getEnemies()) {
            for (GameObject Bullet : world.getBullets()) {
                if (Math.abs(temp.getCentre().getX() - Bullet.getCentre().getX()) < temp.getWidth()
                        && Math.abs(temp.getCentre().getY() - Bullet.getCentre().getY()) < temp.getHeight()) {
                    world.getEnemies().remove(temp);
                    world.getBullets().remove(Bullet);
                    world.addScore(temp);
                }
            }
        }
    }

    private void enemyLogic() {
        // TODO Auto-generated method stub
        for (EnemyObject temp : world.getEnemies()) {
            // Move enemies
            temp.getCentre().ApplyVector(GlobalConst.sEnemySpeed);
            // See if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getY() == 900.0f)  // current boundary need to pass value to model
            {
                world.getEnemies().remove(temp);
                // enemies win so score decreased
                world.minusScore(temp);
            }
        }

        if (world.getEnemies().size() < 2) {
            while (world.getEnemies().size() < 6) {
                world.getEnemies().add(new EnemyObject(world.ufoResource, 50, 50, new Point3f(((float) Math.random() * 1000), 0, 0)));
            }
        }
    }

    private void bulletLogic() {
        // TODO Auto-generated method stub
        // move bullets
        for (GameObject temp : world.getBullets()) {
            //check to move them
            temp.getCentre().ApplyVector(GlobalConst.sBulletMov);
            //see if they hit anything
            //see if they get to the top of the screen ( remember 0 is the top
            if (temp.getCentre().getY() == 0) {
                world.getBullets().remove(temp);
            }
        }
    }

    private void playerLogic() {
        // smoother animation is possible if we make a target position  // done but may try to change things for students
        //check for movement and if you fired a bullet
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
        if (Controller.getInstance().isKeySpacePressed()) {
            createBullet();
            Controller.getInstance().setKeySpacePressed(false);
        }
    }
    private void createBullet() {
        world.getBullets().add(new GameObject(world.bulletResource, 32, 64, new Point3f(world.getPlayer().getCentre().getX(), world.getPlayer().getCentre().getY(), 0.0f)));
    }
}