/**
 * Created by tanki on 2017/2/23.
 */
package main.render;

import main.Config;
import main.algorithm.DiskNode;
import main.algorithm.Graph;
import main.algorithm.SquareNode;
import processing.core.PApplet;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;

public class Canvas extends PApplet
{
    public Queue<Drawable> queue;
    /* main method of sketch */
    static public void main(String[] passedArgs)
    {
        String[] appletArgs = new String[]{"main.render.Canvas"};
        if (passedArgs != null)
        {
            PApplet.main(concat(appletArgs, passedArgs));
        }
        else
        {
            PApplet.main(appletArgs);
        }
    }

    public Canvas(){
        this.queue = new ArrayDeque<>(1000);
    }

    /* sketch initial setup */
    public void setup()
    {
        background(0);
        frameRate(500);
        new Thread(()->{
            Graph graph = new Graph(10000, DiskNode.class);
            graph.renderOn(this);
        }).start();

    }

    @Override
    public void settings() {
        size(Config.X, Config.Y);
    }

    /* put something on the stage */
    public void draw()
    {
        if(this.queue.isEmpty()) return;
        for (int i = 0; !this.queue.isEmpty(); i++) {
            this.queue.remove().renderOn(this);
        }
    }
}
