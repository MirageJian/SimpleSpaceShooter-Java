package gameObjects;

import util.GameObject;
import util.GameResource;
import util.Point3f;

public class EnemyObject extends GameObject {
    private int score = 1;
    private int health = 100;
    public EnemyObject(GameResource gameResource, int width, int height, Point3f centre) {
        super(gameResource, width, height, centre);
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public boolean isDead() {
        return health < 1;
    }
    public void reduceHealth(int damage) {
        health -= damage;
    }
}
