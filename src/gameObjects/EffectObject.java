package gameObjects;

import util.GameObject;
import util.GameResource;

public class EffectObject extends GameObject {
    // Time per frame
    private int lastTimePerF;
    // How many frames this effect has
    private int frameNum;
    // Current time of this effect
    private int currentFrame = 0;

    public EffectObject(GameResource resource, GameObject object, int lastTimePerF, int frameNum) {
        this(resource, object, lastTimePerF, frameNum, true);
    }

    public EffectObject(GameResource resource, GameObject object, int lastTimePerF, int frameNum, boolean startSound) {
        super(resource, object.getWidth(), object.getHeight(), null);
        this.setCentre(object.getNewCentre());
        this.lastTimePerF = lastTimePerF;
        this.frameNum = frameNum;
        if (startSound) resource.startSound();
    }

    public int getLastTimePerF() {
        return lastTimePerF;
    }

    public int getFrameNum() {
        return frameNum;
    }
    public int getCurrentFrame() {
        return currentFrame;
    }
    // When draw it successfully, use this method to check. To make sure effect disappear
    public boolean frameCheck() {
        currentFrame += 1;
        return currentFrame >= lastTimePerF * frameNum;
    }
}
