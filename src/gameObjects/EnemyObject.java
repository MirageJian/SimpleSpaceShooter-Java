package gameObjects;

import util.GameObject;
import util.GameResource;
import util.Point3f;

public class EnemyObject extends GameObject {
    public int score = 1;
    public int health = 1;
    public EnemyObject(GameResource gameResource, int width, int height, Point3f centre) {
        super(gameResource, width, height, centre);
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public boolean isDead() {
        return health < 1;
    }
}
