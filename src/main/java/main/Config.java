package main;

/**
 * Created by tanki on 2017/2/25.
 */
public class Config {
    public static final String type = "sphere";
    public static final int NODE_AMOUNT = 128000;
    public static final int AVG_DEGREE = 128;
    public static final int R = 800;
    public static final int X = Config.R;
    public static final int Y = Config.R;
    public static final int Z = Config.R;
    public static final int CANVAS_MARGIN = 10;
    public static int NODE_SIZE = R / 200;
    //    public static final float LEGAL_DISTANCE = (float) (Math.sqrt(0.5 * (float) AVG_DEGREE / (float) NODE_AMOUNT)); // for disk
//    public static final float LEGAL_DISTANCE = (float) (Math.sqrt(AVG_DEGREE/(float)NODE_AMOUNT/Math.PI)); // for square
    public static final float LEGAL_DISTANCE = (float) (Math.sqrt(AVG_DEGREE / (float) NODE_AMOUNT)); // for shpere
    public static final float LEGAL_SQUARE_DISTANCE = LEGAL_DISTANCE * LEGAL_DISTANCE;
    public static final int UNIT_AMOUNT = (int) Math.ceil(1 / Config.LEGAL_DISTANCE);
    public static final boolean WILL_DRAW = true;
    public static final String exportPath = "./" + Config.type + "-node-" + Config.NODE_AMOUNT + "-avg-" + Config.AVG_DEGREE + "/";
}
