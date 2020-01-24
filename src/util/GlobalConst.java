package util;

public class GlobalConst {
    public static final int TARGET_FRAME = 60;
    public static final float INTERVAL_PER_FRAME = 1000f / TARGET_FRAME;

    public static final Vector3f sEnemySpeed = getVectorPerFrame(new Vector3f(0,-100,0)); // Vector (pixel) per Second
    public static final Vector3f sFighterAMov = getVectorPerFrame(new Vector3f(-200,0,0)); // Vector (pixel) per Second
    public static final Vector3f sFighterDMov = getVectorPerFrame(new Vector3f(200,0,0)); // Vector (pixel) per Second
    public static final Vector3f sFighterWMov = getVectorPerFrame(new Vector3f(0,200,0)); // Vector (pixel) per Second
    public static final Vector3f sFighterSMov = getVectorPerFrame(new Vector3f(0,-200,0)); // Vector (pixel) per Second
    public static final Vector3f sBulletMov = getVectorPerFrame(new Vector3f(0,300,0)); // Vector (pixel) per Second


    private static Vector3f getVectorPerFrame(Vector3f vectorPerSec) {
        return new Vector3f(
                (vectorPerSec.getX() / 1000f) * GlobalConst.INTERVAL_PER_FRAME,
                (vectorPerSec.getY() / 1000f) * GlobalConst.INTERVAL_PER_FRAME,
                (vectorPerSec.getZ() / 1000f) * GlobalConst.INTERVAL_PER_FRAME
        );
    }
}