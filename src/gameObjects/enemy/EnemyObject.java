package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import settings.GlobalConst;
import util.*;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EnemyObject extends GameObject {
    // Lode kinds of bullets
    protected static GameResource energyBall = new GameResource("res/energyball.png", 44, 43);
    protected static GameResource energyBall1 = new GameResource("res/energyball1.png", 44, 43);
    protected static GameResource eBullet1 = new GameResource("res/ebullet1.png", 85, 31);
    protected static GameResource eBullet2 = new GameResource("res/ebullet2.png", 45, 15);
    // Score after death
    private int score;
    private int health;
    private double maxHealth;
    // How enemy moves
    private Vector3f enemyVector;
    private boolean isStill = false;
    // Cool down time
    protected int cd; // Frames unit
    // Rotation angle per second
    private double angularV = 0;
    public EnemyObject(GameResource gameResource, int width, int height, int health, Vector3f enemyVector, int score, int cd) {
        super(gameResource, width, height, CMath.getRandomCentre());
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
}
