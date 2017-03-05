package main;

/**
 * Created by tanki on 2017/2/25.
 */
public class Config {
    public static final int NODE_AMOUNT = 1000;
    public static final int AVG_DEGREE = 64;
    public static final int R = 500;
    public static final int X = Config.R + Config.R;
    public static final int Y = Config.R + Config.R;
    public static final int Z = Config.R + Config.R;
    public static final int CANVAS_MARGIN = 100;
    public static final int NODE_SIZE = 5;
    public static final float LEGAL_DISTANCE = (float) (Math.sqrt(64f / 4000f) / Math.PI);
    public static final float LEGAL_SQUARE_DISTANCE = LEGAL_DISTANCE * LEGAL_DISTANCE;
    public static final int UNIT_AMOUNT = (int) Math.ceil(1 / Config.LEGAL_DISTANCE);
    public static final boolean WILL_DRAW = true;
}
