package gameObjects.enemy;

import gameObjects.BulletObject;
import gameObjects.PlayerObject;
import settings.GlobalConst;
import util.*;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EnemyObject extends GameObject {
    private int score;
    private int health;
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
        this.enemyVector = enemyVector;
        this.score = score;
        this.cd = cd;
    }
    public void reduceHealth(int damage) {
        health -= damage;
    }
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
