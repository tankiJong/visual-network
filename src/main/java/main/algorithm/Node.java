package main.algorithm;

import main.Config;
import main.render.Canvas;
import main.render.Drawable;
import processing.core.PApplet;

import java.util.concurrent.ThreadLocalRandom;
/**
 * Created by tanki on 2017/2/25.
 */
public abstract class Node implements Comparable<Node>, Drawable{
    Coordinate coordinate;
    public abstract void renderOn(Canvas canvas);
}
