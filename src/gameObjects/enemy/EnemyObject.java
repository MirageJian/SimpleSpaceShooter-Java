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
    // If this object is rotating
    private boolean isRotating = false;
    private int rotationAngle = 90;
    // Cool down time
    protected int cd; // Frames unit
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
        if (!isStill) {
            getCentre().ApplyVector(enemyVector);
        }
    }
    public abstract void fire(CopyOnWriteArrayList<BulletObject> EBulletList, PlayerObject player);
    public int getScore() {
        return score;
    }
    public boolean isDead() {
        return health < 1;
    }
    public <T extends EnemyObject> T setStill(Class<T> type, boolean isStill) {
        this.isStill = isStill;
        return type.cast(this);
    }
    public <T extends EnemyObject> T setRotating(Class<T> type, boolean isRotating) {
        this.isRotating = isRotating;
        return type.cast(this);
    }
    public boolean isRotating() {
        return isRotating;
    }
    public boolean isOnBoundary() {
        return getCentre().getY() > GlobalConst.LAYOUT_HEIGHT;
    }

}
