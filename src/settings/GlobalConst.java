package settings;

import util.CMath;
import util.Vector3f;

public class GlobalConst {
    public static final int TARGET_FRAME = 60;
    public static final float INTERVAL_PER_FRAME = 1000f / TARGET_FRAME;

    public static final Vector3f sFighterAMov = CMath.vectorByXYZ(-200, 0, 0); // Vector (pixel) per Second
    public static final Vector3f sFighterDMov = CMath.vectorByXYZ(200,0,0); // Vector (pixel) per Second
    public static final Vector3f sFighterWMov = CMath.vectorByXYZ(0,200,0); // Vector (pixel) per Second
    public static final Vector3f sFighterSMov = CMath.vectorByXYZ(0,-200,0); // Vector (pixel) per Second

    public static final int LAYOUT_WIDTH = 1000;
    public static final int LAYOUT_HEIGHT = 1000;
    public static final int BOUNDARY_WIDTH = 900;
    public static final int BOUNDARY_HEIGHT = 900;
    public static final int BOUNDARY_X_START = 50;
    public static final int BOUNDARY_Y_START = 50;

}
