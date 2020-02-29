package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import settings.GlobalConst;
import util.*;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EnemyObject extends GameObject {
    // Lode kinds of bullets
    protected static GameResource energyBall = new GameResource("res/energyball.png", 44, 43);
    protected static GameResource energyBallB = new GameResource("res/energyball1.png", 44, 43);
    protected static GameResource energyBallY = new GameResource("res/energyball2.png", 44, 43);
    protected static GameResource energyBallG = new GameResource("res/energyball3.png", 44, 43);
    protected static GameResource energyBallR = new GameResource("res/energyball4.png", 44, 43);
    protected static GameResource lBullet = new GameResource("res/ebullet2.png", 45, 15);
    // Score after death
    private int score;
    private int health;
    private double maxHealth;
    // How enemy moves
    protected Vector3f enemyVector;
    private boolean isStill = false;
    // Cool down time
    protected int cd; // Frames unit
    // Rotation angle per second
    private double angularV = 0;
    public EnemyObject(GameResource gameResource, int width, int height, int health, Vector3f enemyVector, int score, int cd) {
        super(gameResource, width, height, CMath.getRandomCentre(-height/2));
        this.health = health;
        maxHealth = health;
        this.enemyVector = enemyVector;
        this.score = score;
        this.cd = cd;
    }
    // Health part
    public void reduceHealth(int damage) {
        health -= damage;
    }
    public double getHealthRatio() { return health/maxHealth; }
    // If still, the object won't move
    public void applyVector() {
        // Rotation setting
        this.setRotation(this.getRotation() + this.getAngularV());
        // Move
        if (!isStill) {
            getCentre().ApplyVector(enemyVector);
        }
    }
    public abstract void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player);
    public int getScore() {
        return score;
    }
    // Is enemy dead
    public boolean isDead() {
        return health < 1;
    }
    public <T extends EnemyObject> T setStill(Class<T> type, boolean isStill) {
        this.isStill = isStill;
        return type.cast(this);
    }
    // is enemy on the boundary
    public boolean isOnBoundary() {
        return getCentre().getY() > GlobalConst.LAYOUT_HEIGHT;
    }
    // Set angular velocity

    public double getAngularV() {
        return angularV;
    }

    public  void setAngularV(double angularV) {
        this.angularV = angularV / GlobalConst.TARGET_FRAME;
    }

    protected void flyAndPause(double angle) {
        boolean isPaused = getCentre().getY() > 200;
        float currentX = enemyVector.getX() * GlobalConst.TARGET_FRAME;
        float x = isPaused ? currentX != 0 ? currentX : -20 : 0;
        float y = isPaused ? 0 : enemyVector.getY() * GlobalConst.TARGET_FRAME;
        if (isPaused && getCentre().getX() < 100)  x = 40;
        if (isPaused && getCentre().getX() > GlobalConst.LAYOUT_WIDTH - 100) x = -40;
        enemyVector = CMath.vectorByXYZ(x, y, 0);
    }
}
