/**
 * Created by tanki on 2017/2/23.
 */
package main.render;

import main.Config;
import main.algorithm.DiskNode;
import main.algorithm.Graph;
import main.algorithm.SphereNode;
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
            Graph graph = new Graph(10000, SphereNode.class);
            graph.renderOn(this);
        }).start();

    }

    @Override
    public void settings() {
        size(Config.X + 100, Config.Y + 100);
    }

    /* put something on the stage */
    public void updateCamera(){
        //camera(mouseX, height/2, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
    }
    public void draw()
    {
        //this.updateCamera();
        if(this.queue.isEmpty()) return;
        for (int i = 0; !this.queue.isEmpty(); i++) {
            this.queue.remove().renderOn(this);
        }
    }
}
