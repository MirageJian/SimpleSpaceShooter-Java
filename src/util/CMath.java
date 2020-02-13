package util;

import settings.GlobalConst;

public class CMath {
    public static Vector3f vectorByAngle(float speed, double a) {
        float cos = (float) Math.cos(Math.toRadians(a));
        float sin = (float) Math.sin(Math.toRadians(a));
        return GlobalConst.getVectorPerFrame(new Vector3f(cos * speed, sin * speed, 0));
    }

    public static int getPerFrame(int angle) {
        return angle / GlobalConst.TARGET_FRAME;
    }
    public static Vector3f getPerFrame(double x, double y, double z) {
        return new Vector3f((float)x / GlobalConst.TARGET_FRAME, (float)y / GlobalConst.TARGET_FRAME, (float)z / GlobalConst.TARGET_FRAME);
    }
    public static Point3f getRandomCentre() {
        return new Point3f(((float) Math.random() * GlobalConst.BOUNDARY_WIDTH + GlobalConst.BOUNDARY_X_START), 0, 0);
    }
    /**
     *
     * @param frameCount current frames number
     * @param start start second
     * @param end end second
     * @param interval millisecond
     * @return will it be triggered
     */
    public static boolean timeTrigger(int frameCount, int start, int end, int interval) {
        return frameCount % CMath.getFrames(interval) == 0 && frameCount >= CMath.getFrames(start) && frameCount < CMath.getFrames(end);
    }
    public static boolean timeTrigger(int frameCount, int start) {
        return frameCount == start * GlobalConst.TARGET_FRAME;
    }
    public static int getFrames(int second) {
        return second * GlobalConst.TARGET_FRAME;
    }
}
