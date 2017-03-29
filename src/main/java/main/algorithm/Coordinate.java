package main.algorithm;

import main.Config;

/**
 * Created by tanki on 2017/3/2.
 */
public abstract class Coordinate implements Comparable<Coordinate> {
    //    public static enum TYPE {
//        CARTESIAN,
//        POLAR2,
//        POLAR3
//    };
    abstract CellIterator getCellIterator();

    public Coordinate() {
        randomlyInit();
    }

    public abstract String getCellIndex();

    abstract void randomlyInit();

    public abstract double SquaredDistance(Coordinate a, Coordinate b);

    public static float eularSquareDistance(float x1, float y1, float x2, float y2) {
        return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
    }

    public static float eularSquareDistance(float x1, float y1, float z1, float x2, float y2, float z2) {
        return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1);
    }

    abstract static class CellIterator {
        static final int MAX_X = Config.UNIT_AMOUNT;
        static final int MAX_Y = Config.UNIT_AMOUNT;
        static final int MAX_Z = Config.UNIT_AMOUNT;
        abstract boolean hasNext();

        abstract String next();
    }
}
