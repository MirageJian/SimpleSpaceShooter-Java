package util;

import settings.GlobalConst;

public class CMath {
    public static Vector3f vectorByAngle(float speed, double a) {
        float cos = (float) Math.cos(Math.toRadians(a));
        float sin = (float) Math.sin(Math.toRadians(a));
        return CMath.vectorByXYZ(cos * speed, sin * speed, 0);
    }
    public static Vector3f vectorByXYZ(double x, double y, double z) {
        return new Vector3f((float)x / GlobalConst.TARGET_FRAME, (float)y / GlobalConst.TARGET_FRAME, (float)z / GlobalConst.TARGET_FRAME);
    }
    public static Point3f getRandomCentre(int y) {
        return new Point3f(((float) Math.random() * GlobalConst.BOUNDARY_WIDTH + GlobalConst.BOUNDARY_X_START), y, 0);
    }
    public static double getAngle(GameObject o1, GameObject o2) {
        float x = o1.getCentre().getX() - o2.getCentre().getX();
        float y = o1.getCentre().getY() - o2.getCentre().getY();
        double hypotenuse = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double acos = Math.toDegrees(Math.acos(x/hypotenuse));
        return y >= 0 ? -acos : acos;
    }
    /**
     *
     * @param frameCount current frames number
     * @param start start second
     * @param end end second
     * @param interval millisecond
     * @return will it be triggered
     */
    public static boolean timeTrigger(int frameCount, int start, int end, double interval) {
        return frameCount % CMath.getFrames(interval) == 0 && frameCount >= CMath.getFrames(start) && frameCount < CMath.getFrames(end);
    }
    public static boolean timeTrigger(int frameCount, int start) {
        return frameCount == start * GlobalConst.TARGET_FRAME;
    }
    public static int getFrames(double second) {
        return (int)(second * GlobalConst.TARGET_FRAME);
    }
    public static boolean binaryRandom() {
        return Math.random() * 10 > 5;
    }
    public static boolean lowChanceRandom() {
        return Math.random() * 10 > 6;
    }

    public static int normalIntense(int num, int intensity) {
        return (int) (num / (intensity * 0.1 + 0.9));
    }
}
