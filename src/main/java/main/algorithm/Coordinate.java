package main.algorithm;

/**
 * Created by tanki on 2017/3/2.
 */
public abstract class Coordinate implements Comparable<Coordinate>{
//    public static enum TYPE {
//        CARTESIAN,
//        POLAR2,
//        POLAR3
//    };
    public Coordinate(){
        randomlyInit();
    }
    public abstract void randomlyInit();
}
